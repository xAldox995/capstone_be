package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.exceptions.BadRequestException;
import org.hibernate.proxy.map.MapProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CryptoCompareServ {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${fetch.url.price}")
    private String fetchUrlBasePrice;//->corrisponde a questo URL: https://min-api.cryptocompare.com/data/price
    @Value("${fetch.url,price.details}")
    private String fetchUrlBasePriceDetails;//->corrisponde a questo URL: https://min-api.cryptocompare.com/data/pricemultifullocompare.com/data/price
    @Value("${fetch.url.hisory}")
    private String fetchUrlBaseHystory;//->corrisponde a questo URL: https://min-api.cryptocompare.com/data/v2/histoday
    @Value("${fetch.url.volume}")
    private String fetchUrlBaseVolume;//->corrisponde a questo URL: https://min-api.cryptocompare.com/data/top/totalvolfull
    @Value("${api.key}")
    private String apiKey;

    public Map<String, Double> getCryptoPrice(String symbol, String currency) {
        try {
            String fullUrl = fetchUrlBasePrice + "?fsym=" + symbol + "&tsyms=" + currency + "&api_key=" + apiKey;
            List<String> currencyList = List.of(currency.split(","));
            Map<String, Double> response = restTemplate.getForObject(fullUrl, Map.class);
            System.out.println("Response from CryptoCompare API: " + response);
            if (response == null || currencyList.stream().noneMatch(cur -> response.containsKey(cur))) {
                throw new BadRequestException("La valuta richiesta non è disponibile o l'API non ha restituito un valore valido");
            }
            return response;
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il recupero dei dati da CryptoCompare: " + e.getMessage());
        }
    }

    public Map<String, Object> getCryptoPriceDetails(String symbols, String currencies) {
        try {
            String fullUrl = fetchUrlBasePriceDetails +
                    "?fsyms=" + symbols + "&tsyms=" + currencies + "&api_key=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(fullUrl, Map.class);
            System.out.println("Response from CryptoCompare API (Price Details): " + response);
            if (response == null || !response.containsKey("RAW")) {
                throw new BadRequestException("Dati di prezzo non disponibili o invalidi.");
            }
            return response;
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il recupero dei dettagli sui prezzi: " + e.getMessage());
        }
    }

    public Map<String, Object> getCryptoMercatoHystory(String symbol, String currency, int limit) {
        try {
            String fullUrl = fetchUrlBaseHystory + "?fsym=" + symbol + "&tsym=" + currency + "&limit=" + limit + "&api_key=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(fullUrl, Map.class);
            System.out.println("Response from CryptoCompare API (Market History): " + response);
            if (response == null || !response.containsKey("Data")) {
                throw new BadRequestException("L'API non ha restituito dati validi per l'andamento di mercato.");
            }
            return response;
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il recupero dei dati storici da CryptoCompare: " + e.getMessage());
        }
    }

    public Map<String, Object> getTopCryptosByVolume(String currency, int limit) {
        try {
            String fullUrl = fetchUrlBaseVolume + "?limit=" + limit + "&tsym=" + currency + "&api_key=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(fullUrl, Map.class);
            System.out.println("Response from CryptoCompare API (Top Cryptos by Volume): " + response);
            if (response == null || !response.containsKey("Data")) {
                throw new BadRequestException("Non ci sono dati disponibili per il volume di mercato.");
            }
            return response;
        } catch (Exception e) {
            throw new BadRequestException("Errore durante il recupero delle criptovalute per volume: " + e.getMessage());
        }
    }
}
