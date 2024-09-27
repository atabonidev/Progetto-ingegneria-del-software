package main_package.entrypoint;
import main_package.controller.ControllerBase;
import main_package.controller.ControllerGestore;
import main_package.persistence.user.GestoreRepository;
import main_package.persistence.impl.ServicesFactory;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;

import java.io.PrintWriter;
import java.util.Scanner;

public class MainGestore {
    public static void main(String[] args) {

        View view = new CLIView(new InputDati(new Scanner(System.in), new OutputUtils(new PrintWriter(System.out, true))));
        GestoreRepository gestoreRepository = new ServicesFactory("jdbc:sqlite:Ristorante.db", "files/parametriConfigurazione.json", "files/parametriRistorante.json").getGestoreRepository();
        ControllerBase gestore = new ControllerGestore(view, gestoreRepository);
        gestore.run("Gestore ", "Accesso non abilitato!");
    }
}