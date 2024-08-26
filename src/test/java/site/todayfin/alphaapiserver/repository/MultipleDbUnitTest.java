package site.todayfin.alphaapiserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import site.todayfin.alphaapiserver.AlphaApiServerApplication;
import site.todayfin.alphaapiserver.repository.alphavantage.ExchangeRatesRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.MarketMoversRepository;
import site.todayfin.alphaapiserver.repository.alphavantage.USgdpRepository;
import site.todayfin.alphaapiserver.repository.coin.CoinRepository;
import site.todayfin.alphaapiserver.repository.stock.StockRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AlphaApiServerApplication.class})
@TestPropertySource("classpath:application.properties")
public class MultipleDbUnitTest {

    @Autowired
    MarketMoversRepository marketMoversRepository;

    @Autowired
    ExchangeRatesRepository exchangeRatesRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    USgdpRepository usgdpRepository;

    @Test
    void whenFindMarketMoversByDate_thenIdOK(){
        assertEquals("66c6a68e0cfd2fdd7c79429e", marketMoversRepository.findByDate("2024-08-22").getId().toString());
    }

    @Test
    void whenFindExchangeRatessByDate_thenIdOK(){
        assertEquals("66c695f3b75dfeede20e0386", exchangeRatesRepository.findByDate("2024-08-22").getId().toString());
    }

    @Test
    void whenFindStockByDate_thenNameOK(){
        assertEquals("AAPL", stockRepository.findByDate("2024-08-22").getName());
    }

    @Test
    void whenFindCoinByDate_thenFromCurrencyCodeOK(){
        assertEquals("AAVE", coinRepository.findByDate("2024-08-22").getFrom_currency_code());
    }

//    @Test
//    void whenFindUSgdpByDate_thenIDOK(){
//        assertEquals("66c5979019c5926c60b8fd7e", usgdpRepository.findAll().get(0).getId().toString());
//    }
}
