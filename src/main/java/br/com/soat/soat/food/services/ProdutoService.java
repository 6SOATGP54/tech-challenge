package br.com.soat.soat.food.services;

import br.com.soat.soat.food.enums.Categoria;
import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public List<Produto> setarDecontoProdutos(Cliente cliente) {

        List<Produto> produtosDesconto = new ArrayList<>();

        if (cliente.getId() != null) {
            BigDecimal desconto = new BigDecimal(10);

            for (Produto produto : this.listarProdutos()) {
                if(produto.getPreco().compareTo(desconto) > 0){
                    produto.setPreco(produto.getPreco().subtract(desconto));
                }

                produtosDesconto.add(produto);
            }

            return produtosDesconto;
        }

        return this.listarProdutos();
    }

    public void deletarProduto(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produtoRepository.deleteById(id);
        }
    }

    public List<Produto> listarProdutosPorCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }
}
