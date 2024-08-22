package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "exchange_rate")
@Data
public class ExchangeRates {
    @MongoId
    private ObjectId id;

    private String date;

    private String rates;
}
