package org.danielli.xultimate.core.serializer.kryo.support;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.BigDecimalSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.BigIntegerSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ClassSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CurrencySerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBufferSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBuilderSerializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.CopyForIterateCollectionSerializer;
import de.javakaffee.kryoserializers.CopyForIterateMapSerializer;
import de.javakaffee.kryoserializers.DateSerializer;
import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.EnumSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

public class DefaultKryoGenerator implements KryoGenerator {
	
	public static final DefaultKryoGenerator INSTANCE = new DefaultKryoGenerator();

	@Override
	public Kryo generate() {
		Kryo kryo = new KryoReflectionFactorySupport() {
			
			@SuppressWarnings("unchecked")
			public com.esotericsoftware.kryo.Serializer<?> getDefaultSerializer(@SuppressWarnings("rawtypes") Class type) {
				if (EnumSet.class.isAssignableFrom(type) ) {
                    return new EnumSetSerializer();
                }
                if (EnumMap.class.isAssignableFrom(type) ) {
                    return new EnumMapSerializer();
                }
                if (Collection.class.isAssignableFrom(type) ) {
                    return new CopyForIterateCollectionSerializer();
                }
                if (Map.class.isAssignableFrom(type) ) {
                    return new CopyForIterateMapSerializer();
                }
                if (Date.class.isAssignableFrom(type) ) {
                    return new DateSerializer(type);
                }
                return super.getDefaultSerializer(type);
			};
		};
		kryo.setRegistrationRequired(false);	// 是否需要预先知道对象所属的类。
		kryo.setReferences(true);	// 开启这个选项，表示相同的对象将被序列化为同一个byte[]。如果要支持循环引用，则必须开启。
		
		kryo.register(ArrayList.class);
	    kryo.register(LinkedList.class);
	    kryo.register(HashSet.class);
	    kryo.register(HashMap.class);
		
		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());

		kryo.register(Currency.class, new CurrencySerializer());
	    kryo.register(StringBuffer.class, new StringBufferSerializer());
	    kryo.register(StringBuilder.class, new StringBuilderSerializer());
		
		kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
	    kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
	    kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
	    kryo.register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer());
	    kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer());
	    kryo.register(Collections.singletonMap("", "").getClass(), new CollectionsSingletonMapSerializer());
	    
	    kryo.register(Class.class, new ClassSerializer());
	    
	    kryo.register(BigDecimal.class, new BigDecimalSerializer());
	    kryo.register(BigInteger.class, new BigIntegerSerializer());
	    kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
	    kryo.register(InvocationHandler.class, new JdkProxySerializer());
	    UnmodifiableCollectionsSerializer.registerSerializers( kryo );
	    SynchronizedCollectionsSerializer.registerSerializers(kryo);
		return kryo;
	}

}
