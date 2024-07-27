package br.com.soat.soat.food.controller.integracoes;

import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoCaixaMercadoPago;
import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import br.com.soat.soat.food.services.IntegracaoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integracoes/mercadoPago")
public class ConfiguracaoIntegracoes {

    @Autowired
    IntegracaoService integracaoService;

    @Operation(summary = "Cadastrar credenciais",
            description = "Passar USER_ID e TOKEN fornecidos na plataforma do mercado pago")
    @PostMapping("/cadastroCredenciais")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> cadastroCredenciais(@Validated @RequestBody CredenciaisAcesso credenciaisAcesso) {
        return ResponseEntity.ok(integracaoService.cadastroCredenciais(credenciaisAcesso));
    }

    @Operation(summary = "Cadastrar loja",
            description = "No body NÃO é necessario ID e DATA CADASTRO é criado automaticamente pelo sistema")
    @PostMapping("/cadastroLojaMercadoPago")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EscopoLojaMercadoPago> cadastroLojaMercadoLivre(@Validated @RequestBody EscopoLojaMercadoPago escopoLojaMercadoPago) {
        return ResponseEntity.ok(integracaoService.cadastroLojaMercadoLivre(escopoLojaMercadoPago));
    }

    @Operation(summary = "Cadastrar caixa",
        description = "No body NÃO é necessario ID e DATA CADASTRO é criado automaticamente pelo sistema." +
                       "store_id: Id da loja")
    @PostMapping("/cadastrarCaixaLojaMercadoLivre")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EscopoCaixaMercadoPago> cadastrarCaixaLojaMercadoLivre(@Validated @RequestBody
                                                                                     EscopoCaixaMercadoPago escopoCaixaMercadoPago) {
        return ResponseEntity.ok(integracaoService.cadastrarCaixaLojaMercadoLivre(escopoCaixaMercadoPago));
    }

    @PostMapping("/pagamentoRecebido")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCaixaLojaMercadoLivre(@RequestParam("id") Object id,
                                               @RequestParam("topic") Object type) {
        integracaoService.consultarPagamento(id,type);
    }





}
