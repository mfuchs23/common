/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.BorderLayout;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateChooser extends JComponent {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(DateChooser.class);

	private Date date;
	private JComponent chooser;
	private Method methodGetDate;

	public DateChooser(Date date) {

		try {

			Class<?> clazz = Class.forName("com.toedter.calendar.JDateChooser");

			Class<?>[] argTypes = new Class[1];
			argTypes[0] = Date.class;

			Constructor<?> constructor = clazz.getConstructor(argTypes);
			Object[] args = new Object[1];
			args[0] = date;
			chooser = (JComponent) constructor.newInstance(args);

			argTypes = new Class[0];
			methodGetDate = clazz.getMethod("getDate", argTypes);

		} catch (Exception oops) {

			chooser = new JTextField();
		}

		setLayout(new BorderLayout());
		add(chooser, BorderLayout.CENTER);
	}

	public Date getDate() {

		try {

			if (methodGetDate != null) {

				Object[] args = new Object[0];
				return (Date) methodGetDate.invoke(chooser, args);
			}

		} catch (Exception oops) {

			logger.fatal("Unexpected Exception!", oops);

			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
			ebox.toFront();
		}

		return date;
	}
}
