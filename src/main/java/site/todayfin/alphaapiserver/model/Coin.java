package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
public class Coin {
    @MongoId
    private ObjectId id;

    private String name;
    private String last_refreshed;
    private String rate;
    private String bid;
    private String ask;
}
