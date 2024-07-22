package br.com.soat.soat.food.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class EscopoCaixaMercadoPago extends Entidade {

    private Long category;

    @Column(unique = true)
    @NonNull
    private String external_id;

    private String external_store_id;

    private Boolean fixed_amount;

    private String name;

    @NonNull
    private Long store_id;

    private Long idAPI;

    @Override
    protected void prePersistSubClasses() {
        setCategory(5611203L);
        setFixed_amount(Boolean.FALSE);
    }
}
