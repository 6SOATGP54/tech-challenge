package br.com.soat.soat.food.repository.custom;

import br.com.soat.soat.food.controller.PedidoController;
import br.com.soat.soat.food.model.enums.StatusPedido;
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
    public List<PedidoController.PedidosRecebidosDTO> pesquisarPedidosRecebidos() {

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

    @Override
    public List<PedidoController.PedidosEmAbertoDTO> pesquisarPedidosEmAberto() {
        String sql = """
                SELECT p.id AS id,
                       p.data_cadastro AS data_cadastro,
                       c.nome AS nome_cliente,
                       String_agg(Concat(p2.nome, ',', p2.categoria, ',', pp.quantidade), ';; ') AS descricao_produtos,
                       p.status_pedido
                FROM   pedido p
                       LEFT JOIN pedido_produto pp
                              ON p.id = pp.pedido_id
                       LEFT JOIN produto p2
                              ON p2.id = pp.produto_id
                       LEFT JOIN cliente c
                              ON c.id = p.cliente
                WHERE  p.status_pedido in ('PRONTO', 'PREPARACAO', 'RECEBIDO')
                GROUP  BY p.id,
                       p.data_cadastro,
                       c.nome
                ORDER  BY
                    CASE
                        WHEN p.status_pedido = 'PRONTO' THEN 1
                        WHEN p.status_pedido = 'PREPARACAO' THEN 2
                        WHEN p.status_pedido = 'RECEBIDO' THEN 3
                    END,
                    p.data_cadastro ASC
                """;

        List<PedidosEmAberto> pedidosRegatadosList = jdbcTemplate.query(sql, (rs, rowNum) ->
                new PedidosEmAberto(
                        rs.getLong("id"),
                        rs.getTimestamp("data_cadastro").toLocalDateTime(),
                        rs.getString("nome_cliente"),
                        rs.getString("descricao_produtos"),
                        rs.getString("status_pedido")));

        List<PedidoController.PedidosEmAbertoDTO> pedidosEmAberto = new ArrayList<>();

        if (!pedidosRegatadosList.isEmpty()) {

            for (PedidosEmAberto pedidoRegatado : pedidosRegatadosList) {

                PedidoController.PedidosEmAbertoDTO gerarPedido = getPedidosEmAberto(pedidoRegatado);

                pedidosEmAberto.add(gerarPedido);
            }

            return pedidosEmAberto;

        }

        return pedidosEmAberto;
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

    private static PedidoController.PedidosEmAbertoDTO getPedidosEmAberto(PedidosEmAberto pedidoRegatado) {

        String[] produtos = pedidoRegatado.produtos().split(";;");

        List<PedidoController.ProdutoDTO> produtosSolicitados = new ArrayList<>();

        for (String produto : produtos) {
            String[] detalhesDoProduto = produto.split(",");

            produtosSolicitados.add(new PedidoController.ProdutoDTO(detalhesDoProduto[0], detalhesDoProduto[1], detalhesDoProduto[2]));
        }

        return new PedidoController.PedidosEmAbertoDTO(
                pedidoRegatado.id(),
                pedidoRegatado.dataCadastro(),
                pedidoRegatado.nomeCliente(),
                produtosSolicitados,
                StatusPedido.valueOf(pedidoRegatado.status));

    }

    private record PedidosRecebidos(Long id, LocalDateTime dataCadastro, String nomeCliente, String produtos) {
    }

    private record PedidosEmAberto(Long id, LocalDateTime dataCadastro, String nomeCliente, String produtos, String status) {
    }
}
