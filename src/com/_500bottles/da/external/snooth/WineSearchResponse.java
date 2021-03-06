package com._500bottles.da.external.snooth;

import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created with IntelliJ IDEA. User: administrator Date: 5/23/13 Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class WineSearchResponse extends Response
{
	private Vector<SnoothWine> wines;

	public WineSearchResponse(String response)
	{
		super(response);

		wines = new Vector<SnoothWine>();

		JSONArray wines_json;

		if (getNumberOfReturned() <= 0)
			return;

		wines_json = (JSONArray) message.get("wines");
		parse_wines_json(wines_json);
	}

	public Vector<SnoothWine> getWines()
	{
		return wines;
	}

	public Iterator<SnoothWine> getWinesIterator()
	{
		return wines.iterator();
	}

	private void parse_wines_json(JSONArray wines_json)
	{
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> it = (Iterator<JSONObject>) wines_json.iterator();

		while (it.hasNext())
		{
			JSONObject item = it.next();
			SnoothWine wine = new SnoothWine();

			wine.setName((String) item.get("name"));
			wine.setCode((String) item.get("code"));
			wine.setRegion((String) item.get("region"));
			wine.setWinery((String) item.get("winery"));
			wine.setWinery_id((String) item.get("winer_id"));
			wine.setVarietal((String) item.get("varietal"));
			wine.setPrice((String) item.get("price"));
			wine.setVintage((String) item.get("vintage"));
			wine.setType((String) item.get("type"));
			wine.setLink((String) item.get("link"));
			wine.setTags((String) item.get("tags"));
			wine.setImage((String) item.get("image"));
			wine.setSnoothrank(item.get("snoothrank"));
			wine.setAvailable((Long) item.get("available"));
			wine.setNum_merchants((Long) item.get("num_merchants"));
			wine.setNum_reviews((Long) item.get("num_reviews"));
			wine.setWineMakerNotes((String) item.get("wm_notes"));

			wines.add(wine);
		}
	}
}
