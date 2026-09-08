package com.uli.fitnessapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProgressionPointDTO(LocalDate date, BigDecimal poidsMax) {
}
