package com.example.feedservice.infrastructure.configuration


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.transaction.annotation.EnableTransactionManagement


//@Configuration
//@EnableTransactionManagement
//class MongoConfig(
//    @Value("\${spring.data.mongodb.database}")
//    private val dbName: String
//) : AbstractReactiveMongoConfiguration() {
//    override fun getDatabaseName(): String {
//        return dbName
//    }
//
//    @Bean
//    fun transactionManager(dbFactory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager {
//        return ReactiveMongoTransactionManager(dbFactory)
//    }
//}


//@ConfigurationProperties(prefix = "spring.data.mongodb")
//@ConfigurationProperties(prefix = "spring.data.mongodb")
//@EnableMongoRepositories(basePackageClasses = [MongoRepository::class])
//@EnableReactiveMongoReposito#ries(basePackages = "net.luversof.blog.repository")

/*
@ConfigurationProperties(prefix = "spring.data.mongodb")
data class MongoConfigProperties(
    val database: String,
    val port: Int,
    val host: String,
    val username: String,
    val password: String
)
@Configuration
@EnableReactiveMongoRepositories(basePackages = ["com.example.feedservice.adapter.outbound.document"])
class MongoDbConfiguration(
    val mongoProp: MongoConfigProperties,
    val env: Environment,
    //@Value("\${mongodb.username}") val username: String
) : AbstractReactiveMongoConfiguration() {

    //    @Autowired
//    @Value("\${spring.profiles.active}")
    val activeProfile: String
        get() = if (env.activeProfiles.isNotEmpty()) env.activeProfiles[0] else "dev"
    override fun getDatabaseName(): String {
        return mongoProp.database
    }

    //       uri: mongodb://catchtable:9T5ywrkDR1@catchtable-mat-db.cluster-cngeukcxnbat.ap-northeast-2.docdb.amazonaws.com:27017/?tls=true&tlsCAFile=global-bundle-dev.pem&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false
    //
//    override fun reactiveMongoClient(): MongoClient {
//        return MongoClients.create(connectionString)
//
//    }
    @Bean
    override fun reactiveMongoTemplate(
        reactiveMongoDbFactory: ReactiveMongoDatabaseFactory,
        mappingMongoConverter: MappingMongoConverter
    ): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoDbFactory, mappingMongoConverter)
    }


    override fun configureClientSettings(builder: MongoClientSettings.Builder) {
        builder
            .readPreference(ReadPreference.secondaryPreferred())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY.withWTimeout(5000L, TimeUnit.MILLISECONDS)) // documentdb default majority + timeout: infinite 이므로 timeout 설정을 꼭 해야함.
            .applyConnectionString(ConnectionString(connectionString))
            .applyToSslSettings { ssl: SslSettings.Builder -> ssl.enabled(true).context(createSSLConfiguration()) }

        val pojoCodecProvider: CodecProvider = PojoCodecProvider.builder()
            .automatic(true)
            .build()
        val registry: CodecRegistry = CodecRegistries.fromRegistries(
            createCustomCodecRegistry(),
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(pojoCodecProvider)
        )
        builder.codecRegistry(registry)
    }

    private val connectionString =
        "mongodb://${mongoProp.username}:${mongoProp.password}@${mongoProp.host}:${mongoProp.port}/${databaseName}?tls=true&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false"


    private fun createCustomCodecRegistry(): CodecRegistry? {
        return CodecRegistries.fromCodecs(
            LocalDateTimeCodec()
        )
    }

    fun asString(resource: Resource): String {
        try {
            InputStreamReader(resource.inputStream, "UTF-8").use { reader ->
                return FileCopyUtils.copyToString(reader)
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }
    private fun createSSLConfiguration(): SSLContext {
        val cpr = ClassPathResource(CERT_FILE_PATH + activeProfile + CERT_FILE_PATH_POSTFIX)
        //val certContent = Files.readString(cpr.file.toPath())
        val certContent = asString(DefaultResourceLoader().getResource("classpath:${cpr.path}"))

        val allCertificates: Set<String> = certContent
            .split(END_OF_CERTIFICATE_DELIMITER).filter { it.isNotBlank() }
            .map { it + END_OF_CERTIFICATE_DELIMITER }
            .toSet()
        val certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE)
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        var certNumber = 1
        for (cert in allCertificates) {
            val caCert: Certificate = certificateFactory.generateCertificate(ByteArrayInputStream(cert.toByteArray()))
            keyStore.setCertificateEntry(String.format("AWS-certificate-%s", certNumber++), caCert)
        }
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val sslContext: SSLContext = SSLContext.getInstance(TLS_PROTOCOL)
        sslContext.init(null, trustManagerFactory.trustManagers, null)
        return sslContext
    }
    companion object {
        private const val CERT_FILE_PATH = "db-certs/global-bundle-"
        private const val CERT_FILE_PATH_POSTFIX = ".pem"
        private const val END_OF_CERTIFICATE_DELIMITER = "-----END CERTIFICATE-----"
        private const val CERTIFICATE_TYPE = "X.509"
        private const val TLS_PROTOCOL = "TLS"
    }
}
*/
