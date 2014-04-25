package org.danielli.xultimate.core.serializer.kryo.support;

import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;

import com.esotericsoftware.kryo.Kryo;

public class ThreadLocalKryoGenerator implements KryoGenerator {

	public static final ThreadLocalKryoGenerator INSTANCE = new ThreadLocalKryoGenerator();
	
	private KryoGenerator kryoGenerator = DefaultKryoGenerator.INSTANCE;
	
	@Override
	public Kryo generate() {
		Kryo currentKryo = KryoContext.currentKryo();
		if (currentKryo == null) {
			currentKryo = kryoGenerator.generate();
			KryoContext.setCurrentKryo(currentKryo);
		}
		return currentKryo;
	}

	public KryoGenerator getKryoGenerator() {
		return kryoGenerator;
	}

	public void setKryoGenerator(KryoGenerator kryoGenerator) {
		this.kryoGenerator = kryoGenerator;
	}

}
