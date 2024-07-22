package br.com.soat.soat.food.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record IntervalosDTO(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime open,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime close) {
}
