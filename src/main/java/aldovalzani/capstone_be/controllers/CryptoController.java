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
            @RequestParam String currency){
        return cryptoCompareServ.getCryptoPrice(symbol,currency);
    }
}
