package site.todayfin.alphaapiserver.repository.coin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.todayfin.alphaapiserver.model.Coin;

@Repository
public interface CoinRepository extends MongoRepository<Coin,String> {
    Coin findByDate(String date);
}
