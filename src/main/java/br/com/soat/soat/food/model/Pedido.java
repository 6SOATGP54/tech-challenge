package br.com.soat.soat.food.model;

import br.com.soat.soat.food.model.enums.StatusAcompanhamento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@Entity
@Table(name = "pedido")
@EqualsAndHashCode(callSuper=false)
public class Pedido extends Entidade {

    private Long cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProduto> pedidoProdutos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusAcompanhamento acompanhamento;
}
