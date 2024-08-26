package site.todayfin.alphaapiserver.service;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import site.todayfin.alphaapiserver.model.ExchangeRates;
import site.todayfin.alphaapiserver.model.MarketMovers;
import site.todayfin.alphaapiserver.model.Stock;
import site.todayfin.alphaapiserver.model.USgdp;
import site.todayfin.alphaapiserver.repository.alphavantage.ExchangeRatesRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.USgdpRepository;
import site.todayfin.alphaapiserver.storage.DateStorage;

import java.util.ArrayList;
import java.util.List;


@Service
public class AlphaVantageService {
    @Autowired
    private DateStorage dateStorage;
    @Autowired
    private MarketMoversRepository marketMoversRepository;
    @Autowired
    private USgdpRepository usgdpRepository;
    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;
    @Autowired
    @Qualifier("stockMongoDBFactory")
    MongoDatabaseFactory stockMongoDBFactory;
    private Gson gson = new Gson();

    public String getMarketMovers(){
        String date = dateStorage.getDate();
        MarketMovers marketMovers = marketMoversRepository.findByDate(date);
        return formatMarketMoversToJson(marketMovers);
    }
    private String formatMarketMoversToJson(MarketMovers marketMovers){
        if (marketMovers ==null) return "{}";

        return String.format("{\"top_gainers\": [%s], \"top_losers\": [%s]}",
                marketMovers.getTop_gainers(),
                marketMovers.getTop_losers());
    }

    public String getUSGDP(){
        USgdp usgdp = usgdpRepository.findAll().get(0);
        return formatUSGDPToJson(usgdp);
    }
    private String formatUSGDPToJson(USgdp usgdp){
        if (usgdp ==null) return "{}";

        return String.format("{\"name\": \"%s\",\"interval\": \"%s\" ,\"unit\": \"%s\",\"data\": [%s]}",
                usgdp.getName(),
                usgdp.getInterval(),
                usgdp.getUnit(),
                usgdp.getData());
    }

    public String getExchangeRates(){
        String date = dateStorage.getDate();
        ExchangeRates exchangeRates = exchangeRatesRepository.findByDate(date);
        return formatExchangeRatesToJson(exchangeRates);
    }
    private String formatExchangeRatesToJson(ExchangeRates exchangeRates){
        if (exchangeRates ==null) return "{}";

        return String.format("{[%s]}",exchangeRates.getRates());
    }

    public String getStocks(){
        String date = dateStorage.getDate();
        MongoDatabase stockDB = stockMongoDBFactory.getMongoDatabase();
        List<Stock> stocks = new ArrayList<>();

        for(String collectionName : stockDB.listCollectionNames()){
            MongoCollection<Document> collection = stockDB.getCollection(collectionName);
            MongoCursor<Document> cursor = collection.find(new Document("date",date)).iterator();
            try{
                while(cursor.hasNext()){
                    Document document = cursor.next();
                    Stock stock = new Stock();
                    stock.setDate(document.getString("date"));
                    stock.setName(document.getString("name"));
                    stock.setLast_refreshed(document.getString("last_refreshed"));
                    stock.setInterval(document.getString("interval"));
                    List<Document> stockData = document.getList("stock_data", Document.class);
                    stock.setData(gson.toJson(stockData));

                    stocks.add(stock);
                }
            }finally {
                cursor.close();
            }
        }

        return formatStocksToJson(stocks);
    }
    private String formatStocksToJson(List<Stock> stocks){
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            sb.append("{");
            sb.append("\"name\": \"").append(stock.getName()).append("\",");
            sb.append("\"last_refreshed\": \"").append(stock.getLast_refreshed()).append("\",");
            sb.append("\"interval\": \"").append(stock.getInterval()).append("\",");
            sb.append("\"data\": ").append(stock.getData());

            sb.append("}");
            if (i < stocks.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
