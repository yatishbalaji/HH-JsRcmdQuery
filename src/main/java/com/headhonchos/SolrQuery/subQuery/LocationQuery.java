package com.headhonchos.SolrQuery.subQuery;

import com.headhonchos.Exceptions.NoJobDataException;
import com.headhonchos.Exceptions.QueryBuildException;
import com.headhonchos.LocationStat;
import com.headhonchos.jobPosting.Location;
import com.headhonchos.queryUtil.QueryBuilder;
import com.headhonchos.queryUtil.QueryJoiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationQuery
{
	private static Logger logger = LoggerFactory.getLogger(LocationQuery.class);
	public static String getQuery(List<Location> locations, boolean anyLocationPreferred)
			throws NoJobDataException, QueryBuildException
	{
		logger.debug("input : {} ", locations);
		if (CollectionUtils.isEmpty(locations)) {
			throw new NoJobDataException("LocationList is either Empty or null.");
		}

		List<String> subQueriesList = new ArrayList();
		List<String> citiesList = new ArrayList();
		for (Location location : locations)
		{

			double latitude = location.getLatitude();
			double longitude = location.getLongitude();

			Pair<Pair<String, String>, Pair<String, String>> preferredLatLng = LocationStat.getPreferredLng(latitude, longitude);
			Pair<String, String> latPair = (Pair)preferredLatLng.getLeft();
			Pair<String, String> lngPair = (Pair)preferredLatLng.getRight();

			String minDegLat = (String)latPair.getLeft();
			String maxDegLat = (String)latPair.getRight();
			String minDegLon = (String)lngPair.getLeft();
			String maxDegLon = (String)lngPair.getRight();

			String actualLatQuery = QueryBuilder.getRangeQuery("lat", minDegLat, maxDegLon);
			String actualLngQuery = QueryBuilder.getRangeQuery("lng", minDegLon, maxDegLat);
			String actualLatLngQuery = QueryJoiner.and(actualLatQuery, actualLngQuery);

			List<String> prefLocationQueries = new ArrayList();
			String subQuery;
			if (anyLocationPreferred) {
				String pref1 = QueryJoiner.and(QueryBuilder.getRangeQuery("pref1lat", minDegLat, maxDegLat), QueryBuilder.getRangeQuery("pref1lng", minDegLon, maxDegLon));
				prefLocationQueries.add(pref1);
				String pref2 = QueryJoiner.and(QueryBuilder.getRangeQuery("pref2lat", minDegLat, maxDegLat), QueryBuilder.getRangeQuery("pref2lng", minDegLon, maxDegLon));
				prefLocationQueries.add(pref2);
				String pref3 = QueryJoiner.and(QueryBuilder.getRangeQuery("pref3lat", minDegLat, maxDegLat), QueryBuilder.getRangeQuery("pref3lng", minDegLon, maxDegLon));
				prefLocationQueries.add(pref3);
				String preferredLocationQuery = QueryJoiner.or(prefLocationQueries);

				subQuery = QueryJoiner.or(actualLatLngQuery, preferredLocationQuery);
			} else {
				subQuery = actualLatQuery;
			}
			subQueriesList.add(subQuery);
			citiesList.add(location.getCityName());
		}
		String latLonQuery = QueryJoiner.or(subQueriesList);


		String actual_js_profiles_city = QueryBuilder.getSimpleQuery("actual_js_profiles_city", citiesList);
		String actual_js_profiles_country = QueryBuilder.getSimpleQuery("actual_js_profiles_country", citiesList);
		String or = QueryJoiner.or(actual_js_profiles_city, actual_js_profiles_country);
		String locationNameQuery;
		if (anyLocationPreferred) {
			String actual_all_js_preferences_locations_cities = QueryBuilder.getSimpleQuery("actual_all_js_preferences_locations_cities", citiesList);
			String simpleQuery = QueryBuilder.getSimpleQuery("actual_all_js_preferences_locations_cities", new ArrayList(Arrays.asList(new String[] { "Any" })));
			String or1 = QueryJoiner.or(actual_all_js_preferences_locations_cities, simpleQuery);
			locationNameQuery = QueryJoiner.or(or, or1);
		} else {
			locationNameQuery = or;
		}
		String locationSubQuery = QueryJoiner.or(locationNameQuery, latLonQuery);
		logger.debug("SubQuery : {} ", locationSubQuery);
		return locationSubQuery;
	}
}