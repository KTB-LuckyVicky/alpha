package site.todayfin.alphaapiserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import site.todayfin.alphaapiserver.AlphaApiServerApplication;

import java.util.*;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AlphaApiServerApplication.class})
@TestPropertySource("classpath:application.properties")
public class MultipleDBConnectionTest {
    @Autowired
    @Qualifier("alphavantageMongoTemplate")
    private MongoTemplate alphavantageMongoTemplate;

    @Autowired
    @Qualifier("coinMongoTemplate")
    private MongoTemplate coinMongoTemplate;

    @Autowired
    @Qualifier("stockMongoTemplate")
    private MongoTemplate stockMongoTemplate;

    @Test
    void alphavantageDBConnectionTest() {
        assertNotNull(alphavantageMongoTemplate);
        // 데이터베이스의 모든 컬렉션 이름 가져오기
        Set<String> collectionNames = alphavantageMongoTemplate.getDb().listCollectionNames().into(new HashSet<>());
        // 컬렉션 개수 확인
        int collectionCount = collectionNames.size();
        // 컬렉션이 3개인지 확인
        assertTrue(collectionCount == 3, "The database should contain 3 collections");
    }

    @Test
    void coinDBConnectionTest() {
        assertNotNull(coinMongoTemplate);
        Set<String> collectionNames = coinMongoTemplate.getDb().listCollectionNames().into(new HashSet<>());
        int collectionCount = collectionNames.size();
        assertTrue(collectionCount == 17, "The database should contain 15 collections");
    }

    @Test
    void stockDBConnectionTest() {
        assertNotNull(stockMongoTemplate);
        Set<String> collectionNames = stockMongoTemplate.getDb().listCollectionNames().into(new HashSet<>());
        int collectionCount = collectionNames.size();
        assertTrue(collectionCount == 25, "The database should contain 25 collections");
    }

    @Test
    void usGdpCollectionItemCountTest() {
        assertNotNull(alphavantageMongoTemplate);
        long itemCount = alphavantageMongoTemplate.getCollection("us_gdp").countDocuments();
        assertTrue(itemCount == 1, "The us_gdp collection should contain exactly 1 item");
    }
}
