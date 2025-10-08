package com.example.U5_W5_D3;

import com.example.U5_W5_D3.model.*;
import com.example.U5_W5_D3.service.OrdineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite per il sistema di gestione pizzeria
 * Test di difficoltà crescente per verificare le funzionalità del sistema
 */
@SpringBootTest
@TestPropertySource(properties = {"pizzeria.costo.coperto=2.00"})
class PizzeriaTestSuite {

    @Autowired
    private OrdineService ordineService;

    private Pizza margherita;
    private Pizza hawaiianPizza;
    private Drink cocaCola;
    private Topping prosciutto;
    private Topping ananas;

    @BeforeEach
    void setUp() {
        // Setup elementi del menu per i test
        margherita = new Pizza("Margherita", 6.50, 800);
        hawaiianPizza = new Pizza("Hawaiian Pizza", 6.50, 800);
        cocaCola = new Drink("Coca Cola", 2.50, 139);
        prosciutto = new Topping("Prosciutto", 2.50, 120);
        ananas = new Topping("Ananas", 1.50, 40);

        hawaiianPizza.aggiungiTopping(prosciutto);
        hawaiianPizza.aggiungiTopping(ananas);
    }

    // ==================== TEST 1: SEMPLICE - Verifica creazione pizza base ====================

    @Test
    @DisplayName("Test 1 - Verifica creazione pizza Margherita base")
    void testCreazionePizzaBase() {
        // Given - Una pizza margherita base

        // When - Verifico le proprietà base
        String nome = margherita.getNome();
        double prezzo = margherita.getPrezzo();
        int calorie = margherita.getCalorie();
        boolean isXL = margherita.isXL();

        // Then - Le proprietà devono corrispondere ai valori attesi
        assertEquals("Margherita", nome, "Il nome della pizza deve essere 'Margherita'");
        assertEquals(6.50, prezzo, 0.01, "Il prezzo base deve essere 6.50");
        assertEquals(800, calorie, "Le calorie base devono essere 800");
        assertFalse(isXL, "La pizza non deve essere XL per default");
        assertEquals(6.50, margherita.getPrezzoTotale(), 0.01, "Il prezzo totale senza toppings deve essere uguale al prezzo base");
        assertEquals(800, margherita.getCalorieTotali(), "Le calorie totali senza toppings devono essere uguali alle calorie base");
    }

    // ==================== TEST 2: INTERMEDIO - Verifica calcoli pizza con toppings ====================

    @Test
    @DisplayName("Test 2 - Verifica calcoli pizza Hawaiian con toppings")
    void testCalcoliPizzaConToppings() {
        // Given - Una pizza Hawaiian con prosciutto e ananas

        // When - Calcolo i totali
        double prezzoTotale = hawaiianPizza.getPrezzoTotale();
        int calorieTotali = hawaiianPizza.getCalorieTotali();
        String descrizione = hawaiianPizza.getDescrizione();

        // Then - I calcoli devono essere corretti
        double prezzoAtteso = 6.50 + 2.50 + 1.50; // base + prosciutto + ananas
        int calorieAttese = 800 + 120 + 40; // base + prosciutto + ananas

        assertEquals(prezzoAtteso, prezzoTotale, 0.01,
                "Il prezzo totale deve includere base + toppings (6.50 + 2.50 + 1.50 = 10.50)");
        assertEquals(calorieAttese, calorieTotali,
                "Le calorie totali devono includere base + toppings (800 + 120 + 40 = 960)");
        assertTrue(descrizione.contains("Ananas"),
                "La descrizione deve contenere 'Ananas'");
        assertTrue(descrizione.contains("Prosciutto"),
                "La descrizione deve contenere 'Prosciutto'");
        assertEquals("Hawaiian Pizza con Ananas, Prosciutto", descrizione,
                "La descrizione deve mostrare tutti i toppings");
    }

    // ==================== TEST 3: AVANZATO - Test gestione tavoli e assegnazioni ====================

