package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
