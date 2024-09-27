package testing.controller;

import main_package.controller.handlers.Login;
import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.model.user.Utente;
import main_package.persistence.DBConnection;
import main_package.persistence.IUtentiRepository;
import main_package.persistence.impl.ServicesFactory;
import main_package.persistence.impl.UtentiRepository;
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

import static org.junit.jupiter.api.Assertions.*;

public class Test_Login_S1 {
    IUtentiRepository utentiRepository = TestServices.getInstance().getServicesFactory().getGestoreRepository().getUtentiRepository();
    Session session = new Session(null, State.UNLOGGED);
    View cliView;

    InputDati inputDati;

    @BeforeEach
    public void setUp(){
        File file = new File("./src/testing/input_cases/case_Login_S1.txt");
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
    void doLogin() {
        Login login = new Login(utentiRepository, "gestore");

        session = login.execute(session, cliView);

        Utente gestore = session.getUtente();

        assertNull(gestore);
    }
}
