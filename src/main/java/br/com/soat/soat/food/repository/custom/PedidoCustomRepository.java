package br.com.soat.soat.food.repository.custom;

import br.com.soat.soat.food.controller.PedidoController;
import br.com.soat.soat.food.model.Pedido;

import java.util.List;

public interface PedidoCustomRepository {
    List<PedidoController.PedidosRecebidosDTO> pesquisarPedidosRecebidos();

    List<PedidoController.PedidosEmAbertoDTO> pesquisarPedidosEmAberto();
}
