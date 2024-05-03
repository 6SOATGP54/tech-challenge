package br.com.soat.soat.food.repository.custom;

import br.com.soat.soat.food.controller.PedidoController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoCustomRepositoryImpl implements PedidoCustomRepository{



    JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PedidoController.PedidosRecebidosDTO> listaPedidos() {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.id, ");
        sql.append("       p.data_cadastro, ");
        sql.append("       c.nome as nome_cliente, ");
        sql.append("       String_agg(Concat(p2.nome, ' (', p2.categoria, ') - ', pp.quantidade), ");
        sql.append("       ';; ') AS ");
        sql.append("       produtos_quantidades ");
        sql.append("FROM   pedido p ");
        sql.append("       LEFT JOIN pedido_produto pp ");
        sql.append("              ON p.id = pp.pedido_id ");
        sql.append("       LEFT JOIN produto p2 ");
        sql.append("              ON p2.id = pp.produto_id ");
        sql.append("       LEFT JOIN cliente c ");
        sql.append("              ON c.id = p.cliente ");
        sql.append("WHERE  p.acompanhamento = 'RECEBIDO' ");
        sql.append("GROUP  BY p.id, ");
        sql.append("          p.data_cadastro, ");
        sql.append("          c.nome ");
        sql.append("ORDER  BY p.data_cadastro;");


        jdbcTemplate.query(sql.toString(), (rs, rowNum) ->
                new PedidosRegatados(
                                     rs.getLong("id"),
                                     (LocalDateTime) rs.getObject("data_cadastro"),
                                     rs.getString("numero_cliente"),
                                     rs.getString("variaveis")
                ));


        return null;
        //TODO:CONTINUAR PROJETO DAQUI
    }

    public record PedidosRegatados(Long id, LocalDateTime dataCadastro, String nomeCliente, String produtos){

    }
}
