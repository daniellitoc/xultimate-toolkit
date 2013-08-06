package org.danielli.xultimate.test.rmi.init;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.danielli.xultimate.test.rmi.server.HelloWorld;
import org.danielli.xultimate.test.rmi.server.HelloWorldImpl;

public class InitHelloWorld {
	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorldImpl();
		try {
			HelloWorld stub = (HelloWorld) UnicastRemoteObject.exportObject(helloWorld, 0);
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.rebind("hello_world", stub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
