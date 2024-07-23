package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.enums.StatusPedido;
import br.com.soat.soat.food.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Transactional
    @Modifying
    @Query("update Pedido p set p.statusPedido = ?1 where p.id = ?2")
    int statusPedido(StatusPedido statusPedido, Long id);

    List<Pedido> findByStatusPedido(StatusPedido statusPedido);
}
