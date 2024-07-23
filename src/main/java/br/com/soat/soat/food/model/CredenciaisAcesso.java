package br.com.soat.soat.food.model;

import br.com.soat.soat.food.enums.ServicosIntegracao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CredenciaisAcesso extends Entidade{

    @Enumerated(EnumType.STRING)
    private ServicosIntegracao nome;

    @Column(columnDefinition = "text")
    private String token;

    @Column(unique = true)
    private String usuario;

    private String webHook;

}
