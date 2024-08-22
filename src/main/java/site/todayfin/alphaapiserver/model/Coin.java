package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "coin_AAVE")
@Data
public class Coin {
    @MongoId
    private ObjectId id;

    private String date;
    private String from_currency_code;
    private String from_currency_name;
    private String to_currency_code;
    private String exchange_rate_usd;
    private String exchange_rate_krw;
    private String bid_price_usd;
    private String bid_price_krw;
    private String ask_price_usd;
    private String ask_price_krw;
    private String last_refreshed;
}
