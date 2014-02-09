# xultimate-toolkit #

The X-Ultimate Toolkit provides a JavaEE application reference architecture based Spring Framework.
	
	
## xultimate-core ##

* 整合utils。包括commons-lang3、commons-io、commons-collections、commons-codec、pinyin4j、tamper、Spring(Utils)等，并封装加密与结算。
* 整合日志，包括commons-logging、JUL、log4j。统一使用slf4j + logback。
* 提供JSONTemplate，包括fastjson和jackson的封装。
* 提供Serializer和Deserializer。包括对JAVA（基本数据类型、对象）、kryo、protostuff的封装。
* 对joda-time、cglib、reflectasm的使用。
* 一个秒表类，主要用于测试方法执行时间。见测试类。


## xultimate-context ##

* 提供CharsetDetector，包括对icu4j、jchardet、cpdetector的封装。
* 提供Encoder和Decoder，包括对Base64、Hex、Url的封装。
* 提供Encryptor和Decryptor，包括对对称加密和非对称加密算法的封装。
* 图片处理工具，包括改变大小、格式转换、剪裁、加水印的功能，包括对im4java和java的封装。
* 扩展Spring的属性置换器，提供加密功能。


## xultimate-context-support ##

* 提供Formatter，包括对HTTL、Spel、FreeMarker、MessageFormat、StringTemplateV3、StrpingTemplateV4、Velocity的封装。
* 包括对XMemcached的封装，主要有XMemcachedTemplate、支持Spring Cache、替换默认的序列化机制为protostuff或Kryo。
* 包括对Jedis的封装，主要有JedisTemplate、ShardedJedisTemplate、数据库主键最大值生成器。
* 包括对FastDFS Java Api的封装，主要有ClientGlobalInitializer、StorageClientTemplate、FastDFSUtils。
* 提供quartz的集群实例，同时，支持类似与JobDetailFactoryBean的形式。
* 包括HTTL、Freemarker、StringTemeplateV3、StringTemplateV4、Velocity的模板处理Utils类。
* 提供Spring Mail的使用，包括各种使用情况、如用HTTL模板做邮件模板、加密SMTP用户密码、收件人等默认值、使用TaskExecutor异步处理。


## xultimate-jdbc ##

* 提供MySQL的主键生成器、Oracle的序列生成器（扩展Spring，添加缓存个数）
* 使用druid作为数据源实现。


## xultimate-web ##

* GZIP过滤器压缩响应流。
* 一些Utils类，主要是对Spring(Utils的封装)，Cookie生成器等。	
* httpClient的Spring Bean形式，同时提供一个RestTemplate的使用。

## xultimate-webmvc ##

* 包含一套Spring MVC的使用Demo。
* 使用XMemcached实现的访问限制器VisitLimiter。
* Session跨域处理。


## xultimate-hibernate ##

* 一个可配置的Hibernate表明、列表等的命名策略。
* 使用Spring Data JPA实现、添加了额外的动态查询功能。
* 相应事例，展示JPA监听器等的使用。
* 可以选择JdbcTemplate或mybatis，就不用hibernate。除非是企业级项目。


## xultimate-mybatis ##

* 扩展SqlSessionFactoryBean，解决configurationProperties不起作用。
* 动态查询、最好不用，后台为了方便可以用。
* 使用事例，包括增、删、改、查的几个实现。去掉类似Hibernate那样复杂的映射，改为最直接的数据库-实体类的映射。
* 事物这，查询的事物不需加注解，或者非要加的话使用NOT_SUPPORT，都比readonly=true要快。	


## xultimate-test ##

* 收集的类，偶尔看看。
