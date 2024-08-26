package site.todayfin.alphaapiserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.todayfin.alphaapiserver.service.AlphaVantageService;

@RequestMapping("/alphavantage")
@Controller
public class AlphaVantageController {

    @Autowired
    private AlphaVantageService alphaVantageService;

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



}
