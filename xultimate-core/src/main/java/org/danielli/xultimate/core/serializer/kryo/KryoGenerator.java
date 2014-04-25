package org.danielli.xultimate.core.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * Kryo实例生成器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface KryoGenerator {
	
	Kryo generate();
}
