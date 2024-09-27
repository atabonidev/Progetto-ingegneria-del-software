package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InitCaricoLavoroPerPersona;
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
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_InizializzaCaricoLavoroPerPersona_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    int caricoLavoroPerPersona;

    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_InizializzaCaricoLavoroPerPersona_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        caricoLavoroPerPersona =  gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
    }

    @AfterEach
    public void tearDown() {
        gestoreRepository.getParametriRistoranteRepository().setCaricoLavoroPerPersona(caricoLavoroPerPersona);
        inputDati.close();
    }

    @Test
    public void inizializzaCaricoLavoroPerPersona() {
        new InitCaricoLavoroPerPersona(gestoreRepository).execute(session, cliView);
        int caricoLavoroPerPersonaImpostato = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();

        assertEquals(5, caricoLavoroPerPersonaImpostato);
    }



}
