package com.example.U5_W5_D3.runner;

import com.example.U5_W5_D3.model.*;
import com.example.U5_W5_D3.service.OrdineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner per la gestione degli ordini
 */
@Component
@Order(2)
@Slf4j
public class OrdineRunner implements CommandLineRunner {

    @Autowired
    private Menu menu;

    @Autowired
    private OrdineService ordineService;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== DEMO SISTEMA GESTIONE ORDINI ===");

        // Crea un nuovo ordine per 4 coperti
        log.info("Creazione di un nuovo ordine per 4 coperti...");
        Ordine ordine = ordineService.creaOrdine(4);

        // Aggiungi elementi all'ordine
        log.info("Aggiunta elementi all'ordine...");

        // Aggiungi una Hawaiian Pizza XL
        Pizza hawaiianXL = new Pizza("Hawaiian Pizza", 6.50, 800, true);
        hawaiianXL.aggiungiTopping(new Topping("Prosciutto", 2.50, 120));
        hawaiianXL.aggiungiTopping(new Topping("Ananas", 1.50, 40));
        ordine.aggiungiElemento(hawaiianXL);

        // Aggiungi una Margherita normale
        Pizza margherita = new Pizza("Margherita", 6.50, 800);
        ordine.aggiungiElemento(margherita);

        // Aggiungi una pizza speciale con doppio prosciutto
        Pizza specialePizza = new Pizza("Pizza Speciale", 6.50, 800);
        specialePizza.aggiungiTopping(new Topping("Prosciutto", 2.50, 120));
        specialePizza.aggiungiTopping(new Topping("Prosciutto", 2.50, 120)); // doppio
        specialePizza.aggiungiTopping(new Topping("Rucola", 1.00, 10));
        ordine.aggiungiElemento(specialePizza);

        // Aggiungi bevande
        ordine.aggiungiElemento(new Drink("Coca Cola", 2.50, 139));
        ordine.aggiungiElemento(new Drink("Birra", 3.50, 150));
        ordine.aggiungiElemento(new Drink("Acqua", 1.50, 0));
        ordine.aggiungiElemento(new Drink("Acqua", 1.50, 0)); // seconda acqua

        log.info("Elementi aggiunti all'ordine con successo");

        // Stampa l'ordine completo
        log.info("Stampa dettaglio ordine:");
        ordineService.stampaOrdine(ordine);

        // Simula il cambio di stato dell'ordine
        log.info("Simulazione cambio stato ordine...");
        Thread.sleep(1000);
        ordine.cambiaStato(StatoOrdine.PRONTO);
        log.info("Ordine #{} ora è: {}", ordine.getNumeroOrdine(), ordine.getStato());

        Thread.sleep(2000);
        ordine.cambiaStato(StatoOrdine.SERVITO);
        log.info("Ordine #{} ora è: {}", ordine.getNumeroOrdine(), ordine.getStato());

        // Completa l'ordine e libera il tavolo
        ordineService.completaOrdine(ordine);

        // Mostra stato finale tavoli
        log.info("Stato finale tavoli:");
        ordineService.getTavoli().forEach(tavolo ->
                log.info("- {}", tavolo.toString()));

        log.info("=== DEMO COMPLETATA ===");
    }
}
