package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "market_movers")
@Data
public class MarketMovers {
    @MongoId
    private ObjectId id;

    private String date;

    private String top_gainers;

    private String top_losers;
}
