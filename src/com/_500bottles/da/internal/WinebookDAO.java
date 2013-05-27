package com._500bottles.da.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com._500bottles.config.Config;
import com._500bottles.exception.da.DAException;
import com._500bottles.object.wine.Wine;
import com._500bottles.object.winebook.Entry;
import com._500bottles.object.winebook.Photo;

public class WinebookDAO extends DAO
{
	private final static String WINEBOOK_TABLE = Config
			.getProperty("winebookTableName");

	public static Entry addEntry(Entry entry) throws Exception
	{
		String table, columns, values, dateCreated, dateLastEdited, winesJSON, photosJSON;

		// TODO: Maybe delete userID if not needed
		columns = "(`userID`, `title`, `dateCreated`,";
		columns += "`dateLastEdited`, `textContent`,";
		columns += "`winesJSON`, `photosJSON`)";

		dateCreated = formatDate(entry.getDateCreated());
		dateLastEdited = formatDate(entry.getDateLastEdited());

		photosJSON = entry.getPhotoIdsAsJSONArray().toJSONString();
		winesJSON = entry.getWineIdsAsJSONArray().toJSONString();

		// TODO: get user id from session manager or via user object.
		values = "('" + "0" + "',";
		values += "'" + entry.getTitle() + "',";
		values += "'" + dateCreated + "',";
		values += "'" + dateLastEdited + "',";
		values += "'" + entry.getContent() + "',";
		values += "'" + winesJSON + "',";
		values += "'" + photosJSON + "')";

		try
		{
			int i = insert(WINEBOOK_TABLE, columns, values);
			// TODO: Better exception handling.
		} catch (Exception e)
		{
			throw e;
		}

		entry.setEntryId(getLastInsertId());

		return entry;
	}

	public static void deleteEntry(Entry entry) throws SQLException
	{
		delete(WINEBOOK_TABLE, "WHERE entryId=" + entry.getEntryId());
	}

	public static void editEntry(Entry entry) throws SQLException
	{
		long entryId = entry.getEntryId();
		String sql = "";

		// sql += "entryID=" + entry.getEntryId();
		sql += "title=" + entry.getTitle();
		sql += ",dateCreated=" + formatDate(entry.getDateCreated());
		sql += ",dateLastEdited=" + formatDate(entry.getDateLastEdited());
		sql += ",textContent=" + entry.getContent();
		sql += ",winesJSON=" + entry.getWineIdsAsJSONArray();
		sql += ",photosJSON=" + entry.getPhotoIdsAsJSONArray();

		update(WINEBOOK_TABLE, sql, "entryId=" + entryId);
	}

	public static Entry getEntry(Entry entry) throws DAException
	{
		long entryId = entry.getEntryId();
		return getEntry(entryId);
	}

	public static Entry getEntry(long entryId) throws DAException
	{
		ResultSet r;
		Entry entry = null;

		try
		{
			r = select(WINEBOOK_TABLE, "*");
			entry = createEntry(r);

		} catch (Exception e)
		{
			// TODO: handle query exceptions.
		}

		return entry;
	}

	private static Entry createEntry(ResultSet r) throws SQLException
	{
		Entry entry;

		long userId, entryId;

		String title, textContent, winesJSON, photosJSON;

		Date dateCreated, dateLastEdited;

		JSONArray wineIds, photosIds;

		Vector<Wine> wines;
		Vector<Photo> photos;

		// Return null if there was no entry in the ResultSet.
		if (!r.next())
			return null;

		userId = r.getLong("userId");
		entryId = r.getLong("entryId");
		title = r.getString("title");
		textContent = r.getString("textContent");
		winesJSON = r.getString("winesJSON");
		photosJSON = r.getString("photosJSON");

		wineIds = (JSONArray) JSONValue.parse(winesJSON);
		photosIds = (JSONArray) JSONValue.parse(photosJSON);

		// TODO: Create the wine and photo objects to add to the entry.
		wines = new Vector<Wine>(); // (wineIds);
		photos = new Vector<Photo>(); // (photosIds);

		dateCreated = r.getDate("dateCreated");
		dateLastEdited = r.getDate("dateLastEdited");

		entry = new Entry(entryId, title, textContent, photos, wines,
				dateCreated, dateLastEdited);

		return entry;
	}
}
