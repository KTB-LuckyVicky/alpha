package site.todayfin.alphaapiserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.todayfin.alphaapiserver.model.MarketMovers;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.storage.DateStorage;


@Service
public class AlphaVantageService {
    @Autowired
    private DateStorage dateStorage;

    @Autowired
    private MarketMoversRepository marketMoversRepository;

    public String getMarketMovers(){
        String date = dateStorage.getDate();
        MarketMovers marketMovers = marketMoversRepository.findByDate(date);
        return formatMarketMoversToJson(marketMovers);
    }

    private String formatMarketMoversToJson(MarketMovers marketMovers){
        if (marketMovers ==null) return "{}";

        return String.format("{\"top_gainers\": [%s], \"top_losers\": [%s]}",
                marketMovers.getTop_gainers(),
                marketMovers.getTop_losers());
    }
}
