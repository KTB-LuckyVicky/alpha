package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
public class Stock {
    @MongoId
    private ObjectId id;
    private String date;
    private String name;
    private String last_refreshed;
    private String interval;
    private String data;
}
