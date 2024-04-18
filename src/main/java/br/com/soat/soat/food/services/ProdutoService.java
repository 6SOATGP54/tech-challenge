package br.com.soat.soat.food.services;

import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto cadastroEupdateProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Produto::getCategoria))
                .toList();
    }
}
