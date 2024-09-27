package main_package.entrypoint;
import main_package.controller.ControllerAddettoPrenotazioni;
import main_package.controller.ControllerBase;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.persistence.impl.ServicesFactory;
import main_package.view.CLIView;
import main_package.view.InputDati;
import main_package.view.OutputUtils;
import main_package.view.View;

import java.io.PrintWriter;
import java.util.Scanner;

public class MainAddettoPrenotazioni {
    public static void main(String[] args) {
        View view = new CLIView(new InputDati(new Scanner(System.in), new OutputUtils(new PrintWriter(System.out))));
        AddettoPrenotazioniRepository addettoPrenotazioniRepository = new ServicesFactory("jdbc:sqlite:Ristorante.db", "files/parametriConfigurazione.json", "files/parametriRistorante.json").getAddettoPrenotazioniRepository();
        ControllerBase addettoPrenotazioni = new ControllerAddettoPrenotazioni(addettoPrenotazioniRepository, view);
        addettoPrenotazioni.run("Addetto alle prenotazioni ", "Accesso non abilitato, prima il gestore deve configurare i parametri iniziali!");
    }
}
