package org.danielli.xultimate.test.cglib.support;

import org.danielli.xultimate.test.cglib.Action;

public class DefaultAction implements Action {

	@Override
	public void execute() {
		System.out.println("DefaultAction.execute()");
	}

}
