package main_package.entrypoint;
import main_package.controller.ControllerBase;
import main_package.controller.ControllerMagazziniere;
import main_package.persistence.user.MagazziniereRepository;
import main_package.persistence.impl.ServicesFactory;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;

import java.io.PrintWriter;
import java.util.Scanner;

public class MainMagazziniere {
    public static void main(String[] args) {

        View view = new CLIView(new InputDati(new Scanner(System.in), new OutputUtils(new PrintWriter(System.out))));
        MagazziniereRepository magazziniereRepository = new ServicesFactory("jdbc:sqlite:Ristorante.db", "files/parametriConfigurazione.json", "files/parametriRistorante.json").getMagazziniereRepository();
        ControllerBase magazziniere = new ControllerMagazziniere(view, magazziniereRepository);
        magazziniere.run("Magazziniere ", "Accesso non abilitato, prima il gestore deve configurare i parametri iniziali!");

    }
}
