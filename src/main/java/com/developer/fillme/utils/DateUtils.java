package com.developer.fillme.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public interface DatePattern {
		String YYYY_MM_DD = "yyyy-MM-dd";
	}

	public static LocalDate toLocalDate(String dateStr, String pattern) {
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	public static String localDateToString(LocalDate localDate, String pattern) {
		if (localDate == null || StringUtils.isBlank(pattern)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return formatter.format(localDate);
	}

	public static LocalDate parseDate(String dateStr) {
		DateTimeFormatter[] formatters = new DateTimeFormatter[] {
				DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH),
				DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH),
				DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH),
				DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH),
				DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH),
		};

		for (DateTimeFormatter formatter : formatters) {
			try {
                return LocalDate.parse(dateStr, formatter);
			} catch (DateTimeParseException ignored) {
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String dateStr = "11-11-1999";
		LocalDate localDate = parseDate(dateStr);
		if (localDate == null) {
			System.out.println("Invalid date + " + dateStr);
			return;
		}
		System.out.println(localDate);
	}
}
