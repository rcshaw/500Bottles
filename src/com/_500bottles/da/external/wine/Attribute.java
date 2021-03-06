package com._500bottles.da.external.wine;

import java.util.HashMap;

import com._500bottles.da.external.wine.exception.InvalidCategory;

//this is the abstract class is extended by all the classes that checks for format of 
//the url building class for wine.com
public abstract class Attribute
{
	private final static String DEFAULT_CATEGORY = "";
	protected HashMap<String, String> categoryAttributeMap;
	private String category, errormsg;

	public Attribute(String e)
	{
		this.category = DEFAULT_CATEGORY;
		errormsg = e;
	}

	public void setCategory(String cat) throws InvalidCategory
	{
		if (categoryAttributeMap == null)
			initCategoryAttributeMap();

		if (categoryAttributeMap.get(cat) == null)
			throw new InvalidCategory(errormsg);

		this.category = cat;
	}

	public String getCategory()
	{
		return this.category;
	}

	public boolean equals(Attribute a)
	{
		if (a.getCategory() == this.getCategory())
			return true;

		return false;
	}

	public String getAttribute()
	{
		if (categoryAttributeMap == null)
			initCategoryAttributeMap();

		return this.categoryAttributeMap.get(this.category);
	}

	protected abstract void initCategoryAttributeMap();

}
