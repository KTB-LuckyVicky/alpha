package site.todayfin.alphaapiserver.repository.alphavantage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.todayfin.alphaapiserver.model.ExchangeRates;

@Repository
public interface ExchangeRatesRepository extends MongoRepository<ExchangeRates, String> {
    ExchangeRates findByDate(String date);
}
