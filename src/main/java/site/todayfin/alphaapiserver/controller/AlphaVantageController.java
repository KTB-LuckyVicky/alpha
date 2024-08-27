package site.todayfin.alphaapiserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.todayfin.alphaapiserver.service.AlphaVantageService;
import site.todayfin.alphaapiserver.storage.DateStorage;

@RequestMapping("/alphavantage")
@Controller
public class AlphaVantageController {

    @Autowired
    private AlphaVantageService alphaVantageService;
    @Autowired
    private DateStorage dateStorage;

    @ResponseBody
    @GetMapping("top-gainers-losers")
    public String topGainersLosers() {
        return alphaVantageService.getMarketMovers();
    }

    @ResponseBody
    @GetMapping("real-gdps")
    public String realGdps(){
        return alphaVantageService.getUSGDP();
    }

    @ResponseBody
    @GetMapping("exchange-rates")
    public String exchangeRates(){
        return alphaVantageService.getExchangeRates();
    }

    @ResponseBody
    @GetMapping("stocks")
    public String stocks(){
        return alphaVantageService.getStocks();
    }

    @ResponseBody
    @GetMapping("coins")
    public String coins(){
        return alphaVantageService.getCoins();
    }

    @ResponseBody
    @GetMapping("date")
    public String date(){
        return dateStorage.getDate();
    }

}
