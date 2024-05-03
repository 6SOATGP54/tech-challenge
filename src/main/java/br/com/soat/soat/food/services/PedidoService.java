package br.com.soat.soat.food.services;


import br.com.soat.soat.food.controller.PedidoController;
import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.PedidoProduto;
import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.model.enums.StatusAcompanhamento;
import br.com.soat.soat.food.repository.PedidoProdutoRepository;
import br.com.soat.soat.food.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        salvarPedido.setAcompanhamento(pedido.getAcompanhamento());
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
        pedidoRepository.acompanhamentoPedido(pedidoDTO.status(),pedidoDTO.id());

        Optional<Pedido> pedidoAtualizado = pedidoRepository.findById(pedidoDTO.id());

        return pedidoAtualizado.orElse(null);

    }

    public List<Pedido> pesquisarPedidosRecebidos() {
        return pedidoRepository.findByAcompanhamento(StatusAcompanhamento.RECEBIDO);
    }
}
