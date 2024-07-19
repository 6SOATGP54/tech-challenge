package br.com.soat.soat.food.model;

import br.com.soat.soat.food.model.embeddable.DiaDaSemana;
import br.com.soat.soat.food.model.embeddable.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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

    private String externalId;

    private Long user_id;


}
