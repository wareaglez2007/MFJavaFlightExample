/**
 * 
 */
package com.tours.entity;

/**
 * @author Rostom Sahakian
 *
 */
public class City {
	/**
	 * CREATE TABLE CITIES ( CITY_ID INTEGER NOT NULL , CITY_NAME VARCHAR(24)
	 * NOT NULL, COUNTRY VARCHAR(26) NOT NULL, AIRPORT VARCHAR(3), LANGUAGE
	 * VARCHAR(16), COUNTRY_ISO_CODE CHAR(2) );
	 * 
	 * 
	 * ALTER TABLE CITIES ADD CONSTRAINT CITIES_PK Primary Key ( CITY_ID);
	 * 
	 * ALTER TABLE CITIES ADD CONSTRAINT COUNTRIES_FK Foreign Key (
	 * COUNTRY_ISO_CODE) REFERENCES COUNTRIES ( COUNTRY_ISO_CODE)
	 */
	
	private int city_id;
	private String city_name;
	private String country;
	private String airport;
	private String cic; //This is the foreign key that can be used to get the countries <->
	public int getCity_id() {
		return city_id;
	}
	
	//Create a constructor
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}


	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getCic() {
		return cic;
	}
	public void setCic(String cic) {
		this.cic = cic;
	}

	@Override
	public String toString() {
		return "City [city_id=" + city_id + ", city_name=" + city_name
				+ ", country=" + country + ", airport=" + airport + ", cic="
				+ cic + "]";
	}
	
}
