package com.example.U5_W5_D3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un ordine del ristorante
 */
@Data
@NoArgsConstructor
public class Ordine {
    private int numeroOrdine;
    private StatoOrdine stato;
    private int numeroCoperti;
    private LocalDateTime oraAcquisizione;
    private Tavolo tavolo;
    private List<MenuItem> elementi;
    private double costoCoperto;

    public Ordine(int numeroOrdine, int numeroCoperti, Tavolo tavolo, double costoCoperto) {
        this.numeroOrdine = numeroOrdine;
        this.stato = StatoOrdine.IN_CORSO;
        this.numeroCoperti = numeroCoperti;
        this.oraAcquisizione = LocalDateTime.now();
        this.tavolo = tavolo;
        this.elementi = new ArrayList<>();
        this.costoCoperto = costoCoperto;
    }

    /**
     * Aggiunge un elemento all'ordine
     */
    public void aggiungiElemento(MenuItem elemento) {
        if (elementi == null) {
            elementi = new ArrayList<>();
        }
        elementi.add(elemento);
    }

    /**
     * Calcola l'importo totale dell'ordine
     */
    public double getImportoTotale() {
        double totaleElementi = elementi.stream()
                .mapToDouble(elemento -> {
                    if (elemento instanceof Pizza) {
                        return ((Pizza) elemento).getPrezzoTotale();
                    }
                    return elemento.getPrezzo();
                })
                .sum();

        double totaleCoperti = numeroCoperti * costoCoperto;
        return totaleElementi + totaleCoperti;
    }

    /**
     * Calcola il totale dei coperti
     */
    public double getTotaleCoperti() {
        return numeroCoperti * costoCoperto;
    }

    /**
     * Calcola il totale degli elementi (senza coperti)
     */
    public double getTotaleElementi() {
        return elementi.stream()
                .mapToDouble(elemento -> {
                    if (elemento instanceof Pizza) {
                        return ((Pizza) elemento).getPrezzoTotale();
                    }
                    return elemento.getPrezzo();
                })
                .sum();
    }

    /**
     * Cambia lo stato dell'ordine
     */
    public void cambiaStato(StatoOrdine nuovoStato) {
        this.stato = nuovoStato;
    }

    /**
     * Restituisce una rappresentazione formattata dell'ordine
     */
    public String getDettaglioOrdine() {
        StringBuilder dettaglio = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        dettaglio.append("═══════════════════════════════════════════════════════════════════════════════\n");
        dettaglio.append(String.format("🧾 ORDINE N. %d - %s\n", numeroOrdine, stato));
        dettaglio.append("═══════════════════════════════════════════════════════════════════════════════\n");
        dettaglio.append(String.format("📍 %s\n", tavolo.toString()));
        dettaglio.append(String.format("👥 Coperti: %d\n", numeroCoperti));
        dettaglio.append(String.format("🕒 Ora acquisizione: %s\n", oraAcquisizione.format(formatter)));
        dettaglio.append("───────────────────────────────────────────────────────────────────────────────\n");
        dettaglio.append("📋 ELEMENTI ORDINATI:\n");
        dettaglio.append("───────────────────────────────────────────────────────────────────────────────\n");

        if (elementi != null && !elementi.isEmpty()) {
            for (int i = 0; i < elementi.size(); i++) {
                MenuItem elemento = elementi.get(i);
                double prezzo = elemento instanceof Pizza ?
                        ((Pizza) elemento).getPrezzoTotale() : elemento.getPrezzo();

                dettaglio.append(String.format("%d. %-40s €%.2f\n",
                        i + 1, elemento.getDescrizione(), prezzo));
            }
        } else {
            dettaglio.append("Nessun elemento ordinato\n");
        }

        dettaglio.append("───────────────────────────────────────────────────────────────────────────────\n");
        dettaglio.append("💰 RIEPILOGO COSTI:\n");
        dettaglio.append("───────────────────────────────────────────────────────────────────────────────\n");
        dettaglio.append(String.format("Totale elementi:                        €%.2f\n", getTotaleElementi()));
        dettaglio.append(String.format("Coperti (%d x €%.2f):                   €%.2f\n",
                numeroCoperti, costoCoperto, getTotaleCoperti()));
        dettaglio.append("───────────────────────────────────────────────────────────────────────────────\n");
        dettaglio.append(String.format("🎯 TOTALE ORDINE:                       €%.2f\n", getImportoTotale()));
        dettaglio.append("═══════════════════════════════════════════════════════════════════════════════\n");

        return dettaglio.toString();
    }

    @Override
    public String toString() {
        return String.format("Ordine #%d - Tavolo %d - %d coperti - Stato: %s - Totale: €%.2f",
                numeroOrdine, tavolo.getNumero(), numeroCoperti, stato, getImportoTotale());
    }
}
