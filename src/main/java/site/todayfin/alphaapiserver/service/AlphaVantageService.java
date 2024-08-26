package site.todayfin.alphaapiserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.todayfin.alphaapiserver.model.ExchangeRates;
import site.todayfin.alphaapiserver.model.MarketMovers;
import site.todayfin.alphaapiserver.model.USgdp;
import site.todayfin.alphaapiserver.repository.alphavantage.ExchangeRatesRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.USgdpRepository;
import site.todayfin.alphaapiserver.storage.DateStorage;


@Service
public class AlphaVantageService {
    @Autowired
    private DateStorage dateStorage;
    @Autowired
    private MarketMoversRepository marketMoversRepository;
    @Autowired
    private USgdpRepository usgdpRepository;
    @Autowired
    private ExchangeRatesRepository exchangeRatesRepository;

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

    public String getUSGDP(){
        USgdp usgdp = usgdpRepository.findAll().get(0);
        return formatUSGDPToJson(usgdp);
    }
    private String formatUSGDPToJson(USgdp usgdp){
        if (usgdp ==null) return "{}";

        return String.format("{\"name\": \"%s\",\"interval\": \"%s\" ,\"unit\": \"%s\",\"data\": [%s]}",
                usgdp.getName(),
                usgdp.getInterval(),
                usgdp.getUnit(),
                usgdp.getData());
    }

    public String getExchangeRates(){
        String date = dateStorage.getDate();
        ExchangeRates exchangeRates = exchangeRatesRepository.findByDate(date);
        return formatExchangeRatesToJson(exchangeRates);
    }
    private String formatExchangeRatesToJson(ExchangeRates exchangeRates){
        if (exchangeRates ==null) return "{}";

        return String.format("{[%s]}",exchangeRates.getRates());
    }
}
