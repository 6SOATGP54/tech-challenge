package br.com.soat.soat.food.services;

import br.com.soat.soat.food.enums.EndpointsIntegracaoEnum;
import br.com.soat.soat.food.model.CredenciaisAcesso;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RequestServices {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static Object criarLoja(Object request,
                                   CredenciaisAcesso credenciaisAcesso,
                                   String endpoint,
                                   HttpMethod httpMethod,
                                   EndpointsIntegracaoEnum endpointsIntegracaoEnum) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer ".concat(credenciaisAcesso.getToken()));
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(endpoint, httpMethod, httpEntity, String.class);

            Object body = response.getBody();

            if (body instanceof LinkedHashMap<?, ?>) {
                LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) body;
                return map.get("id");
            }

            if (EndpointsIntegracaoEnum.CRIAR_CAIXA.equals(endpointsIntegracaoEnum.CRIAR_CAIXA)) {
                JSONObject jsonObject = new JSONObject(response);
                Map<String, Object> map = jsonObject.toMap();

                AtomicReference<Long> id = new AtomicReference<>();

                map.forEach((key, value) -> {
                    if (key.equals("body")) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = null;
                        try {
                            rootNode = objectMapper.readTree(value.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        id.set(rootNode.path("id").asLong());
                    }
                });

                return id.get() != null ? id.get() : 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
