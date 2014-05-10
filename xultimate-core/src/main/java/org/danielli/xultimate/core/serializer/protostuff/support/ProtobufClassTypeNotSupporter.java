package org.danielli.xultimate.core.serializer.protostuff.support;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.danielli.xultimate.core.support.DefaultClassTypeNotSupporter;
import org.joda.time.DateTime;

/**
 * 序列化性能不太好。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ProtobufClassTypeNotSupporter extends DefaultClassTypeNotSupporter {

	public static final ProtobufClassTypeNotSupporter INSTANCE = new ProtobufClassTypeNotSupporter(); 
	
	private ProtobufClassTypeNotSupporter() {
		Class<?>[] notSupportClassTypes = new Class<?>[5];
		notSupportClassTypes[0] = Number.class;
		notSupportClassTypes[1] = Date.class;
		notSupportClassTypes[2] = DateTime.class;
		notSupportClassTypes[3] = Collection.class;
		notSupportClassTypes[4] = Map.class;
	}
	
	@Override
	public boolean support(Class<?> classType) {
		return classType.isArray() ? false : super.support(classType);
	}
}
