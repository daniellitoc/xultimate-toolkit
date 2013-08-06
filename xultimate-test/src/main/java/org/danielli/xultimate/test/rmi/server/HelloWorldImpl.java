package org.danielli.xultimate.test.rmi.server;

import java.rmi.RemoteException;

public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHello(String name) throws RemoteException {
		return name + "! Hello World!";
	}

}
