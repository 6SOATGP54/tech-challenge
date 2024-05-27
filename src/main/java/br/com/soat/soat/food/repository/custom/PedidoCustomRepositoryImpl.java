package br.com.soat.soat.food.repository.custom;

import br.com.soat.soat.food.controller.PedidoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoCustomRepositoryImpl implements PedidoCustomRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<PedidoController.PedidosRecebidosDTO> listaPedidos() {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.id ");
        sql.append("       AS id, ");
        sql.append("       p.data_cadastro ");
        sql.append("       AS data_cadastro, ");
        sql.append("       c.nome ");
        sql.append("       AS nome_cliente, ");
        sql.append("       String_agg(Concat(p2.nome, ',', p2.categoria, ',', pp.quantidade), ';; ') ");
        sql.append("       AS ");
        sql.append("       descricao_produtos ");
        sql.append("FROM   pedido p ");
        sql.append("       LEFT JOIN pedido_produto pp ");
        sql.append("              ON p.id = pp.pedido_id ");
        sql.append("       LEFT JOIN produto p2 ");
        sql.append("              ON p2.id = pp.produto_id ");
        sql.append("       LEFT JOIN cliente c ");
        sql.append("              ON c.id = p.cliente ");
        sql.append("WHERE  p.status_pedido = 'RECEBIDO' ");
        sql.append("GROUP  BY p.id, ");
        sql.append("          p.data_cadastro, ");
        sql.append("          c.nome ");
        sql.append("ORDER  BY p.data_cadastro");


        List<PedidosRecebidos> pedidosRegatadosList = jdbcTemplate.query(sql.toString(), (rs, rowNum) ->
                new PedidosRecebidos(
                        rs.getLong("id"),
                        rs.getTimestamp("data_cadastro").toLocalDateTime(),
                        rs.getString("nome_cliente"),
                        rs.getString("descricao_produtos")));

        List<PedidoController.PedidosRecebidosDTO> pedidosRecebidos = new ArrayList<>();

        if (!pedidosRegatadosList.isEmpty()) {

            for (PedidosRecebidos pedidoRegatado : pedidosRegatadosList) {

                PedidoController.PedidosRecebidosDTO gerarPedido = getPedidosRecebidos(pedidoRegatado);

                pedidosRecebidos.add(gerarPedido);
            }

            return pedidosRecebidos;

        }

        return pedidosRecebidos;

    }

    private static PedidoController.PedidosRecebidosDTO getPedidosRecebidos(PedidosRecebidos pedidoRegatado) {

        String[] produtos = pedidoRegatado.produtos().split(";;");

        List<PedidoController.ProdutoDTO> produtosSolicitados = new ArrayList<>();

        for (String produto : produtos) {
            String[] detalhesDoProduto = produto.split(",");

            produtosSolicitados.add(new PedidoController.ProdutoDTO(detalhesDoProduto[0], detalhesDoProduto[1], detalhesDoProduto[2]));
        }

        return new PedidoController.PedidosRecebidosDTO(
                pedidoRegatado.id(),
                pedidoRegatado.dataCadastro(),
                pedidoRegatado.nomeCliente(),
                produtosSolicitados);

    }

    private record PedidosRecebidos(Long id, LocalDateTime dataCadastro, String nomeCliente, String produtos) {
    }
}
