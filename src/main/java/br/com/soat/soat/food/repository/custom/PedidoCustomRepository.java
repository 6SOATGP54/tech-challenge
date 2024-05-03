package br.com.soat.soat.food.repository.custom;

import br.com.soat.soat.food.controller.PedidoController;

import java.util.List;

public interface PedidoCustomRepository {

    List<PedidoController.PedidosRecebidosDTO> listaPedidos();
}
