/*
 * Attribution
 * CC BY
 * This license lets others distribute, remix, tweak,
 * and build upon your work, even commercially,
 * as long as they credit you for the original creation.
 * This is the most accommodating of licenses offered.
 * Recommended for maximum dissemination and use of licensed materials.
 *
 * http://creativecommons.org/licenses/by/3.0/
 * http://creativecommons.org/licenses/by/3.0/legalcode
 */
package com.thjug.bgile.util;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 *
 * @author @nuboat
 */
public final class StringUtility {

	private StringUtility() {
	}

	public static boolean isEmpty(final String string) {
		return string == null || "".equals(string) ? true : false;
	}

	private static final String EMAIL_PATTERN_STR = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STR);

	public static boolean isEmail(final String string) {
		return EMAIL_PATTERN.matcher(string).matches();
	}

	private static final String ALPHABET_PATTERN_STR = "^[a-zA-Z0-9_]+$";
	private static final Pattern ALPHABET_PATTERN = Pattern.compile(ALPHABET_PATTERN_STR);

	public static boolean isAlphabet(final String string) {
		return ALPHABET_PATTERN.matcher(string).matches();
	}

	public static String readableFileSize(final long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		final int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}