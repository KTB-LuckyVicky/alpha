package site.todayfin.alphaapiserver.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "us_gdp")
@Data
public class USgdp {
    @MongoId
    private ObjectId id;

    private String name;
    private String date;
    private String interval;
    private String unit;
    private String data;
}
