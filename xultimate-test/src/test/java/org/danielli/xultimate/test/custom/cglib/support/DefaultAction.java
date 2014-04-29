package org.danielli.xultimate.test.custom.cglib.support;

import org.danielli.xultimate.test.custom.cglib.Action;

public class DefaultAction implements Action {

	@Override
	public void execute() {
		System.out.println("DefaultAction.execute()");
	}

}
