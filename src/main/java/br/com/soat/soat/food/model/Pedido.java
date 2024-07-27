package br.com.soat.soat.food.model;

import br.com.soat.soat.food.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pedido")
@EqualsAndHashCode(callSuper=false)
@Data
public class Pedido extends Entidade {

    private Long cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PedidoProduto> pedidoProdutos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    private String referencia;

    private String pagamento;

    @Transient
    private Long credencialId;

    @Transient
    private Long caixaId;
}
