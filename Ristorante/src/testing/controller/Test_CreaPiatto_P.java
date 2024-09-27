package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InizializzaPiatto;	
import main_package.model.Piatto;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Test_CreaPiatto_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;

    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_CreaPiatto_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }

    @AfterEach
    public void tearDown() {
        gestoreRepository.getRicetteRepository().deleteRicetta("ricetta1_test");
        inputDati.close();
    }


    @Test
    void CreaPiatto(){
        //creo la ricetta da associare al piatto
        Map<String, Double> ingredientiTest = new HashMap<>();
        ingredientiTest.put("i1", 1.0);
        ingredientiTest.put("i2", 3.0);
        Ricetta ricettaTest = new Ricetta("ricetta1_test", ingredientiTest, 1, 0.25);
        gestoreRepository.getRicetteRepository().insertRicetta(ricettaTest);

        //test metodo
        new InizializzaPiatto(gestoreRepository).execute(session, cliView);
        Piatto piatto = gestoreRepository.getPiattiRepository().getPiattoFromName("piatto_test");

        LocalDate dataInizio = LocalDate.of(2023,1,1);
        LocalDate dataFine = LocalDate.of(2023,1,10);

        Piatto piattoTest = new Piatto("piatto_test", "ricetta1_test", 0.25, dataInizio, dataFine);

        assertNotNull(piatto);
		assertEquals(piattoTest, piatto);
    }
}
