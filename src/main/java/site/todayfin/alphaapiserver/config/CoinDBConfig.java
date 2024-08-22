package site.todayfin.alphaapiserver.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import site.todayfin.alphaapiserver.repository.coin.CoinRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = CoinRepository.class, mongoTemplateRef = "coinMongoTemplate")
@EnableConfigurationProperties
public class CoinDBConfig {
    @Bean(name = "coinProperties")
    @ConfigurationProperties(prefix = "mongodb.coin")
    public MongoProperties coinProperties() {
        return new MongoProperties();
    }
    @Bean(name = "coinMongoClient")
    public MongoClient mongoClient(@Qualifier("coinProperties") MongoProperties mongoProperties) {
        // MongoDB URI 생성
        String uri = "mongodb://" + mongoProperties.getHost() + ":" + mongoProperties.getPort();

        // MongoClientSettings를 사용하여 MongoClient 인스턴스 생성
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build());
    }

    @Bean(name = "coinMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("coinMongoClient") MongoClient mongoClient,
            @Qualifier("coinProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(name = "coinMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("coinMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
