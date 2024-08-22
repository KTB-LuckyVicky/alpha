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
import site.todayfin.alphaapiserver.repository.stock.StockRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = StockRepository.class, mongoTemplateRef = "stockMongoTemplate")
@EnableConfigurationProperties
public class StockDBConfig {
    @Bean(name = "stockProperties")
    @ConfigurationProperties(prefix = "mongodb.stock")
    public MongoProperties stockProperties() {
        return new MongoProperties();
    }
    @Bean(name = "stockMongoClient")
    public MongoClient mongoClient(@Qualifier("stockProperties") MongoProperties mongoProperties) {
        // MongoDB URI 생성
        String uri = "mongodb://" + mongoProperties.getHost() + ":" + mongoProperties.getPort();

        // MongoClientSettings를 사용하여 MongoClient 인스턴스 생성
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build());
    }

    @Bean(name = "stockMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("stockMongoClient") MongoClient mongoClient,
            @Qualifier("stockProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(name = "stockMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("stockMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
