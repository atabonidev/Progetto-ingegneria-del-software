package main_package.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu {
    private InputDati inputDati;
    final private static String CORNICE = "--------------------------------";
    //final private static String VOCE_USCITA = "0\tEsci";
    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

    private String titolo;
    private List<String> voci = new ArrayList<>();

    public MyMenu(String titolo, InputDati inputDati) {
        this.titolo = titolo;
        this.inputDati = inputDati;
    }

    public MyMenu() {
    }

    public void addVoce(String voce){
        if (this.voci == null){
            voci = new ArrayList<>();
        }
        voci.add(voce);
    }

    public int getVociSize(){
        return voci.size();
    }

    private void stampaMenu() {
        inputDati.getOutput().println("\n--- " + titolo + " ----");
        for (int i = 0; i < voci.size(); i++) {
            inputDati.getOutput().println((i) + "\t" + voci.get(i));
        }
        inputDati.getOutput().println(CORNICE + "\n");
    }

    public int scegli() {
        stampaMenu();
        return inputDati.leggiInteroConMinimoMassimo(RICHIESTA_INSERIMENTO, 0, voci.size()-1);
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void clearVoci() {
        if (this.voci != null) this.voci.clear();
    }

    public void setVoci(List<String> voci) {
        if (this.voci != null) this.voci.clear();
        this.voci = voci;
    }

}
