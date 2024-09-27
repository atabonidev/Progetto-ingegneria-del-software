package testing.controller;

import main_package.controller.handlers.AggiungiPrenotazione;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_AggiungiPrenotazione_S {
    AddettoPrenotazioniRepository addettoPrenotazioniRepository = TestServices.getInstance().getServicesFactory().getAddettoPrenotazioniRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputStream systemIn;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_AggiungiPrenotazione_S.txt");
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
    void AggiungiPrenotazione(){
        new AggiungiPrenotazione(addettoPrenotazioniRepository).execute(session, cliView);

        String expectedOutput = "Inserire numero di coperti prenotazione >>  Inserire data di prenotazione >>  \n" +
                "Inserisci giorno >>  Inserisci mese >>  Inserisci anno >>  Impossibile effettuare prenotazione per questa data";

        String formatoCorretto = "\n";
        //Normalizzazione del testo generato sostituendo tutti i separatori di riga con il formato corretto
        String testoGeneratoNormalizzato = outputStreamCaptor.toString().trim().replaceAll("\r\n|\r|\n", formatoCorretto);

        assertEquals(expectedOutput, testoGeneratoNormalizzato);
    }
}
