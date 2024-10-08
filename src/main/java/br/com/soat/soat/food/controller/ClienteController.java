package br.com.soat.soat.food.controller;


import br.com.soat.soat.food.dtos.Response;
import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;


    @PostMapping("/cadastroCliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> insertOrUpdateSala(@Validated @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.cadastroEupdateCliente(cliente));
    }

    @PostMapping("/loginCliente")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Response> logar(@Validated @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.loginCliente(cliente));
    }


    @GetMapping("/pesquisarCliente/{cpf}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> buscarClienteCPF(@PathVariable  Long cpf) {
        return ResponseEntity.ok(clienteService.buscarClienteCPF(cpf));
    }


}
