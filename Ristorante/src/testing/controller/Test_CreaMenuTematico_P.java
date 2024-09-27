package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InizializzaMenuTematico;
import main_package.controller.handlers.inizializzaParametroHandlers.InizializzaRicetta;
import main_package.model.MenuTematico;
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

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Test_CreaMenuTematico_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_CreaMenuTematico_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }

    @AfterEach
    public void tearDown() {
        gestoreRepository.getMenuTematiciRepository().deleteMenuTematicoFromNome("menuTematico_test");
        gestoreRepository.getRicetteRepository().deleteRicetta("ricetta2_test");
        gestoreRepository.getRicetteRepository().deleteRicetta("ricetta3_test");
        inputDati.close();
    }


    @Test
    void CreaMenuTematico(){
        //impostazione ricette
        Map<String, Double> ingredienti1 = new HashMap<>();
        ingredienti1.put("i1", 1.0);
        ingredienti1.put("i2", 3.0);
        Ricetta ricetta1 = new Ricetta("ricetta2_test", ingredienti1, 1, 0.25);
        gestoreRepository.getRicetteRepository().insertRicetta(ricetta1);

        Map<String, Double> ingredienti2 = new HashMap<>();
        ingredienti2.put("i1", 1.0);
        ingredienti2.put("i2", 3.0);
        Ricetta ricetta2 = new Ricetta("ricetta3_test", ingredienti2, 1, 0.25);
        gestoreRepository.getRicetteRepository().insertRicetta(ricetta2);

        //impostazione piatti
        LocalDate dataInizio1 = LocalDate.of(2023,1,1);
        LocalDate dataFine1 = LocalDate.of(2023,1,10);
        Piatto piattoTest1 = new Piatto("piatto1_test", "ricetta2_test", 0.25, dataInizio1, dataFine1);
        gestoreRepository.getPiattiRepository().insertPiatto(piattoTest1);

        LocalDate dataInizio2 = LocalDate.of(2023,1,1);
        LocalDate dataFine2 = LocalDate.of(2023,1,10);
        Piatto piattoTest2 = new Piatto("piatto2_test", "ricetta3_test", 0.25, dataInizio2, dataFine2);
        gestoreRepository.getPiattiRepository().insertPiatto(piattoTest2);

        //test metodo
        new InizializzaMenuTematico(gestoreRepository).execute(session, cliView);
        MenuTematico menuTematico = gestoreRepository.getMenuTematiciRepository().getMenuTematicoFromNome("menuTematico_test");

		List<String> listaPiatti = new ArrayList<>();
        listaPiatti.add("piatto1_test");
        listaPiatti.add("piatto2_test");

        LocalDate dataInizio = LocalDate.of(2023,1,1);
        LocalDate dataFine = LocalDate.of(2023,1,10);

        MenuTematico menuTematicoTest = new MenuTematico("menuTematico_test", listaPiatti, dataInizio, dataFine, 0.5);

        assertEquals(menuTematico, menuTematicoTest);
    }
}
