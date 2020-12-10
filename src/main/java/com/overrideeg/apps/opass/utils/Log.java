/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.utils;



import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {


	public Log(Class Class, Exception e) {
		final String space = "\n==============================================================\n\n";
		String message = "ERROR IN " + Class.getName() + ": " + e.getMessage() + space;
		System.err.print(space);
		e.printStackTrace();
		System.err.print(space);
	}

	public Log(Class Class, Exception e, String note) {
		final String space = "\n==============================================================\n\n";
		String message = "ERROR IN " + Class.getName() + ", " + note + ":\n" + e.getMessage() + space;
		e.printStackTrace();
		System.err.print(space);
	}

	private String stackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

}