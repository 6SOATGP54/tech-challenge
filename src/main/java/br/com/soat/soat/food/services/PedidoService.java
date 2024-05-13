package br.com.soat.soat.food.services;


import br.com.soat.soat.food.controller.PedidoController;
import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.PedidoProduto;
import br.com.soat.soat.food.model.enums.StatusPedido;
import br.com.soat.soat.food.repository.PedidoProdutoRepository;
import br.com.soat.soat.food.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    public Pedido cadastroEupdatePedido(Pedido pedido) {
        List<PedidoProduto> listaItens = new ArrayList<>();

        for (PedidoProduto produto : pedido.getPedidoProdutos()) {
            listaItens.add(produto);
        }

        Pedido salvarPedido = new Pedido();
        salvarPedido.setCliente(pedido.getCliente());
        salvarPedido.setStatusPedido(pedido.getStatusPedido());
        salvarPedido.setPedidoProdutos(new ArrayList<>());

        Pedido pedidoSalvo = pedidoRepository.save(salvarPedido);

        for (PedidoProduto itens : listaItens) {
            itens.setPedido(pedidoSalvo.getId());
            pedidoProdutoRepository.save(itens);
        }

        pedidoSalvo.setPedidoProdutos(listaItens);

        return  pedidoSalvo;
    }

    public Pedido atualizarStatusPedido(PedidoController.PedidoDTO pedidoDTO) {
        pedidoRepository.statusPedido(pedidoDTO.statusPedido(),pedidoDTO.id());

        Optional<Pedido> pedidoAtualizado = pedidoRepository.findById(pedidoDTO.id());

        return pedidoAtualizado.orElse(null);

    }

    public List<Pedido> pesquisarPedidosRecebidos() {
        return pedidoRepository.findByStatusPedido(StatusPedido.RECEBIDO);
    }
}
