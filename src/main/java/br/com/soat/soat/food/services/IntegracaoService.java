package br.com.soat.soat.food.services;

import br.com.soat.soat.food.enums.EndpointsIntegracaoEnum;
import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoCaixaMercadoPago;
import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import br.com.soat.soat.food.model.embeddable.DiaDaSemana;
import br.com.soat.soat.food.model.embeddable.Location;
import br.com.soat.soat.food.repository.integracoes.CaixaMercadoPagoRepository;
import br.com.soat.soat.food.repository.integracoes.CredenciaisIntegracaoRepository;
import br.com.soat.soat.food.repository.integracoes.LojaMercadoLivreRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @Autowired
    CaixaMercadoPagoRepository caixaMercadoPagoRepository;

    public Boolean cadastroCredenciais(CredenciaisAcesso credenciaisAcesso) {
        CredenciaisAcesso save = credenciaisIntegracaoRepository.save(credenciaisAcesso);
        return save.getId() != null;
    }

    public EscopoLojaMercadoPago cadastroLojaMercadoLivre(EscopoLojaMercadoPago escopoLojaMercadoPago) {

        EscopoLojaMercadoPago save = lojaMercadoLivreRepository.save(escopoLojaMercadoPago);

        Optional<EscopoLojaMercadoPagoDTO> escopoLojaMercadoPagoDTO = getEscopoLojaMercadoPagoDTO(save);

        CredenciaisAcesso credenciaisAcesso = getCredenciaisAcesso(escopoLojaMercadoPago);

        return publicarLojaMercadoPago(escopoLojaMercadoPago,
                credenciaisAcesso,
                escopoLojaMercadoPagoDTO);

    }

    private EscopoLojaMercadoPago publicarLojaMercadoPago(EscopoLojaMercadoPago escopoLojaMercadoPago,
                                                          CredenciaisAcesso credenciaisAcesso,
                                                          Optional<EscopoLojaMercadoPagoDTO> escopoLojaMercadoPagoDTO) {

        Map<Object, Object> parametros = Map.of("usuario_acesso", credenciaisAcesso.getUsuario());

        String urlCriarLoja = EndpointsIntegracaoEnum.CRIAR_LOJA.parametrosUrl(parametros);

        Object o =
                RequestServices.criarLoja(escopoLojaMercadoPagoDTO.orElse(null),
                        credenciaisAcesso,
                        urlCriarLoja,
                        HttpMethod.POST,EndpointsIntegracaoEnum.CRIAR_LOJA);


        escopoLojaMercadoPago.setUserId(o instanceof Long ? (Long) o : 0L);

        return lojaMercadoLivreRepository.save(escopoLojaMercadoPago);
    }

    private CredenciaisAcesso getCredenciaisAcesso(EscopoLojaMercadoPago escopoLojaMercadoPago) {

        Long credenciaisId = escopoLojaMercadoPago.getCredenciaisId();

        CredenciaisAcesso credenciaisAcesso =
                credenciaisIntegracaoRepository.findById(credenciaisId).orElse(new CredenciaisAcesso());
        return credenciaisAcesso;
    }

    private Optional<EscopoLojaMercadoPagoDTO> getEscopoLojaMercadoPagoDTO(EscopoLojaMercadoPago save) {

        List<EscopoLojaMercadoPagoDTO> escopoLojaMercadoPagoDTOS = buscarLojaMercadoPago();

        Optional<EscopoLojaMercadoPagoDTO> escopoLojaMercadoPagoDTO = escopoLojaMercadoPagoDTOS.stream()
                .filter(e -> e.externalId().equals(save.getExternalId()))
                .findFirst();
        return escopoLojaMercadoPagoDTO;
    }

    public List<EscopoLojaMercadoPagoDTO> buscarLojaMercadoPago() {
        List<EscopoLojaMercadoPago> all = lojaMercadoLivreRepository.findAll();

        return all.stream().map(escopoLojaMercadoPago -> {
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

    public EscopoCaixaMercadoPago cadastrarCaixaLojaMercadoLivre(EscopoCaixaMercadoPago caixa) {

        EscopoLojaMercadoPago loja = lojaMercadoLivreRepository
                .findByUserId(caixa.getStore_id());


        EscopoCaixaMercadoPago caixaMercadoPagoToDB =
                EscopoCaixaMercadoPago.builder()
                        .external_id(caixa.getExternal_id())
                        .external_store_id(loja.getExternalId())
                        .name(caixa.getName())
                        .store_id(loja.getUserId()).build();

        EscopoCaixaMercadoPago save = caixaMercadoPagoRepository.save(caixaMercadoPagoToDB);

        CaixaLojaDTO caixaLojaDTO = new CaixaLojaDTO(save.getCategory(),
                save.getExternal_id(),
                save.getExternal_store_id(),
                save.getFixed_amount(),
                save.getName(),
                save.getStore_id());


        CredenciaisAcesso credenciaisAcesso = getCredenciaisAcesso(loja);

        String url = EndpointsIntegracaoEnum.CRIAR_CAIXA.getUrl();

        Object o =
                RequestServices.criarLoja(caixaLojaDTO,
                        credenciaisAcesso,
                        url,
                        HttpMethod.POST,EndpointsIntegracaoEnum.CRIAR_CAIXA);

        save.setIdAPI(o instanceof Long ? (Long) o : 0L);

        return caixaMercadoPagoRepository.save(save);
    }

    public record EscopoLojaMercadoPagoDTO(String name,
                                           @JsonProperty("business_hours") Map<Object, Object> businessHours,
                                           Map<Object, Object> location,
                                           @JsonProperty("external_id") String externalId) {
    }

    private record IntervalosDTO(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime open,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime close) {
    }


    public record CaixaLojaDTO(Long category, String external_id, String external_store_id, Boolean fixed_amount,
                               String name, Long store_id) {

    }


}
