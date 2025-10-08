package com.example.U5_W5_D3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe che rappresenta un tavolo del ristorante
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tavolo {
    private int numero;
    private int copertimassimi;
    private StatoTavolo stato;

    /**
     * Controlla se il tavolo può ospitare il numero di coperti richiesto
     */
    public boolean pueOspitare(int numeroCoperti) {
        return stato == StatoTavolo.LIBERO && numeroCoperti <= copertimassimi;
    }

    /**
     * Occupa il tavolo
     */
    public void occupa() {
        this.stato = StatoTavolo.OCCUPATO;
    }

    /**
     * Libera il tavolo
     */
    public void libera() {
        this.stato = StatoTavolo.LIBERO;
    }

    @Override
    public String toString() {
        return String.format("Tavolo %d (max %d coperti) - %s", numero, copertimassimi, stato);
    }
}
