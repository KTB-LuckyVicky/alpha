package site.todayfin.alphaapiserver.repository.alphavantage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.todayfin.alphaapiserver.model.MarketMovers;

@Repository
public interface MarketMoversRepository extends MongoRepository<MarketMovers, String> {
    MarketMovers findByDate(String date);
}
