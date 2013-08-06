package org.danielli.xultimate.test.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloWorld extends Remote {
	
	String sayHello(String name) throws RemoteException;
}
