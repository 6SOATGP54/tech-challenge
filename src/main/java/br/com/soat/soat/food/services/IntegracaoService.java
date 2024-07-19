package br.com.soat.soat.food.services;

import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import br.com.soat.soat.food.model.embeddable.DiaDaSemana;
import br.com.soat.soat.food.model.embeddable.Intervalo;
import br.com.soat.soat.food.model.embeddable.Location;
import br.com.soat.soat.food.repository.integracoes.CredenciaisIntegracaoRepository;
import br.com.soat.soat.food.repository.integracoes.LojaMercadoLivreRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IntegracaoService {

    @Autowired
    CredenciaisIntegracaoRepository credenciaisIntegracaoRepository;

    @Autowired
    LojaMercadoLivreRepository lojaMercadoLivreRepository;

    public Boolean cadastroCredenciais(CredenciaisAcesso credenciaisAcesso) {
        CredenciaisAcesso save = credenciaisIntegracaoRepository.save(credenciaisAcesso);
        return save.getId() != null;
    }

    public EscopoLojaMercadoPago cadastroLojaMercadoLivre(EscopoLojaMercadoPago escopoLojaMercadoPago) {
        return lojaMercadoLivreRepository.save(escopoLojaMercadoPago);
    }


    public List<EscopoLojaMercadoPagoDTO> buscarLojaMercadoLivre() {
        return lojaMercadoLivreRepository.findAll().stream().map(escopoLojaMercadoPago -> {
            Map<Object, Object> businessHours = escopoLojaMercadoPago.getBusinessHours().stream()
                    .collect(Collectors.toMap(DiaDaSemana::getDia,
                            diaDaSemana -> diaDaSemana.getIntervalos().stream()
                                    .map(i -> new IntervalosDTO(i.getOpen(), i.getClose()))
                                    .collect(Collectors.toList())));

            Map<Object, Object> location = getLocation(escopoLojaMercadoPago);

            return new EscopoLojaMercadoPagoDTO(
                    escopoLojaMercadoPago.getName(),
                    businessHours,
                    location,
                    escopoLojaMercadoPago.getExternalId()
            );
        }).collect(Collectors.toList());
    }

    private static Map<Object, Object> getLocation(EscopoLojaMercadoPago escopoLojaMercadoPago) {
        Location locationSalvo = escopoLojaMercadoPago.getLocation();
        Map<Object, Object> location = Map.of(
                "city_name", locationSalvo.getCityName(),
                "state_name", locationSalvo.getStateName(),
                "street_name", locationSalvo.getStreetName(),
                "street_number", locationSalvo.getStreetNumber(),
                "longitude", locationSalvo.getLongitude(),
                "latitude", locationSalvo.getLatitude(),
                "reference", locationSalvo.getReference()
        );
        return location;
    }

    public record EscopoLojaMercadoPagoDTO(String name,
                                           @JsonProperty("business_hours") Map<Object, Object> businessHours,
                                           Map<Object, Object> location,
                                           @JsonProperty("external_id") String externalId) {

    }

    private record IntervalosDTO(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime open,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime close){

    }

}
