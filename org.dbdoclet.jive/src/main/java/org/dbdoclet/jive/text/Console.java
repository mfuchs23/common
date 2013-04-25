package org.dbdoclet.jive.text;

import java.awt.Font;
import java.util.regex.Pattern;


public class Console extends ScreenPane implements IConsole {

	private static final long serialVersionUID = 1L;
	protected static final Pattern REGEX_ERROR_MESSAGE = Pattern
			.compile("^.*\\.java:\\d+:.*$");

	public Console() {
		super();
	}

	public Console(Font font, int cols, int rows) {
		super(font, cols, rows);
	}

	public Console(Font font) {
		super(font);
	}

	public Console(int width, int height) {
		super(width, height);
	}

	@Override
	public void clear() {
		super.clear();
	}

	@Override
	public void info(String text) {
		super.info(text);
	}
}
