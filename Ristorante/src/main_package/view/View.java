package main_package.view;

/*
Interfaccia che rappresenta le azioni che possono essere fatte nella main_package.view.

Viene implementato da una classe che sfrutta i metodi sia di InputDati che di MyMenu per implementare i metodi della View
 */

import main_package.model.MenuTematico;
import main_package.model.Piatto;
import main_package.model.Prodotto;
import main_package.model.Ricetta;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface View {
    //_____Metodi di visualizzazione model___________
    void print(String str);
    //mostra solo l'insieme dei prodotti ma senza il consumo procapite associato
    void printTabellaProdottoValore(Map<Prodotto, Double> tabella, String titoloTabella);
    void printRicette(List<Ricetta> listaRicette);
    void printNomiPiatti(List<String> listPiatti);
    void printPiatti(List<Piatto> listaPiatti);
    void printMenuTematici(List<MenuTematico> listaMenuTematici);
    void printCorrispondenzaPiattoRicetta(Map<String, String> corrispondenzePiattiRicetta);

    //_____Metodi per i menu_________________________
    void creaMenu(String titolo);
    void setVociMenu(List<String> voci);
    //void addVoceMenu();
    int scegliVoceMenu();
    void setTitoloMenu(String titolo);

    //_____Metodi di lettura input esterni__________
    int leggiInteroPositivo(String s);
    int leggiInteroConMinimoMassimo(String s, int minimo, int massimo);
    double leggiDoubleConMinimoEscluso(String messaggio, double minimo);
    double leggiDoubleConMinimoMassimoEsclusi(String messaggio, double minimo, double massimo);
    String leggiStringaNonVuota(String s);
    boolean leggiYesOrNo(String s);
    LocalDate leggiData(String messaggio);
    LocalDate leggiDataConMinimo(String messaggio, LocalDate dataMinima);
}
