package site.todayfin.alphaapiserver.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.test.context.TestPropertySource;
import site.todayfin.alphaapiserver.AlphaApiServerApplication;
import site.todayfin.alphaapiserver.model.Stock;
import site.todayfin.alphaapiserver.repository.alphavantage.ExchangeRatesRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.USgdpRepository;
import site.todayfin.alphaapiserver.repository.coin.CoinRepository;
import site.todayfin.alphaapiserver.repository.stock.StockRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AlphaApiServerApplication.class})
@TestPropertySource("classpath:application.properties")
public class MultipleDbUnitTest {

    @Autowired
    MarketMoversRepository marketMoversRepository;
    @Autowired
    ExchangeRatesRepository exchangeRatesRepository;
    @Autowired
    CoinRepository coinRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    USgdpRepository usgdpRepository;
    @Autowired
    @Qualifier("stockMongoDBFactory")
    MongoDatabaseFactory stockMongoDBFactory;

    @Test
    void whenFindMarketMoversByDate_thenIdOK(){
        assertEquals("66c6a68e0cfd2fdd7c79429e", marketMoversRepository.findByDate("2024-08-22").getId().toString());
    }

    @Test
    void whenFindExchangeRatessByDate_thenIdOK(){
        assertEquals("66c695f3b75dfeede20e0386", exchangeRatesRepository.findByDate("2024-08-22").getId().toString());
    }

    @Test
    void stockDB_Test(){
        MongoDatabase stockDB = stockMongoDBFactory.getMongoDatabase();
        List<Stock> stocks = new ArrayList<>();

        assertNotNull(stockMongoDBFactory);

        assertEquals(25,stockDB.listCollectionNames().into(new ArrayList<>()).size());

        int count=0;
        for(String collectionName : stockDB.listCollectionNames()){
            MongoCollection<Document> collection = stockDB.getCollection(collectionName);
            MongoCursor<Document> cursor = collection.find(new Document("date","2024-08-26")).iterator();
            try{
                while(cursor.hasNext()){
                    Document document = cursor.next();
                    Stock stock = new Stock();
                    stock.setDate(document.getString("date"));
                    stock.setName(document.getString("name"));
                    stock.setLast_refreshed(document.getString("last_refreshed"));
                    stock.setInterval(document.getString("interval"));
                    stock.setData(document.get("stock_data").toString());

                    stocks.add(stock);
                }
            }finally {
                cursor.close();
            }

            assertEquals(collectionName,"stock_"+ stocks.get(count).getName());
            count++;
        }
    }

    @Test
    void whenFindCoinByDate_thenFromCurrencyCodeOK(){
        assertEquals("AAVE", coinRepository.findByDate("2024-08-22").getFrom_currency_code());
    }

//    @Test
//    void whenFindUSgdpByDate_thenIDOK(){
//        assertEquals("66c5979019c5926c60b8fd7e", usgdpRepository.findAll().get(0).getId().toString());
//    }
}
