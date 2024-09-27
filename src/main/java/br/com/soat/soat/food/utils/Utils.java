package br.com.soat.soat.food.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

public abstract class Utils {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> ResponseEntity<T> request(String url,
                                                HttpMethod httpMethod,
                                                Object body,
                                                Class<T> responseType) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);

        try{
            return restTemplate.exchange(url, httpMethod, httpEntity, responseType);
        }catch (Exception e){
            Logger.getAnonymousLogger().severe("ERRO ".concat(e.getMessage()));
        }

        return null;
    }


}
