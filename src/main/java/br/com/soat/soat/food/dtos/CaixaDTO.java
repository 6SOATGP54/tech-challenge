package br.com.soat.soat.food.dtos;

public record CaixaDTO(Long category, String external_id, String external_store_id, Boolean fixed_amount,
                       String name, Long store_id) {
}