    @Test
    @DisplayName("Test 3 - Verifica gestione tavoli e assegnazioni")
    void testGestioneTavoliEAssegnazioni() {
        // Given - Il servizio ordini con tavoli inizializzati

        // When - Cerco tavoli per diversi numeri di coperti
        Optional<Tavolo> tavoloPer2 = ordineService.trovaTavoloLibero(2);
        Optional<Tavolo> tavoloPer4 = ordineService.trovaTavoloLibero(4);
        Optional<Tavolo> tavoloPer8 = ordineService.trovaTavoloLibero(8);
        Optional<Tavolo> tavoloPer10 = ordineService.trovaTavoloLibero(10); // Troppi coperti

        // Then - Le assegnazioni devono essere corrette
        assertTrue(tavoloPer2.isPresent(), "Deve esistere un tavolo per 2 coperti");
        assertTrue(tavoloPer4.isPresent(), "Deve esistere un tavolo per 4 coperti");
        assertTrue(tavoloPer8.isPresent(), "Deve esistere un tavolo per 8 coperti");
        assertFalse(tavoloPer10.isPresent(), "Non deve esistere un tavolo per 10 coperti");

        // Verifico che i tavoli trovati possano effettivamente ospitare i coperti
        assertTrue(tavoloPer2.get().getCopertimassimi() >= 2,
                "Il tavolo per 2 deve avere almeno 2 coperti massimi");
        assertTrue(tavoloPer4.get().getCopertimassimi() >= 4,
                "Il tavolo per 4 deve avere almeno 4 coperti massimi");
        assertTrue(tavoloPer8.get().getCopertimassimi() >= 8,
                "Il tavolo per 8 deve avere almeno 8 coperti massimi");

        // Verifico che tutti i tavoli trovati siano liberi
        assertEquals(StatoTavolo.LIBERO, tavoloPer2.get().getStato(), "Il tavolo deve essere libero");
        assertEquals(StatoTavolo.LIBERO, tavoloPer4.get().getStato(), "Il tavolo deve essere libero");
        assertEquals(StatoTavolo.LIBERO, tavoloPer8.get().getStato(), "Il tavolo deve essere libero");
    }

    // ==================== TEST 4: COMPLESSO - Test ciclo completo ordine ====================

    @Test
    @DisplayName("Test 4 - Verifica ciclo completo di un ordine")
    void testCicloCompletoOrdine() {
        // Given - Un ordine per 3 coperti
        int numeroCoperti = 3;

        // When - Creo un ordine completo
        Ordine ordine = ordineService.creaOrdine(numeroCoperti);
        Tavolo tavoloAssegnato = ordine.getTavolo();

        // Aggiungo elementi all'ordine
        ordine.aggiungiElemento(hawaiianPizza);
        ordine.aggiungiElemento(margherita);
        ordine.aggiungiElemento(cocaCola);

        // Then - Verifico lo stato iniziale dell'ordine
        assertNotNull(ordine, "L'ordine deve essere creato");
        assertTrue(ordine.getNumeroOrdine() >= 1, "L'ordine deve avere un numero valido");
        assertEquals(StatoOrdine.IN_CORSO, ordine.getStato(), "L'ordine deve iniziare in stato IN_CORSO");
        assertEquals(numeroCoperti, ordine.getNumeroCoperti(), "Il numero di coperti deve corrispondere");
        assertNotNull(ordine.getOraAcquisizione(), "L'ora di acquisizione deve essere impostata");

        // Verifico l'assegnazione del tavolo
        assertNotNull(tavoloAssegnato, "Un tavolo deve essere assegnato");
        assertEquals(StatoTavolo.OCCUPATO, tavoloAssegnato.getStato(), "Il tavolo deve essere occupato");
        assertTrue(tavoloAssegnato.getCopertimassimi() >= numeroCoperti,
                "Il tavolo deve poter ospitare i coperti richiesti");

        // Verifico i calcoli dell'ordine
        assertEquals(3, ordine.getElementi().size(), "L'ordine deve contenere 3 elementi");
        double totaleElementi = 10.50 + 6.50 + 2.50; // Hawaiian + Margherita + Coca Cola
        double totaleCoperti = numeroCoperti * 2.00; // 3 coperti * 2.00 (dal test properties)
        double totaleAtteso = totaleElementi + totaleCoperti;

        assertEquals(totaleElementi, ordine.getTotaleElementi(), 0.01,
                "Il totale elementi deve essere corretto");
        assertEquals(totaleCoperti, ordine.getTotaleCoperti(), 0.01,
                "Il totale coperti deve essere corretto");
        assertEquals(totaleAtteso, ordine.getImportoTotale(), 0.01,
                "L'importo totale deve essere corretto");

        // When - Cambio gli stati dell'ordine
        ordine.cambiaStato(StatoOrdine.PRONTO);
        assertEquals(StatoOrdine.PRONTO, ordine.getStato(), "L'ordine deve essere PRONTO");

        ordine.cambiaStato(StatoOrdine.SERVITO);
        assertEquals(StatoOrdine.SERVITO, ordine.getStato(), "L'ordine deve essere SERVITO");

        // When - Completo l'ordine
        ordineService.completaOrdine(ordine);

        // Then - Il tavolo deve essere liberato
        assertEquals(StatoTavolo.LIBERO, tavoloAssegnato.getStato(),
                "Il tavolo deve essere liberato dopo il completamento dell'ordine");
    }

