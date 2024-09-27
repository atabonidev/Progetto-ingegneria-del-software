package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InizializzaRicetta;
import main_package.model.Ricetta;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class Test_CreaRicetta_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_CreaRicetta_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }

    @AfterEach
    public void tearDown() {
        gestoreRepository.getRicetteRepository().deleteRicetta("ricetta_test");
        inputDati.close();
    }

    @Test
    void CreaRicetta(){
        new InizializzaRicetta(gestoreRepository).execute(session, cliView);
        Ricetta ricetta = gestoreRepository.getRicetteRepository().getRicettaFromName("ricetta_test");

        Map<String, Double> ingredientiTest = new HashMap<>();
        ingredientiTest.put("casoncello", 1.0);
        ingredientiTest.put("burro", 3.0);
        Ricetta ricettaTest = new Ricetta("ricetta_test", ingredientiTest, 1, 0.25);

        assertNotNull(ricetta);
		assertEquals(ricettaTest, ricetta);
    }
}
