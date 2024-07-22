package br.com.soat.soat.food.services;

import br.com.soat.soat.food.dtos.CaixaDTO;
import br.com.soat.soat.food.dtos.EscopoLojaMercadoPagoDTO;
import br.com.soat.soat.food.dtos.IntervalosDTO;
import br.com.soat.soat.food.enums.EndpointsIntegracaoEnum;
import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoCaixaMercadoPago;
import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import br.com.soat.soat.food.model.embeddable.DiaDaSemana;
import br.com.soat.soat.food.model.embeddable.Location;
import br.com.soat.soat.food.repository.integracoes.CaixaMercadoPagoRepository;
import br.com.soat.soat.food.repository.integracoes.CredenciaisIntegracaoRepository;
import br.com.soat.soat.food.repository.integracoes.LojaMercadoLivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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

        EscopoLojaMercadoPagoDTO escopoLojaMercadoPagoDTO = entityTODTO(escopoLojaMercadoPago);

        CredenciaisAcesso credenciaisAcesso = getCredenciaisAcesso(escopoLojaMercadoPago);

        return publicarLojaMercadoPago(escopoLojaMercadoPago,
                credenciaisAcesso,
                escopoLojaMercadoPagoDTO);

    }

    private EscopoLojaMercadoPago publicarLojaMercadoPago(EscopoLojaMercadoPago escopoLojaMercadoPago,
                                                          CredenciaisAcesso credenciaisAcesso,
                                                          EscopoLojaMercadoPagoDTO escopoLojaMercadoPagoDTO) {

        Map<Object, Object> parametros = Map.of("usuario_acesso", credenciaisAcesso.getUsuario());

        String urlCriarLoja = EndpointsIntegracaoEnum.CRIAR_LOJA.parametrosUrl(parametros);

        Object o =
                RequestServices.criarLoja(escopoLojaMercadoPagoDTO,
                        credenciaisAcesso,
                        urlCriarLoja,
                        HttpMethod.POST,
                        EndpointsIntegracaoEnum.CRIAR_LOJA);


        escopoLojaMercadoPago.setUserId(o instanceof Long ? (Long) o : 0L);

        if(escopoLojaMercadoPago.getUserId() != null){
           return  lojaMercadoLivreRepository.save(escopoLojaMercadoPago);
        }

        return new EscopoLojaMercadoPago();
    }

    private CredenciaisAcesso getCredenciaisAcesso(EscopoLojaMercadoPago escopoLojaMercadoPago) {

        Long credenciaisId = escopoLojaMercadoPago.getCredenciaisId();

        CredenciaisAcesso credenciaisAcesso =
                credenciaisIntegracaoRepository.findById(credenciaisId).orElse(new CredenciaisAcesso());
        return credenciaisAcesso;
    }


    public EscopoLojaMercadoPagoDTO entityTODTO(EscopoLojaMercadoPago escopoLojaMercadoPago) {

        Map<Object, Object> businessHours = escopoLojaMercadoPago.getBusinessHours().stream()
                .collect(Collectors.toMap(DiaDaSemana::getDia,
                        diaDaSemana -> diaDaSemana.getIntervalos().stream()
                                .map(i -> new IntervalosDTO(i.getOpen(), i.getClose()))
                                .collect(Collectors.toList())));

        Map<Object, Object> location = getLocation(escopoLojaMercadoPago);


    return  new EscopoLojaMercadoPagoDTO(
                escopoLojaMercadoPago.getName(),
                businessHours,
                location,
                escopoLojaMercadoPago.getExternalId());

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

        caixa.setExternal_store_id(loja.getExternalId());
        caixa.setStore_id(loja.getUserId());

        CaixaDTO caixaLojaDTO = new CaixaDTO(caixa.getCategory(),
                caixa.getExternal_id(),
                caixa.getExternal_store_id(),
                caixa.getFixed_amount(),
                caixa.getName(),
                caixa.getStore_id());

        CredenciaisAcesso credenciaisAcesso = getCredenciaisAcesso(loja);

        String url = EndpointsIntegracaoEnum.CRIAR_CAIXA.getUrl();

        Object o =
                RequestServices.criarLoja(caixaLojaDTO,
                        credenciaisAcesso,
                        url,
                        HttpMethod.POST,EndpointsIntegracaoEnum.CRIAR_CAIXA);

        caixa.setIdAPI(o instanceof Long ? (Long) o : 0L);

        if(caixa.getIdAPI() != null){
            caixaMercadoPagoRepository.save(caixa);
        }

        return new EscopoCaixaMercadoPago();
    }


}
