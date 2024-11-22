package aldovalzani.capstone_be.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CryptoCompareServ {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${fetch.url}")
    private  String fetchUrlBase;//->corrisponde a questo URL per ora https://min-api.cryptocompare.com/data/price
    @Value("${api.key}")
    private  String apiKey;

    public Map<String, Double>  getCryptoPrice (String symbol, String currency){
       String fullUrl = fetchUrlBase+"?fsym="+symbol+"&tsyms="+currency+"&api_key="+apiKey;
       return restTemplate.getForObject(fullUrl, Map.class);
    }
}
