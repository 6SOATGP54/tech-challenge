package br.com.soat.soat.food.model;


import br.com.soat.soat.food.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
@EqualsAndHashCode(callSuper=false)
public class Produto extends Entidade {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @OrderBy
    private Categoria categoria;

    private String imagem;

}
