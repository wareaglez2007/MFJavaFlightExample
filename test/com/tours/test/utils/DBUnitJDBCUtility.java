package com.tours.test.utils;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

public class DBUnitJDBCUtility
{
    private IDatabaseTester databaseTester = null;
    private DataSource dataSource;
    private IDataSet dataSet;
    private DatabaseConnection connection;

    /**
     * This is the Utility class to run DBUnit tests.
     * 
     * The steps to use this utility:
     * <ol>
     *   <li>Construct an instance of this utility. <br />
     *       It will build an in-memory temp database</li>
     *   <li>Pass a file of SQL DDL commands to construct your database
     *       objects (tables, views, stored procedures, etc)</li>
     *   <li>Pass a file containing the data to load into the newly
     *       created database objects</li>
     *   <li>Get the DataSource, this will give you a connection pool
     *       for the temp database</li>
     *   <li>Execute your DAO tests, using the DataSource you acquired</li>
     *   <li>To use the DBUnit table assert commands such as: <br />
     *       <strong>Assertion.assertEquals(expectedTable, actualTable);</strong><br />
     *       call the getTableFromDatabase or the getTablesFromFile methods</li>
     *   <li>Finally close the temp database</li>
     * </ol>
     * 
     * @throws Exception
     */
    public DBUnitJDBCUtility(String SQLFile, String dataFile) throws Exception
    {		
        // the temp database connect details
        String url = "jdbc:hsqldb:mem:testdb";
        String userid = "sa";
        String password = "";
        String driver = "org.hsqldb.jdbcDriver";

        System.setProperty("jdbc.drivers", driver);
        try
        {
            // load the database driver
            Class.forName(driver);

            ConnectionFactory connectionFactory = 
                    new DriverManagerConnectionFactory(url, userid, password);
            // build a connection pool
            GenericObjectPool connectionPool = new GenericObjectPool();
            connectionPool.setMaxActive(10);
            connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
            new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
            // get a DataSource for the connection pool
            dataSource = new PoolingDataSource(connectionPool);

            databaseTester = new CustomDataSourceTester(dataSource);
            //	JDBCServiceManager.INSTANCE.setReadOnlyDataSource(dataSource);
            
            connection = new DatabaseConnection(dataSource.getConnection());
                    
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.FEATURE_DATATYPE_WARNING, false);
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                    new HsqldbDataTypeFactory());
            
            executeDDL(SQLFile);
            loadDataExtract(dataFile);

        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
    
    /**
     * This is the method to shutdown the temp database.
     * 
     */
    public void shutdown()
    {
        try
        {
            Connection conn = getDataSource().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SHUTDOWN");
            stmt.close();
            conn.close();
            System.gc();
        }
        catch (Exception e)
        {
            fail(e.getLocalizedMessage());
        }
    }
    
