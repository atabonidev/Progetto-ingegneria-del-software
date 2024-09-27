package testing.controller;

import main_package.controller.handlers.inizializzaParametroHandlers.InitNumeroPostiASedere;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.persistence.impl.ServicesFactory;
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

public class Test_InizializzaNumeroPostiASedere_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    int numeroPostiASedere;

    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_InizializzaNumeroPostiASedere_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        numeroPostiASedere = gestoreRepository.getParametriRistoranteRepository().getNumeroPostiASedere();
    }

    @AfterEach
    public void tearDown() {
        gestoreRepository.getParametriRistoranteRepository().setNumeroPostiASedere(numeroPostiASedere);
        inputDati.close();
    }

    @Test
    void InizializzaNumeroPostiASedere(){
        new InitNumeroPostiASedere(gestoreRepository).execute(session, cliView);
        int numeroPostiRegistrato = gestoreRepository.getParametriRistoranteRepository().getNumeroPostiASedere();

        assertEquals(23, numeroPostiRegistrato);
    }
}
