package org.danielli.xultimate.core.serializer.kryo.support;

import com.esotericsoftware.kryo.Kryo;

public class KryoContext {
	
	private static final ThreadLocal<Kryo> currentKryo =  new ThreadLocal<Kryo>();
	
	public static void setCurrentKryo(Kryo kryo) {  
		if (kryo != null) {
			currentKryo.set(kryo);
		}
		else {
			currentKryo.remove();
		}
	}
	
	public static Kryo currentKryo() {  
		return currentKryo.get();
	}
}
