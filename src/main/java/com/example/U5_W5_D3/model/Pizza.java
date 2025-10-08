package com.example.U5_W5_D3.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe che rappresenta una pizza del menù
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Pizza extends MenuItem {
    private List<Topping> toppings;
    private boolean isXL;

    public Pizza(String nome, double prezzo, int calorie) {
        super(nome, prezzo, calorie);
        this.toppings = new ArrayList<>();
        this.isXL = false;
    }

    public Pizza(String nome, double prezzo, int calorie, boolean isXL) {
        super(nome, prezzo, calorie);
        this.toppings = new ArrayList<>();
        this.isXL = isXL;
    }

    /**
     * Aggiunge un topping alla pizza
     */
    public void aggiungiTopping(Topping topping) {
        this.toppings.add(topping);
    }

    /**
     * Calcola il prezzo totale della pizza inclusi i toppings
     */
    public double getPrezzoTotale() {
        double prezzoBase = isXL ? prezzo * 1.5 : prezzo;
        double prezzoToppings = toppings.stream()
                .mapToDouble(Topping::getPrezzo)
                .sum();
        return prezzoBase + prezzoToppings;
    }

    /**
     * Calcola le calorie totali della pizza inclusi i toppings
     */
    public int getCalorieTotali() {
        int calorieBase = isXL ? (int) (calorie * 1.5) : calorie;
        int calorieToppings = toppings.stream()
                .mapToInt(Topping::getCalorie)
                .sum();
        return calorieBase + calorieToppings;
    }

    @Override
    public String getDescrizione() {
        StringBuilder descrizione = new StringBuilder();
        descrizione.append(nome);

        if (isXL) {
            descrizione.append(" XL");
        }

        if (!toppings.isEmpty()) {
            // Raggruppa i toppings per nome per gestire le quantità multiple
            Map<String, Long> toppingsCount = toppings.stream()
                    .collect(Collectors.groupingBy(Topping::getNome, Collectors.counting()));

            descrizione.append(" con ");
            List<String> toppingsDescrizione = new ArrayList<>();

            for (Map.Entry<String, Long> entry : toppingsCount.entrySet()) {
                String toppingNome = entry.getKey();
                Long count = entry.getValue();

                if (count > 1) {
                    toppingsDescrizione.add(count > 2 ? "triplo " + toppingNome : "doppio " + toppingNome);
                } else {
                    toppingsDescrizione.add(toppingNome);
                }
            }

            descrizione.append(String.join(", ", toppingsDescrizione));
        }

        return descrizione.toString();
    }

    @Override
    public String getInformazioniNutrizionali() {
        return String.format("Calorie: %d kcal", getCalorieTotali());
    }

    @Override
    public String toString() {
        return String.format("%-35s €%.2f - %s",
                getDescrizione(),
                getPrezzoTotale(),
                getInformazioniNutrizionali());
    }
}
