package org.configureme.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for dates.
 * 
 * @author anotheria team
 */
public final class DateUtils {

	/**
	 * Date format.
	 */
	public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mmZ";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private DateUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Convert timestamp to {@link String} representation in iso-8601 format.
	 * 
	 * @param timestamp
	 *            timestamp
	 * @return {@link String}
	 */
	public static String toISO8601String(final long timestamp) {
		return toISO8601String(new Date(timestamp));
	}

	/**
	 * Convert {@link Date} to {@link String} representation in iso-8601 format.
	 * 
	 * @param date
	 *            original {@link Date}
	 * @return {@link String}
	 */
	public static String toISO8601String(final Date date) {
		if (date == null)
			return StringUtils.EMPTY;

		return new SimpleDateFormat(FORMAT_ISO_8601).format(date);
	}

}
