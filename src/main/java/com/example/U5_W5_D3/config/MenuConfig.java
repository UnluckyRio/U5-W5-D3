package com.example.U5_W5_D3.config;

import com.example.U5_W5_D3.model.Drink;
import com.example.U5_W5_D3.model.Menu;
import com.example.U5_W5_D3.model.Pizza;
import com.example.U5_W5_D3.model.Topping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Classe di configurazione Spring per i Bean del menù pizzeria
 */
@Configuration
public class MenuConfig {

    // ============ TOPPINGS BEANS ============

    @Bean
    public Topping prosciutto() {
        return new Topping("Prosciutto", 2.50, 120);
    }

    @Bean
    public Topping ananas() {
        return new Topping("Ananas", 1.50, 40);
    }

    @Bean
    public Topping funghi() {
        return new Topping("Funghi", 1.80, 15);
    }

    @Bean
    public Topping salame() {
        return new Topping("Salame piccante", 2.00, 150);
    }

    @Bean
    public Topping olive() {
        return new Topping("Olive", 1.20, 80);
    }

    @Bean
    public Topping gorgonzola() {
        return new Topping("Gorgonzola", 2.20, 180);
    }

    @Bean
    public Topping rucola() {
        return new Topping("Rucola", 1.00, 10);
    }

    @Bean
    public Topping parmigiano() {
        return new Topping("Parmigiano", 1.80, 110);
    }

    // ============ DRINKS BEANS ============

    @Bean
    public Drink cocaCola() {
        return new Drink("Coca Cola", 2.50, 139);
    }

    @Bean
    public Drink birra() {
        return new Drink("Birra", 3.50, 150);
    }

    @Bean
    public Drink acqua() {
        return new Drink("Acqua", 1.50, 0);
    }

    @Bean
    public Drink sprite() {
        return new Drink("Sprite", 2.50, 136);
    }

    @Bean
    public Drink vino() {
        return new Drink("Vino della casa", 4.00, 125);
    }

    // ============ PIZZE BEANS ============

    @Bean
    public Pizza margherita() {
        return new Pizza("Margherita", 6.50, 800);
    }

    @Bean
    public Pizza margheritaXL() {
        return new Pizza("Margherita", 6.50, 800, true);
    }

    @Bean
    public Pizza hawaiianPizza() {
        Pizza pizza = new Pizza("Hawaiian Pizza", 6.50, 800);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(ananas());
        return pizza;
    }

    @Bean
    public Pizza hawaiianPizzaXL() {
        Pizza pizza = new Pizza("Hawaiian Pizza", 6.50, 800, true);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(ananas());
        return pizza;
    }

    @Bean
    public Pizza quattroStagioni() {
        Pizza pizza = new Pizza("Quattro Stagioni", 6.50, 800);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(funghi());
        pizza.aggiungiTopping(olive());
        pizza.aggiungiTopping(salame());
        return pizza;
    }

    @Bean
    public Pizza quattroStagioniXL() {
        Pizza pizza = new Pizza("Quattro Stagioni", 6.50, 800, true);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(funghi());
        pizza.aggiungiTopping(olive());
        pizza.aggiungiTopping(salame());
        return pizza;
    }

    @Bean
    public Pizza quattroFormaggi() {
        Pizza pizza = new Pizza("Quattro Formaggi", 6.50, 800);
        pizza.aggiungiTopping(gorgonzola());
        pizza.aggiungiTopping(parmigiano());
        return pizza;
    }

    @Bean
    public Pizza quattroFormaggiXL() {
        Pizza pizza = new Pizza("Quattro Formaggi", 6.50, 800, true);
        pizza.aggiungiTopping(gorgonzola());
        pizza.aggiungiTopping(parmigiano());
        return pizza;
    }

    @Bean
    public Pizza diavolaPizza() {
        Pizza pizza = new Pizza("Diavola", 6.50, 800);
        pizza.aggiungiTopping(salame());
        return pizza;
    }

    @Bean
    public Pizza diavolaPizzaXL() {
        Pizza pizza = new Pizza("Diavola", 6.50, 800, true);
        pizza.aggiungiTopping(salame());
        return pizza;
    }

    @Bean
    public Pizza rucoleseSpecial() {
        Pizza pizza = new Pizza("Rucolese Special", 6.50, 800);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(prosciutto()); // doppio prosciutto
        pizza.aggiungiTopping(rucola());
        pizza.aggiungiTopping(parmigiano());
        return pizza;
    }

    @Bean
    public Pizza rucoleseSpecialXL() {
        Pizza pizza = new Pizza("Rucolese Special", 6.50, 800, true);
        pizza.aggiungiTopping(prosciutto());
        pizza.aggiungiTopping(prosciutto()); // doppio prosciutto
        pizza.aggiungiTopping(rucola());
        pizza.aggiungiTopping(parmigiano());
        return pizza;
    }

    // ============ MENU BEAN ============

    @Bean
    public Menu menu() {
        List<Pizza> pizze = Arrays.asList(
                margherita(),
                margheritaXL(),
                hawaiianPizza(),
                hawaiianPizzaXL(),
                quattroStagioni(),
                quattroStagioniXL(),
                quattroFormaggi(),
                quattroFormaggiXL(),
                diavolaPizza(),
                diavolaPizzaXL(),
                rucoleseSpecial(),
                rucoleseSpecialXL()
        );

        List<Drink> bevande = Arrays.asList(
                acqua(),
                cocaCola(),
                sprite(),
                birra(),
                vino()
        );

        return new Menu(pizze, bevande);
    }
}
