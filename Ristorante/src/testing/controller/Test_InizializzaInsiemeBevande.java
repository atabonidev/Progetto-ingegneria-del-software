package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InitInsiemeBevande;
import main_package.controller.handlers.inizializzaParametroHandlers.InitNumeroPostiASedere;
import main_package.model.Prodotto;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.persistence.impl.ServicesFactory;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.persistence.user.GestoreRepository;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test_InizializzaInsiemeBevande {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_InizializzaInsiemeBevande_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }

    @AfterEach
    public void tearDown() {
        inputDati.close();
    }


    @Test
    public void inizializzaInsiemeBevande() {
        //Suppongo che non esistano gia' le seguenti due bevande nella tabella Prodotto
        Prodotto bevanda1 = new Prodotto("bevanda1_test", TipoProdotto.BEVANDA_SERVITA, UnitaMisura.LITRI);
        Prodotto bevanda2 = new Prodotto("bevanda2_test", TipoProdotto.BEVANDA_SERVITA, UnitaMisura.LITRI);

        new InitInsiemeBevande(gestoreRepository).execute(session, cliView);

        Map<Prodotto, Double> insiemeBevande = gestoreRepository.getBevandeRepository().getInsiemeBevande();

        assertTrue(insiemeBevande.containsKey(bevanda1));
        assertEquals(insiemeBevande.get(bevanda1), 3);
        assertTrue(insiemeBevande.containsKey(bevanda2));
        assertEquals(insiemeBevande.get(bevanda2), 4);

        //Elimino le due bevande appena inserite in modo da lasciare inalterata la tabella di partenza
        gestoreRepository.getBevandeRepository().removeBevanda(bevanda1);
        gestoreRepository.getBevandeRepository().removeBevanda(bevanda2);
    }
}
