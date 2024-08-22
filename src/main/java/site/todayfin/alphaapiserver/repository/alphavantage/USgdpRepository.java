package site.todayfin.alphaapiserver.repository.alphavantage;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.todayfin.alphaapiserver.model.USgdp;

import java.util.Optional;

@Repository
public interface USgdpRepository extends MongoRepository<USgdp, String> {
}
