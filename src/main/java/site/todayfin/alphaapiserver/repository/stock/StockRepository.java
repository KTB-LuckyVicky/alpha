package site.todayfin.alphaapiserver.repository.stock;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.todayfin.alphaapiserver.model.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock,String > {
    Stock findByDate(String date);
}
