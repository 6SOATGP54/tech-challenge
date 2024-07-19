package br.com.soat.soat.food.controller.integracoes;

import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import br.com.soat.soat.food.services.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/integracoes")
public class ConfiguracaoIntegracoes {

    @Autowired
    IntegracaoService integracaoService;

    @PostMapping("/cadastroCredenciais")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> cadastroCredenciais(@Validated @RequestBody CredenciaisAcesso credenciaisAcesso) {
        return ResponseEntity.ok(integracaoService.cadastroCredenciais(credenciaisAcesso));
    }

    @PostMapping("/cadastroLojaMercadoLivre")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EscopoLojaMercadoPago> cadastroLojaMercadoLivre(@Validated @RequestBody EscopoLojaMercadoPago escopoLojaMercadoPago) {
        return ResponseEntity.ok(integracaoService.cadastroLojaMercadoLivre(escopoLojaMercadoPago));
    }

    @PostMapping("/buscarLojaMercadoLivre")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<IntegracaoService.EscopoLojaMercadoPagoDTO>> buscarLojaMercadoLivre() {
        return ResponseEntity.ok(integracaoService.buscarLojaMercadoLivre());
    }


}
