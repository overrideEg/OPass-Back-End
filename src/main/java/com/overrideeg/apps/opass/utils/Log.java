package com.overrideeg.apps.opass.utils;



import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {


	public Log(Class Class, Exception e) {
		final String space = "\n==============================================================\n\n";
		String message = "ERROR IN " + Class.getName() + ": " + e.getMessage() + space;
//		BackLogDTO backLog = new BackLogDTO(message, Class.getSimpleName(), e.getMessage(), stackTraceToString(e));
//		BackLogDTO backLogDTO = new BackLogServiceImpl().create(backLog);
//		System.err.println(message + ":" + backLogDTO.getValueDate() + ":" + backLogDTO.getHostAddress());
		System.err.print(space);
		e.printStackTrace();
		System.err.print(space);
	}

	public Log(Class Class, Exception e, String note) {
		final String space = "\n==============================================================\n\n";
		String message = "ERROR IN " + Class.getName() + ", " + note + ":\n" + e.getMessage() + space;
//		BackLogDTO backLog = new BackLogDTO(message, Class.getSimpleName(), e.getMessage(), stackTraceToString(e));
//		BackLogDTO backLogDTO = new BackLogServiceImpl().create(backLog);
//		System.err.println(message + ":" + backLogDTO.getValueDate() + ":" + backLogDTO.getHostAddress());
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