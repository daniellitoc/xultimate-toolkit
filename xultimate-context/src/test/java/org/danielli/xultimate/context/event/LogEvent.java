package org.danielli.xultimate.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

public class LogEvent extends ApplicationContextEvent {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public LogEvent(ApplicationContext source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
