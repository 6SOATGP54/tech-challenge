package br.com.soat.soat.food.model;

import br.com.soat.soat.food.services.PedidoService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "pedido_produto")
@EqualsAndHashCode(callSuper=false)
public class PedidoProduto extends Entidade {

    @Column(name = "pedido_id")
    private Long pedido;

    @Column(name = "produto_id")
    private Long produto;

    private int quantidade;

}