    /**
     * This is the method to reset the temp database.
     * 
     * @throws Exception
     */
    public void clean() throws Exception 
    {
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
    
    /**
     * This is the method to rebuild the temp database.
     * 
     * @param dllFileName the name of the DDL text file
     * @param fileName the name of the XML data file
     * @throws Exception if the commands cannot be executed
     */
    public void rebuild(String dllFileName, String fileName) 
            throws Exception 
    {
        executeDDL(dllFileName);
        loadDataExtract(fileName);
    }

    /**
     * This is the method used to execute DDL commands against the temp database.
     * 
     * @param dllFileName the name of the DDL text file
     * @throws Exception if the commands cannot be executed
     */
    public void executeDDL(String dllFileName) throws Exception
    {
        List<String> sqlStatements = 
                new ClasspathFileReader().readFile(dllFileName);

        Statement stmt = getDataSource().getConnection().createStatement();

        for (String ddl : sqlStatements)
        {
            stmt.execute(ddl);
        }
    }

    /**
     * This is the method to load table data from an XML file.
     * 
     * @param fileName the name of the XML data file
     * @throws Exception if the commands cannot be executed
     */
    public void loadDataExtract(String fileName) throws Exception
    {
        InputStream inStream = new ClasspathFileReader()
        .readStream(fileName);
        FlatXmlProducer producer = new FlatXmlProducer(
                new InputSource(inStream));
        producer.setColumnSensing(true);
        dataSet = new CachedDataSet(producer);

        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    /**
     * This is the method that returns a DataSource for the temp database.
     * 
     * @return the DataSource
     * @throws Exception is the DataSource cannot be returned
     */
    public DataSource getDataSource() throws Exception
    {
        return dataSource;
    }

    /**
     * This a the method that extracts a Table from the temp database.
     * 
     * This method is used for the DBUnit assert commands.
     * 
     * @param tableName to extract from the temp database
     * @return the Table object
     * @throws Exception if there is a problem
     */
    public ITable getTableFromDatabase(String tableName) throws Exception
    {
        IDataSet databaseDataSet =
                databaseTester.getConnection().createDataSet();
        return databaseDataSet.getTable(tableName);
    }

    /**
     * This is the method to extract a set of Tables from an XML data file.
     * 
     * This method is used for the DBUnit assert commands.
     * 
     * @param fileName the XML data file name
     * @param tableNames the names of the Tables to extract from the XML data file
     * @return Map<String, ITable> the map of names and Table objects
     * @throws Exception if the tables cannot be extracted
     */
    public Map<String, ITable> getTablesFromFile(String fileName, List<String> tableNames) 
            throws Exception
    {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();

        builder.setColumnSensing(true);
        InputStream inStream = new ClasspathFileReader()
        .readStream(fileName);
        IDataSet expectedDataSet = builder.build(new InputSource(inStream));

        Map<String, ITable> results = new HashMap<String, ITable>();
        for (String current : tableNames)
        {
            results.put(current, expectedDataSet.getTable(current));
        }
        return results;
    }

    /**
     * This is the method to extract a Table from an XML data file.
     * 
     * This method is used for the DBUnit assert commands.
     * 
     * @param fileName the XML data file name
     * @param tableName the name of the Table to extract from the XML data file
     * @return  the Table object
     * @throws Exception if the table cannot be extracted
     */
    public ITable getTableFromFile(String fileName, String tableName) 
            throws Exception
    {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();

        builder.setColumnSensing(true);
        InputStream inStream = new ClasspathFileReader()
        .readStream(fileName);
        IDataSet expectedDataSet = builder.build(new InputSource(inStream));

        return expectedDataSet.getTable(tableName);
    }
   

    private class CustomDataSourceTester extends DataSourceDatabaseTester
    {
        public CustomDataSourceTester(DataSource dataSource)
        {
            super(dataSource);
        }

        // override of the default to configure the connection object
        @Override
        public IDatabaseConnection getConnection() throws Exception
        {
            IDatabaseConnection connection = super.getConnection();
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.FEATURE_DATATYPE_WARNING,
                    false);
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                    new HsqldbDataTypeFactory());
            return connection;
        }
    }

    private class ClasspathFileReader
    {
        public List<String> readFile(String name) throws RuntimeException
        {
            if (name == null) { throw new RuntimeException(
                    "Null files are not allowed"); }
            try
            {
                InputStream inFile = this.getClass().getClassLoader()
                        .getResourceAsStream(name);
                List<String> results = new ArrayList<String>();
                String line = null;

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        inFile));
                while (true)
                {
                    line = in.readLine();
                    if (line == null)
                    {
                        break;
                    }
                    results.add(line);
                }
                in.close();
                return results;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Failed to open file: " + name, e);
            }
        }

        public InputStream readStream(String name) throws RuntimeException
        {
            if (name == null) 
            { 
                throw new RuntimeException(
                        "Null files are not allowed"); 
            }
            try
            {
                InputStream inStream = ClasspathFileReader.class.getClassLoader()
                        .getResourceAsStream(name);
                return inStream;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Failed to open file: " + name, e);
            }
        }
    }
}
