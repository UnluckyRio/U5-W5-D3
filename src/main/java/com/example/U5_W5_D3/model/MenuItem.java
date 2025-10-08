package com.example.U5_W5_D3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe astratta che rappresenta un elemento generico del menù
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MenuItem {
    protected String nome;
    protected double prezzo;
    protected int calorie;

    /**
     * Metodo astratto per ottenere la descrizione completa dell'elemento del menù
     */
    public abstract String getDescrizione();

    /**
     * Metodo per ottenere le informazioni nutrizionali formattate
     */
    public String getInformazioniNutrizionali() {
        return String.format("Calorie: %d kcal", calorie);
    }
}
