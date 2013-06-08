package com._500bottles.da.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import com._500bottles.config.Config;
import com._500bottles.exception.da.DAException;
import com._500bottles.manager.SessionManager;
import com._500bottles.object.wine.Favorites;
import com._500bottles.object.wine.Wine;

public class FavoritesDAO extends DAO
{
	private static final String FAVORITES_TABLE = Config
			.getProperty("favoritesTableName");

	public static Favorites addFavorite(long userId, Favorites favorite)
			throws DAException
	{
		String columns, values;
		columns = "(`userID`, ";
		columns += "`wineID`)";

		values = "('" + userId + "',";
		values += "'" + favorite.getWineId() + "')";
		try
		{
			// int i =
			insert(FAVORITES_TABLE, columns, values);
			Database.disconnect();
			// System.out.print("This is what we got: " + i);
		} catch (SQLException e)
		{
			throw new DAException("Failed Favorites insertion", e);
		}

		favorite.setfavoritesId(DAO.getLastInsertId());

		return favorite;
	}

	// Might not use one of the deletes
	public static boolean deleteFavorite(long userId, Wine wine)
	{
		try
		{
			delete(FAVORITES_TABLE, "wineId=" + wine.getId() + " and userId="
					+ userId);
			Database.disconnect();
		} catch (SQLException e)
		{
			return false;
			// throw new DAException("Failed Favorites deletion", e);
		}
		return true;
	}

	public static boolean deleteFavorite(Favorites favorite)
	{
		int ret;
		try
		{
			ret = delete(FAVORITES_TABLE,
					"favoritesId=" + favorite.getfavoritesId());
			Database.disconnect();
		} catch (SQLException e)
		{
			return false;
			// throw new DAException("Failed Favorites deletion", e);
		}
		if (ret == 0)
			return false;
		return true;
	}

	public static void editFavorite(Favorites favorite) throws DAException
	{
		long favoritesId = favorite.getfavoritesId();
		String sql = "";

		// Get userID from session manager
		sql += "wineID=" + favorite.getWineId();

		try
		{
			update(FAVORITES_TABLE, sql, "favoritesId=" + favoritesId);
			Database.disconnect();
		} catch (SQLException e)
		{
			throw new DAException("Failed Favorites update", e);
		}
	}

	public static Favorites getFavorite(long wineId) throws DAException
	{
		ResultSet r;
		Favorites favorite = null;

		try
		{
			long userId = SessionManager.getSessionManager().getLoggedInUser()
					.getUserId();
			// System.out.println(favoritesId);
			String where = "wineId = " + wineId;
			where += " AND " + "userId = " + userId;
			r = select(FAVORITES_TABLE, "*", where);

			// System.out.println("abc " + r.getLong("favoritesId"));
			favorite = createFavorites(r);
			Database.disconnect();
		} catch (SQLException e)
		{
			System.out.println("Message: " + e.getMessage());
			throw new DAException("SQL select exception.", e);
		}

		return favorite;
	}

	public static Favorites getFavorite(Favorites favorite) throws DAException,
			NullPointerException
	{
		if (favorite == null)
			throw new NullPointerException("Null Favorite.");
		if (favorite.getWineId() == 0)
			throw new DAException("Favorite not set.");

		long wineId = favorite.getWineId();
		// System.out.println("favoritesId in getFavorite: "
		// + favorite.getfavoritesId());
		return getFavorite(wineId);
	}

	private static Favorites createFavorites(ResultSet r) throws SQLException
	{
		Favorites f;

		long favoritesId, wineId;

		if (!r.next())
			return null;

		favoritesId = r.getLong("favoritesId");
		// System.out.println("break");
		wineId = r.getLong("wineId");

		f = new Favorites();
		f.setfavoritesId(favoritesId);
		f.setWineId(wineId);

		return f;
	}
}
