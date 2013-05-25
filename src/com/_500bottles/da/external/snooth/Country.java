package com._500bottles.da.external.snooth;

import com._500bottles.da.external.snooth.exception.InvalidCountry;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: administrator
 * Date: 5/22/13
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Country
{
	private final static String DEFAULT_COUNTRY = "";
	private static HashMap<String, String> countries;

	private String country;

	public Country ()
	{
		this.country = DEFAULT_COUNTRY;
	}

	public void setCountry(String country) throws InvalidCountry
	{
		if (this.countries == null)
			initCountriesMap();

		if (this.countries.get(country) == null)
			throw new InvalidCountry();

		this.country = country;
	}

	public String getCountry()
	{
		return this.country;
	}

	public String toString()
	{
		return this.getCountry();
	}

	public boolean equals(Country c)
	{
		if (c.getCountry() == this.getCountry())
			return true;

		return false;
	}

	private String getCountryName()
	{
		if (this.countries == null)
			initCountriesMap();

		return this.countries.get(country);
	}

	private static void initCountriesMap()
	{
		countries = new HashMap<String, String>();
		countries.put("ad","Andorra");
		countries.put("ar","Argentina");
		countries.put("au","Australia");
		countries.put("at","Austria");
		countries.put("bb","Barbados");
		countries.put("be","Belgium");
		countries.put("bz","Belize");
		countries.put("bm","Bermuda");
		countries.put("bo","Bolivia");
		countries.put("ba","Bosnia and Herzegovina");
		countries.put("br","Brazil");
		countries.put("bg","Bulgaria");
		countries.put("ca","Canada");
		countries.put("ky","Cayman Islands");
		countries.put("cl","Chile");
		countries.put("cn","China");
		countries.put("hr","Croatia");
		countries.put("cy","Cyprus");
		countries.put("cz","Czech Republic");
		countries.put("dk","Denmark");
		countries.put("ee","Estonia");
		countries.put("fi","Finland");
		countries.put("fr","France");
		countries.put("de","Germany");
		countries.put("el","Greece");
		countries.put("hk","Hong Kong");
		countries.put("hu","Hungary");
		countries.put("is","Iceland");
		countries.put("in","India");
		countries.put("id","Indonesia");
		countries.put("ie","Ireland");
		countries.put("il","Israel");
		countries.put("it","Italy");
		countries.put("jp","Japan");
		countries.put("lb","Lebanon");
		countries.put("li","Liechtenstein");
		countries.put("lt","Lithuania");
		countries.put("lu","Luxembourg");
		countries.put("my","Malaysia");
		countries.put("mt","Malta");
		countries.put("mu","Mauritius");
		countries.put("mx","Mexico");
		countries.put("md","Moldova");
		countries.put("mm","Myanmar");
		countries.put("nl","Netherlands");
		countries.put("nz","New Zealand");
		countries.put("ni","Nicaragua");
		countries.put("no","Norway");
		countries.put("pa","Panama");
		countries.put("pe","Peru");
		countries.put("ph","Philippines");
		countries.put("pl","Poland");
		countries.put("pt","Portugal");
		countries.put("ru","Russia");
		countries.put("sg","Singapore");
		countries.put("sk","Slovakia");
		countries.put("si","Slovenia");
		countries.put("za","South Africa");
		countries.put("kr","South Korea");
		countries.put("es","Spain");
		countries.put("se","Sweden");
		countries.put("ch","Switzerland");
		countries.put("tw","Taiwan");
		countries.put("th","Thailand");
		countries.put("tr","Turkey");
		countries.put("uk","United Kingdom");
		countries.put("us","United States");
		countries.put("uy","Uruguay");
		countries.put("vn","Vietnam");
	}
}