package main_package.view;

import main_package.model.MenuTematico;
import main_package.model.Piatto;
import main_package.model.Prodotto;
import main_package.model.Ricetta;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLIView implements View {
    private MyMenu menu;
    private InputDati inputDati;

    public CLIView(InputDati inputDati){
        this.inputDati = inputDati;
    }

    public void print(String str){
        inputDati.getOutput().print(str);
    }

    //Metodo per stampare il separatore
    private void printSeparetor() {
        print("**************************************************");
    }

    //Metodo che stampa il periodo di validita'
    private void printPeriodoValidita(LocalDate inizio, LocalDate fine){
        print(inizio.toString() + " - " + fine.toString());
    }

    /*===========================
    Metodi Visualizzazione Model
    ===========================*/
    @Override
    public void printTabellaProdottoValore(Map<Prodotto, Double> tabella, String titoloTabella) {
        print(titoloTabella + ":");
        for (Prodotto prodotto: tabella.keySet()) {
            print("- " + prodotto.getNome() + " -> " + tabella.get(prodotto));
        }
    }

    @Override
    public void printRicette(List<Ricetta> listaRicette) {
        int count = 1;
        printSeparetor();
        print("*\tLISTA RICETTE\t*");
        for (Ricetta ricetta: listaRicette) {
            print("[ " + ricetta.getNome() + " ]");
            print("Numero porzioni: " + ricetta.getPorzioni());
            print("Carico lavoro per porzione: " + ricetta.getCaricoLavPerPorzione());
            printIngredienti(ricetta.getIngredienti());
        }
        printSeparetor();
    }

    private void printIngredienti(Map<String, Double> ingredienti) {
        print("Lista ingredienti:");
        for (String ingrediente: ingredienti.keySet()) {
            print("- " + ingrediente+ " -> " + ingredienti.get(ingrediente));
        }
    }

    @Override
    public void printNomiPiatti(List<String> listaPiatti) {
        print("*\tLISTA PIATTI\t*");
        for (String piatto: listaPiatti) {
            print("\t- " + piatto);
        }
    }

    public void printPiatti(List<Piatto> listaPiatti) {
        print("*\tLISTA PIATTI\t*");
        for (Piatto piatto: listaPiatti) {
            print("[ " + piatto.getNome() + " ]");
            print("\t- " + piatto.getRicetta());
            print("\t- " + piatto.getInizioValidita().toString());
            print("\t- " + piatto.getFineValidita().toString());
            print("\t- " + piatto.getCaricoLavoroPiatto());
        }
    }

    @Override
    public void printMenuTematici(List<MenuTematico> listaMenuTematici) {
        printSeparetor();
        print("*\tLISTA MENU TEMATICI\t*");
        for (MenuTematico menuTematico : listaMenuTematici) {
            print("[ " + menuTematico.getNome() + " ]");
            print("\t- " + menuTematico.getDataInizioValidita().toString());
            print("\t- " + menuTematico.getDataFineValidita().toString());
            print("\t- " + menuTematico.getCaricoLavMenuTematico());
            printNomiPiatti(menuTematico.getListaPiatti());
        }
        printSeparetor();
    }

    @Override
    public void printCorrispondenzaPiattoRicetta(Map<String, String> corrispondenzePiattiRicetta) {
        print("*\tCORRISPONDENZA PIATTO - RICETTA\t*");
        for (String piatto : corrispondenzePiattiRicetta.keySet()) {
            print(piatto + " - " + corrispondenzePiattiRicetta.get(piatto));
        }
    }

    /*===========================
    Visualizzazione e gestione menu
    ===========================*/
    @Override
    public void creaMenu(String titolo) {
        this.menu = new MyMenu(titolo, inputDati);
    }

    @Override
    public void setVociMenu(List<String> voci) {
        menu.clearVoci();
        menu.setVoci(voci);
    }

    public void clearVociMenu(){
        menu.clearVoci();
    }
    public void addVoceMenu(String voce){
        menu.addVoce(voce);
    }
    public int scegliVoceMenu() {
        return menu.scegli();
    }
    public void setTitoloMenu(String titolo) {
        menu.setTitolo(titolo);
    }

    /*===========================
    Metodi di lettura
    ===========================*/
    @Override
    public int leggiInteroPositivo(String s) {
        return inputDati.leggiInteroPositivo(s);
    }

    @Override
    public int leggiInteroConMinimoMassimo(String s, int minimo, int massimo) {
        return inputDati.leggiInteroConMinimoMassimo(s, minimo, massimo);
    }

    @Override
    public double leggiDoubleConMinimoEscluso(String s, double minimo) {
        return inputDati.leggiDoubleConMinimoEscluso(s, minimo);
    }

    @Override
    public double leggiDoubleConMinimoMassimoEsclusi(String s, double minimo, double massimo) {
        return inputDati.leggiDoubleConMinimoMassimoEsclusi(s, minimo, massimo);
    }

    @Override
    public String leggiStringaNonVuota(String s) {
        return inputDati.leggiStringaNonVuota(s);
    }

    @Override
    public boolean leggiYesOrNo(String s) {
        return inputDati.yesOrNo(s);
    }

    @Override
    public LocalDate leggiData(String s) {
        return inputDati.leggiData(s);
    }

    @Override
    public LocalDate leggiDataConMinimo(String messaggio, LocalDate dataMinima) {
        return inputDati.leggiDataConMinimo(messaggio, dataMinima);
    }
}
