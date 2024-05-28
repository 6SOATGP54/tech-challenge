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
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(pedidoDTO.id());
        int pedidoAtualizado = 0;

        if (pedidoEncontrado.isPresent()) {
            StatusPedido statusPedido = pedidoEncontrado.get().getStatusPedido();
            switch (statusPedido) {
                case RECEBIDO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.PREPARACAO);
                    break;
                case PREPARACAO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.PRONTO);
                    break;
                case PRONTO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.FINALIZADO);
                    break;
            }

            pedidoAtualizado = pedidoRepository.statusPedido(pedidoEncontrado.get().getStatusPedido(), pedidoEncontrado.get().getId());
        }

        return pedidoAtualizado == 0 ? new Pedido() : pedidoEncontrado.get();
    }

    public List<PedidoController.PedidosRecebidosDTO> pesquisarPedidosRecebidos() {
        return pedidoProdutoRepository.listaPedidos();
    }

    public List<Pedido> pesquisarPedidosProntos() {
        return pedidoRepository.findByStatusPedido(StatusPedido.PRONTO);
    }

    public List<Pedido> pesquisarPedidosEmPreparacao() {
        return pedidoRepository.findByStatusPedido(StatusPedido.PREPARACAO);
    }
}
