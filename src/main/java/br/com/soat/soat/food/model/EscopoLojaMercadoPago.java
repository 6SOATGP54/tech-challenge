package br.com.soat.soat.food.model;

import br.com.soat.soat.food.model.embeddable.DiaDaSemana;
import br.com.soat.soat.food.model.embeddable.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class EscopoLojaMercadoPago extends Entidade{

    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name="loja_to_businessHours",
            joinColumns=
            @JoinColumn(name="loja_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="dia_semana_id", referencedColumnName="id")
    )
    private List<DiaDaSemana> businessHours;

    @Embedded
    private Location location;

    @Column(unique = true)
    private String externalId;

    @Column(name = "user_id")
    private Long userId;

    private Long credenciaisId;

}
