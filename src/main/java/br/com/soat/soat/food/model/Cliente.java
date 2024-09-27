package br.com.soat.soat.food.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cliente")
@EqualsAndHashCode(callSuper=false)
public class Cliente extends Entidade {

    @NotNull
    private String cpf;

    private String nome;

    private String email;

}
