package org.danielli.xultimate.test.jvm.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xms20m -Xmx20m
 * 
 * @author Daniel Li
 */
public class HeapOOM {
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		
		while (true) {
			list.add(new OOMObject());
		}
	}
	
	static class OOMObject {
		
	}
}
