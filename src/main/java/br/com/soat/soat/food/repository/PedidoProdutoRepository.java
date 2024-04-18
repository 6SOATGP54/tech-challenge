package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.model.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto,Long> {
}
