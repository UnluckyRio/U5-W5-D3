package com.example.U5_W5_D3.service;

import com.example.U5_W5_D3.model.Ordine;
import com.example.U5_W5_D3.model.StatoOrdine;
import com.example.U5_W5_D3.model.StatoTavolo;
import com.example.U5_W5_D3.model.Tavolo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione degli ordini e dei tavoli
 */
@Service
@Slf4j
public class OrdineService {

    @Value("${pizzeria.costo.coperto}")
    private double costoCoperto;

    private List<Tavolo> tavoli;
    private List<Ordine> ordini;
    private int prossimoNumeroOrdine = 1;

    public OrdineService() {
        this.tavoli = new ArrayList<>();
        this.ordini = new ArrayList<>();
        inizializzaTavoli();
    }

    /**
     * Inizializza i tavoli del ristorante
     */
    private void inizializzaTavoli() {
        tavoli.add(new Tavolo(1, 2, StatoTavolo.LIBERO));
        tavoli.add(new Tavolo(2, 4, StatoTavolo.LIBERO));
        tavoli.add(new Tavolo(3, 6, StatoTavolo.LIBERO));
        tavoli.add(new Tavolo(4, 8, StatoTavolo.LIBERO));
        tavoli.add(new Tavolo(5, 4, StatoTavolo.LIBERO));
        tavoli.add(new Tavolo(6, 2, StatoTavolo.LIBERO));
        log.info("Inizializzati {} tavoli", tavoli.size());
    }

    /**
     * Trova un tavolo libero che può ospitare il numero di coperti richiesto
     */
    public Optional<Tavolo> trovaTavoloLibero(int numeroCoperti) {
        return tavoli.stream()
                .filter(tavolo -> tavolo.pueOspitare(numeroCoperti))
                .findFirst();
    }

    /**
     * Crea un nuovo ordine
     */
    public Ordine creaOrdine(int numeroCoperti) {
        Optional<Tavolo> tavoloLibero = trovaTavoloLibero(numeroCoperti);

        if (tavoloLibero.isEmpty()) {
            log.warn("Nessun tavolo disponibile per {} coperti", numeroCoperti);
            throw new RuntimeException("Nessun tavolo disponibile per " + numeroCoperti + " coperti");
        }

        Tavolo tavolo = tavoloLibero.get();
        tavolo.occupa();

        Ordine ordine = new Ordine(prossimoNumeroOrdine++, numeroCoperti, tavolo, this.costoCoperto);
        ordini.add(ordine);

        log.info("Creato nuovo ordine #{} per tavolo {} con {} coperti",
                ordine.getNumeroOrdine(), tavolo.getNumero(), numeroCoperti);

        return ordine;
    }

    /**
     * Completa un ordine e libera il tavolo
     */
    public void completaOrdine(Ordine ordine) {
        ordine.cambiaStato(StatoOrdine.SERVITO);
        ordine.getTavolo().libera();
        log.info("Ordine #{} completato, tavolo {} liberato",
                ordine.getNumeroOrdine(), ordine.getTavolo().getNumero());
    }

    /**
     * Stampa il dettaglio di un ordine usando il logger
     */
    public void stampaOrdine(Ordine ordine) {
        log.info("\n{}", ordine.getDettaglioOrdine());
    }

    /**
     * Restituisce tutti i tavoli
     */
    public List<Tavolo> getTavoli() {
        return new ArrayList<>(tavoli);
    }

    /**
     * Restituisce tutti gli ordini
     */
    public List<Ordine> getOrdini() {
        return new ArrayList<>(ordini);
    }
}
