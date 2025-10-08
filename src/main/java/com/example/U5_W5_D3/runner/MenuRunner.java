package com.example.U5_W5_D3.runner;

import com.example.U5_W5_D3.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner per la stampa del menù
 */
@Component
@Order(1)
@Slf4j
public class MenuRunner implements CommandLineRunner {

    @Autowired
    private Menu menu;

    @Override
    public void run(String... args) throws Exception {
        log.info("🍕 Benvenuti alla nostra Pizzeria! 🍕");

        // Stampa il menù completo utilizzando il Bean Menu
        menu.stampaMenu();

        log.info("Grazie per aver visitato il nostro menù!");
        log.info("Per ordinare, contattaci al numero: 📞 123-456-7890");
    }
}
