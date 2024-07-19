package br.com.soat.soat.food.model.embeddable;

import br.com.soat.soat.food.model.Entidade;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dia_semana")
public class DiaDaSemana extends Entidade {

    @Enumerated(EnumType.STRING)
    private Dia dia;

    @OneToMany(mappedBy = "diaDaSemana", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Intervalo> intervalos;


    public enum Dia {
        monday,
        tuesday,
        wednesday,
        thursday,
        friday,
        Saturday,
        sunday
    }
}
