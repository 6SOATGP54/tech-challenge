package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.model.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByCategoria(Categoria categoria);
}