package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.enums.StatusAcompanhamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Transactional
    @Modifying
    @Query("update Pedido p set p.acompanhamento = ?1 where p.id = ?2")
    int acompanhamentoPedido(StatusAcompanhamento acompanhamento, Long id);
}
