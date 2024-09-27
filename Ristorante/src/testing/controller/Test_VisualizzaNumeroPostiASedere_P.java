package testing.controller;

import main_package.controller.handlers.visualizzaParametroHandlers.ShowNumeroPostiASedere;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.persistence.impl.ServicesFactory;
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
public class Test_VisualizzaNumeroPostiASedere_P {
    Session session = new Session(null, State.LOGGED);
    View cliView;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_VisualizzaNumeroPostiASedere_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(outputStreamCaptor)));
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
    void VisualizzaNumeroPostiASedere(){
        new ShowNumeroPostiASedere(new ServicesFactory("jdbc:sqlite:RistoranteTest.db", "src/testing/filesTest/parametriConfigurazioneTEST.json", "src/testing/filesTest/parametriRistoranteTEST.json").getGestoreRepository()).execute(session, cliView);

        String expectedOutput = "23";

        String formatoCorretto = "\n";
        //Normalizzazione del testo generato sostituendo tutti i separatori di riga con il formato corretto
        String testoGeneratoNormalizzato = outputStreamCaptor.toString().trim().replaceAll("\r\n|\r|\n", formatoCorretto);

        assertEquals(expectedOutput, testoGeneratoNormalizzato);
    }
}
