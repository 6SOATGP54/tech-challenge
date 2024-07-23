package br.com.soat.soat.food.dtos;

import br.com.soat.soat.food.enums.Categoria;

import java.math.BigDecimal;

public record ItemOrdemVendaDTO(String sku_number,
                                Categoria category,
                                String title,
                                String description,
                                BigDecimal unit_price,
                                int quantity,
                                String unit_measure,
                                BigDecimal total_amount) {
}