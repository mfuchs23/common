package org.dbdoclet.jive.text;

import java.awt.Color;

public interface IConsole {

	public void clear();
	public void error(String msg);
	public void exception(Throwable oops);
	public void info(String string);
	public void section(String msg);
	public void setBackground(Color white);
}