package com.example.U5_W5_D3.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe che rappresenta una bevanda del menù
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Drink extends MenuItem {

    public Drink(String nome, double prezzo, int calorie) {
        super(nome, prezzo, calorie);
    }

    @Override
    public String getDescrizione() {
        return nome;
    }

    @Override
    public String toString() {
        return String.format("%-35s €%.2f - %s",
                getDescrizione(),
                prezzo,
                getInformazioniNutrizionali());
    }
}
