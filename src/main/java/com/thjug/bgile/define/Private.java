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
package com.thjug.bgile.define;

/**
 *
 * @author @nuboat
 */
public enum Private {

	T("True"),
	F("False");

	private final String text;

	private Private(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
