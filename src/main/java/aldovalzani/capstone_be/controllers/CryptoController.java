package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.services.CryptoCompareServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
http://localhost:3001/api
 */
@RestController
@RequestMapping("/api")
public class CryptoController {
    @Autowired
    private CryptoCompareServ cryptoCompareServ;

    @GetMapping ("/crypto/price")
    public Map<String,Double> fetchCryptoPrice(
            @RequestParam String symbol,
            @RequestParam (defaultValue ="EUR") String currency){
        return cryptoCompareServ.getCryptoPrice(symbol,currency);
    }

    @GetMapping("/crypto/prices/details")
    public Map<String, Object> fetchCryptoPriceDetails(
            @RequestParam String symbols,
            @RequestParam String currencies) {
        return cryptoCompareServ.getCryptoPriceDetails(symbols, currencies);
    }

    @GetMapping("/crypto/market-history")
    public Map<String, Object> fetchCryptoMarketHistory(
            @RequestParam String symbol,
            @RequestParam (defaultValue = "EUR") String currency,
            @RequestParam(defaultValue = "30") int limit) {
        return cryptoCompareServ.getCryptoMercatoHystory(symbol, currency, limit);
    }

    @GetMapping("/crypto/top/volume")
    public Map<String, Object> fetchTopCryptosByVolume(
            @RequestParam (defaultValue = "EUR") String currency,
            @RequestParam(defaultValue = "10") int limit) {
        return cryptoCompareServ.getTopCryptosByVolume(currency, limit);
    }


}
