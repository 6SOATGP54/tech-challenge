package br.com.soat.soat.food.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "pedido_produto")
@EqualsAndHashCode(callSuper=false)
public class PedidoProduto extends Entidade {


    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    @Column(name = "produto_id")
    private Long produto;

    private int quantidade;

}
