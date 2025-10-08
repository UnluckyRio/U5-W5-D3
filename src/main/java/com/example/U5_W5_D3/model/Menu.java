package com.example.U5_W5_D3.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il menù completo della pizzeria
 */
@Data
@NoArgsConstructor
public class Menu {
    private List<Pizza> pizze;
    private List<Drink> bevande;

    public Menu(List<Pizza> pizze, List<Drink> bevande) {
        this.pizze = pizze != null ? pizze : new ArrayList<>();
        this.bevande = bevande != null ? bevande : new ArrayList<>();
    }

    /**
     * Aggiunge una pizza al menù
     */
    public void aggiungiPizza(Pizza pizza) {
        if (pizze == null) {
            pizze = new ArrayList<>();
        }
        pizze.add(pizza);
    }

    /**
     * Aggiunge una bevanda al menù
     */
    public void aggiungiBevanda(Drink bevanda) {
        if (bevande == null) {
            bevande = new ArrayList<>();
        }
        bevande.add(bevanda);
    }

    /**
     * Stampa il menù completo formattato
     */
    public void stampaMenu() {
        System.out.println("=".repeat(80));
        System.out.println("🍕 MENÙ PIZZERIA 🍕");
        System.out.println("=".repeat(80));

        if (pizze != null && !pizze.isEmpty()) {
            System.out.println("\n🍕 PIZZE:");
            System.out.println("-".repeat(80));
            for (Pizza pizza : pizze) {
                System.out.println(pizza.toString());
            }
        }

        if (bevande != null && !bevande.isEmpty()) {
            System.out.println("\n🥤 BEVANDE:");
            System.out.println("-".repeat(80));
            for (Drink bevanda : bevande) {
                System.out.println(bevanda.toString());
            }
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("Tutte le pizze contengono di base pomodoro e mozzarella");
        System.out.println("Formato XL disponibile per tutte le pizze (+50% prezzo e calorie)");
        System.out.println("=".repeat(80));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menù Pizzeria:\n");

        if (pizze != null) {
            sb.append("Pizze: ").append(pizze.size()).append("\n");
        }

        if (bevande != null) {
            sb.append("Bevande: ").append(bevande.size()).append("\n");
        }

        return sb.toString();
    }
}
