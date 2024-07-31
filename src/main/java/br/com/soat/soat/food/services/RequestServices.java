package br.com.soat.soat.food.services;

import br.com.soat.soat.food.enums.EndpointsIntegracaoEnum;
import br.com.soat.soat.food.model.CredenciaisAcesso;
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
import java.util.logging.Logger;

@Service
public class RequestServices {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static Object requestToMercadoPago(Object request,
                                              CredenciaisAcesso credenciaisAcesso,
                                              String endpoint,
                                              HttpMethod httpMethod,
                                              EndpointsIntegracaoEnum endpointsIntegracaoEnum) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer ".concat(credenciaisAcesso.getToken()));
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);

        Object body = null;
        try {
            ResponseEntity<String> response = restTemplate.exchange(endpoint, httpMethod, httpEntity, String.class);

            body = response.getBody();

            if (EndpointsIntegracaoEnum.CRIAR_CAIXA.equals(endpointsIntegracaoEnum) ||
                    EndpointsIntegracaoEnum.CRIAR_LOJA.equals(endpointsIntegracaoEnum)) {
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

                return id.get() != null ? id.get() : null;
            }

            if (EndpointsIntegracaoEnum.GERARQRCODE.equals(endpointsIntegracaoEnum)) {
                JSONObject jsonObject = new JSONObject(response);
                Map<String, Object> map = jsonObject.toMap();

                AtomicReference<String> qrCode = new AtomicReference<>();

                map.forEach((key, value) -> {
                    if (key.equals("body")) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode;
                        try {
                            rootNode = objectMapper.readTree(value.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        qrCode.set(rootNode.path("qr_data").asText());
                    }
                });

                return qrCode.get() != null ? qrCode.get() : null;
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
