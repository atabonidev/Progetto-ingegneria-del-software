package testing.controller;
import main_package.controller.handlers.inizializzaParametroHandlers.InitInsiemeGeneriExtra;
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

public class Test_InizializzaInsiemeGeneriExtra {
    GestoreRepository gestoreRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository();
    Session session = new Session(null, State.LOGGED);
    View cliView;
    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_InizializzaInsiemeGeneriExtra_P.txt");
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
    public void inizializzaGeneriExtra() {
        //Suppongo che non esistano gia' i seguenti due generi extra nella tabella Prodotto
        Prodotto genereExtra1 = new Prodotto("genere_extra1_test", TipoProdotto.GENERE_EXTRA, UnitaMisura.ETTOGRAMMI);
        Prodotto genereExtra2 = new Prodotto("genere_extra2_test", TipoProdotto.GENERE_EXTRA, UnitaMisura.ETTOGRAMMI);

        new InitInsiemeGeneriExtra(gestoreRepository).execute(session, cliView);

        Map<Prodotto, Double> insiemeGeneriExtra = gestoreRepository.getGeneriExtraRepository().getInsiemeGeneriExtra();

        assertTrue(insiemeGeneriExtra.containsKey(genereExtra1));
        assertEquals(insiemeGeneriExtra.get(genereExtra1), 1);
        assertTrue(insiemeGeneriExtra.containsKey(genereExtra2));
        assertEquals(insiemeGeneriExtra.get(genereExtra2), 2);

        //Elimino i due generi extra appena inseriti in modo da lasciare inalterata la tabella di partenza
        gestoreRepository.getGeneriExtraRepository().removeGenereExtra(genereExtra1);
        gestoreRepository.getGeneriExtraRepository().removeGenereExtra(genereExtra2);
    }
}
