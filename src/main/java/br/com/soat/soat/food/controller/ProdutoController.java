package br.com.soat.soat.food.controller;

import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.model.enums.Categoria;
import br.com.soat.soat.food.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping("/cadastroProduto")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> insertOrUpdateSala(@Validated @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.cadastroEupdateProduto(produto));
    }

    @GetMapping("/listarProdutos")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Produto>> insertOrUpdateSala() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PostMapping("/listarProdutosPorCategoria")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Produto>> listarProdutosPorCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(produtoService.listarProdutosPorCategoria(categoria));
    }

    @PostMapping("/listarProdutosDesconto")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Produto>> produtosComDesconto(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(produtoService.setarDecontoProdutos(cliente));
    }

    @DeleteMapping("/deletarProduto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }
}
