package br.com.soat.soat.food.model.embeddable;

import br.com.soat.soat.food.model.Entidade;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "intervalo")
public class Intervalo extends Entidade {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    private LocalTime open;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm")
    private LocalTime close;


    @ManyToOne
    @JoinColumn(name = "dia_da_semana_id")
    @JsonBackReference
    private DiaDaSemana diaDaSemana;

}
