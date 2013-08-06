xultimate-toolkit
===================

The X-Ultimate Toolkit provides a JavaEE application reference architecture based Spring Framework.
	
部分介绍
	xultimate-core
		1.整合utils。包括commons-lang3、commons-io、commons-collections、commons-codec、pinyin4j、tamper、Spring(Utils)等，并封装加密与结算。
		2.整合日志，包括commons-logging、JUL、log4j。统一使用slf4j + logback。
		3.提供JSONTemplate，包括fastjson和jackson的封装。
		4.提供Serializer和Deserializer。包括对JAVA（基本数据类型、对象）、kryo、protostuff的封装。
		5.对joda-time、cglib、reflectasm的使用。
		6.一个秒表类，主要用于测试方法执行时间。见测试类。
	xultimate-context
		1.提供CharsetDetector，包括对icu4j、jchardet、cpdetector的封装。
		2.提供Encoder和Decoder，包括对Base64、Hex、Url的封装。
		3.提供Encryptor和Decryptor，包括对对称加密和非对称加密算法的封装。
		4.图片处理工具，包括改变大小、格式转换、剪裁、加水印的功能，包括对im4java和java的封装。
		5.扩展Spring的属性置换器，提供加密功能。
	xultimate-context-support
		1.提供Formatter，包括对FreeMarker、MessageFormat、Spel、StringTemplateV3、StrpingTemplateV4、Velocity的封装。
		2.包括对XMemcached的封装，主要有XMemcachedTemplate、支持Spring Cache、替换默认的序列化机制为protostuff。
		3.包括对Jedis的封装，主要有JedisTemplate、ShardedJedisTemplate、数据库主键最大值生成器。
		4.提供quartz的集群实例，同时，支持类似与JobDetailFactoryBean的形式。
		5.包括Freemarker、StringTemeplateV3、StringTemplateV4、Velocity的模板处理Utils类。
		5.提供Spring Mail的使用，包括各种使用情况、如用Velocity模板做邮件模板、加密SMTP用户密码、收件人等默认值、使用TaskExecutor异步处理。
	xultimate-jdbc
		1.提供MySQL的主键生成器、Oracle的序列生成器（扩展Spring，添加缓存个数）
		2.使用druid作为数据源实现。
	xultimate-web
		1.GZIP过滤器压缩响应流。
		2.一些Utils类，主要是对Spring(Utils的封装)，Cookie生成器等。	
		3.httpClient的Spring Bean形式，同时提供一个RestTemplate的使用。
	xultimate-hibernate
		1.一个可配置的Hibernate表明、列表等的命名策略。
		2.使用Spring Data JPA实现、添加了额外的动态查询功能。
		3.相应事例，展示JPA监听器等的使用。
		4.可以选择JdbcTemplate或mybatis，就不用hibernate。除非是企业级项目。
	xultimate-mybatis
		1.扩展SqlSessionFactoryBean，解决configurationProperties不起作用。
		2.动态查询、最好不用，后台为了方便可以用。
		3.使用事例，包括增、删、改、查的几个实现。去掉类似Hibernate那样复杂的映射，改为最直接的数据库-实体类的映射。
		4.事物这，查询的事物不需加注解，或者非要加的话使用NOT_SUPPORT，都比readonly=true要快。	
	xultimate-test
		1.收集的类，偶尔看看。
