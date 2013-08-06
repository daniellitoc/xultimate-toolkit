package org.danielli.xultimate.test.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.danielli.xultimate.test.rmi.server.HelloWorld;

public class HelloWorldClient {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.111.100", Registry.REGISTRY_PORT);
			String[] beans = registry.list();
			for (String bean : beans) {
				System.out.println(bean);
			}
			HelloWorld helloWorld = (HelloWorld) registry.lookup("hello_world");
			System.out.println(helloWorld.sayHello("Daniel Li"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
