package br.com.soat.soat.food.model;

import br.com.soat.soat.food.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "pedido")
@EqualsAndHashCode(callSuper=false)
public class Pedido extends Entidade {

    private Long cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProduto> pedidoProdutos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;
}
