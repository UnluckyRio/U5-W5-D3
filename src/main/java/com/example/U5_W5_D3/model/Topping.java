package com.example.U5_W5_D3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe che rappresenta un topping per le pizze
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topping {
    private String nome;
    private double prezzo;
    private int calorie;

    @Override
    public String toString() {
        return String.format("%s (+€%.2f, +%d kcal)", nome, prezzo, calorie);
    }
}
