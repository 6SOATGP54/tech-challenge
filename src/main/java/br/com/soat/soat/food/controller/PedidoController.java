package br.com.soat.soat.food.controller;


import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.enums.StatusPedido;
import br.com.soat.soat.food.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {


    @Autowired
    PedidoService pedidoService;


    @PostMapping("/cadastroPedido")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pedido> insertOrUpdateSala(@Validated @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.cadastroEupdatePedido(pedido));
    }


    @GetMapping("/pesquisarPedidosRecebidos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<PedidosRecebidosDTO>> pesquisarPedidosRecebidos() {
        return ResponseEntity.ok(pedidoService.pesquisarPedidosRecebidos());
    }


    @PutMapping("/atualizarStatusPedido")
    public ResponseEntity<Pedido> atualizarStatusPedido(@Validated @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.atualizarStatusPedido(pedidoDTO));
    }


    public record  PedidoDTO(Long id) {
    }

    public record PedidosRecebidosDTO(Long id, LocalDateTime dataCadastro, String nomeCliente, List<ProdutoDTO> produtos) {
    }

    public record ProdutoDTO(String nomeProduto, String categoria, String quantidade){

    }
}