    // ==================== TEST 5: PARAMETRICO - Test formato XL con diversi moltiplicatori ====================

    @ParameterizedTest
    @DisplayName("Test 5 - Verifica calcoli pizza XL con diversi parametri")
    @CsvSource({
            "Margherita, 6.50, 800, 9.75, 1200",
            "Diavola, 8.50, 950, 12.75, 1425",
            "Quattro Formaggi, 10.50, 1090, 15.75, 1635",
            "Capricciosa, 12.00, 1150, 18.00, 1725"
    })
    void testCalcoliPizzaXLParametrico(String nomePizza, double prezzoBase, int calorieBase,
                                       double prezzoXLAtteso, int calorieXLAttese) {
        // Given - Una pizza con i parametri forniti
        Pizza pizzaNormale = new Pizza(nomePizza, prezzoBase, calorieBase, false);
        Pizza pizzaXL = new Pizza(nomePizza, prezzoBase, calorieBase, true);

        // When - Calcolo i valori per entrambe le versioni
        double prezzoNormale = pizzaNormale.getPrezzoTotale();
        int calorieNormali = pizzaNormale.getCalorieTotali();
        double prezzoXL = pizzaXL.getPrezzoTotale();
        int calorieXL = pizzaXL.getCalorieTotali();

        // Then - I valori devono corrispondere alle aspettative
        assertEquals(prezzoBase, prezzoNormale, 0.01,
                String.format("Il prezzo normale di %s deve essere %.2f", nomePizza, prezzoBase));
        assertEquals(calorieBase, calorieNormali,
                String.format("Le calorie normali di %s devono essere %d", nomePizza, calorieBase));

        assertEquals(prezzoXLAtteso, prezzoXL, 0.01,
                String.format("Il prezzo XL di %s deve essere %.2f (+50%%)", nomePizza, prezzoXLAtteso));
        assertEquals(calorieXLAttese, calorieXL,
                String.format("Le calorie XL di %s devono essere %d (+50%%)", nomePizza, calorieXLAttese));

        // Verifico che il formato XL sia effettivamente il 50% in più
        assertEquals(prezzoBase * 1.5, prezzoXL, 0.01,
                "Il prezzo XL deve essere il 150% del prezzo base");
        assertEquals((int) (calorieBase * 1.5), calorieXL,
                "Le calorie XL devono essere il 150% delle calorie base");

        // Verifico le descrizioni
        assertFalse(pizzaNormale.getDescrizione().contains("XL"),
                "La pizza normale non deve contenere 'XL' nella descrizione");
        assertTrue(pizzaXL.getDescrizione().contains("XL"),
                "La pizza XL deve contenere 'XL' nella descrizione");
    }

    // ==================== Test gestione toppings multipli ====================

    @ParameterizedTest
    @DisplayName("Test Bonus - Verifica gestione toppings multipli")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testToppingsMultipli(int numeroToppings) {
        // Given - Una pizza base
        Pizza pizza = new Pizza("Pizza Test", 6.50, 800);
        Topping topping = new Topping("Prosciutto", 2.00, 100);

        // When - Aggiungo il topping più volte
        for (int i = 0; i < numeroToppings; i++) {
            pizza.aggiungiTopping(topping);
        }

        // Then - I calcoli devono essere corretti
        double prezzoAtteso = 6.50 + (numeroToppings * 2.00);
        int calorieAttese = 800 + (numeroToppings * 100);

        assertEquals(prezzoAtteso, pizza.getPrezzoTotale(), 0.01,
                String.format("Con %d toppings il prezzo deve essere %.2f", numeroToppings, prezzoAtteso));
        assertEquals(calorieAttese, pizza.getCalorieTotali(),
                String.format("Con %d toppings le calorie devono essere %d", numeroToppings, calorieAttese));

        String descrizione = pizza.getDescrizione();

        // Verifico la descrizione in base al numero di toppings
        if (numeroToppings == 1) {
            assertTrue(descrizione.contains("Prosciutto") && !descrizione.contains("doppio"),
                    "Con 1 topping deve apparire il nome semplice");
        } else if (numeroToppings == 2) {
            assertTrue(descrizione.contains("doppio Prosciutto"),
                    "Con 2 toppings deve apparire 'doppio'");
        } else if (numeroToppings >= 3) {
            assertTrue(descrizione.contains("triplo Prosciutto"),
                    "Con 3+ toppings deve apparire 'triplo'");
        }
    }
}
