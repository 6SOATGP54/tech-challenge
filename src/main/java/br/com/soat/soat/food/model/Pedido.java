package br.com.soat.soat.food.model;

import br.com.soat.soat.food.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pedido")
@EqualsAndHashCode(callSuper=false)
public class Pedido extends Entidade {

    private Long cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProduto> pedidoProdutos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProduto> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    @PrePersist
    public void setRecebido() {
        setStatusPedido(StatusPedido.RECEBIDO);
    }
}
