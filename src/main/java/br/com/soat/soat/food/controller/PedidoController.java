package br.com.soat.soat.food.controller;


import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.enums.StatusAcompanhamento;
import br.com.soat.soat.food.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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



    @PutMapping("/atualizarStatusPedido")
    public ResponseEntity<Pedido> atualizarStatusPedido(@Validated @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.atualizarStatusPedido(pedidoDTO));
    }


    public record  PedidoDTO(Long id, StatusAcompanhamento status) {
    }
}
