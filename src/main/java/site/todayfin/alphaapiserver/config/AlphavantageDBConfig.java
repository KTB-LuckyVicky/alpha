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
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import site.todayfin.alphaapiserver.repository.alphavantage.ExchangeRatesRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.USgdpRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = {MarketMoversRepository.class, ExchangeRatesRepository.class, USgdpRepository.class}, mongoTemplateRef = "alphavantageMongoTemplate")
@EnableConfigurationProperties
public class AlphavantageDBConfig {

    @Bean(name = "alphavantageProperties")
    @ConfigurationProperties(prefix = "mongodb.alphavantage")
    @Primary
    public MongoProperties alphavantageProperties() {
        return new MongoProperties();
    }

    @Bean(name = "alphavantageMongoClient")
    public MongoClient mongoClient(@Qualifier("alphavantageProperties") MongoProperties mongoProperties) {
        // MongoDB URI 생성
        String uri = "mongodb://" + mongoProperties.getHost() + ":" + mongoProperties.getPort();

        // MongoClientSettings를 사용하여 MongoClient 인스턴스 생성
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build());
    }

    @Primary
    @Bean(name = "alphavantageMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("alphavantageMongoClient") MongoClient mongoClient,
            @Qualifier("alphavantageProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "alphavantageMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("alphavantageMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

}
