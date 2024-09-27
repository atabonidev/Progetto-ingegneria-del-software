package testing.controller;

import main_package.controller.handlers.EffettuaSpesa;
import main_package.model.*;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.persistence.user.GestoreRepository;
import main_package.persistence.user.MagazziniereRepository;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_EffettuaSpesa_P {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    AddettoPrenotazioniRepository addettoPrenotazioniRepository = TestServices.getInstance().getServicesFactory().getAddettoPrenotazioniRepository();
    MagazziniereRepository magazziniereRepository = TestServices.getInstance().getServicesFactory().getMagazziniereRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_EffettuaSpesa_P.txt");
        try {
            inputDati = new InputDati(new Scanner(new BufferedReader(new FileReader(file))), new OutputUtils(new PrintWriter(new ByteArrayOutputStream())));
            cliView = new CLIView(inputDati);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
    }

    @AfterEach
    public void tearDown() {
        addettoPrenotazioniRepository.getPrenotazioniRepository().clearTable();
        magazziniereRepository.getMagazzinoRepository().clearTable();
        gestoreRepository.getRicetteRepository().deleteRicetta("ricetta_EffettuaSpesa_test");
        inputDati.close();
    }

    @Test
    public void EffettuaSpesa(){
        //creazione ricetta
        Map<String, Double> ingredienti1 = new HashMap<>();
        ingredienti1.put("i1", 1.0);
        ingredienti1.put("i2", 3.0);
        Ricetta ricetta1 = new Ricetta("ricetta_EffettuaSpesa_test", ingredienti1, 1, 0.25);
        gestoreRepository.getRicetteRepository().insertRicetta(ricetta1);

        //creazione piatto
        LocalDate dataInizio1 = LocalDate.now();
        LocalDate dataFine1 = LocalDate.now();
        Piatto piattoTest1 = new Piatto("piatto_EffettuaSpesa_test", "ricetta_EffettuaSpesa_test", 0.25, dataInizio1, dataFine1);
        gestoreRepository.getPiattiRepository().insertPiatto(piattoTest1);

        //creazione prenotazione Localdate.now
        Prenotazione prenotazioneTest = new Prenotazione(1, LocalDate.now(), Collections.singletonMap("piatto_EffettuaSpesa_test", 1), new HashMap<>());
        addettoPrenotazioniRepository.getPrenotazioniRepository().aggiungiPrenotazione(prenotazioneTest);

        //effettuo spesa
        new EffettuaSpesa(magazziniereRepository).execute(session, cliView);
        TabellaProdottoQuantita magazzino = magazziniereRepository.getMagazzinoRepository().getMagazzino();

        HashMap<Prodotto, Double> prodottiInseriti = new HashMap<>();
        prodottiInseriti.put(new Prodotto("i1", TipoProdotto.INGREDIENTE, UnitaMisura.KG), 1.1);
        prodottiInseriti.put(new Prodotto("i2", TipoProdotto.INGREDIENTE, UnitaMisura.KG), 3.3);
        TabellaProdottoQuantita magazzinoExpected = new TabellaProdottoQuantita(prodottiInseriti);

        //controllo risultato ottenuto
        assertEquals(magazzino, magazzinoExpected);
    }
}
