package main_package.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputDati{
    private Scanner lettore;
    private OutputUtils output;

    private final String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
    private final String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private final String ERRORE_MINIMO_MASSIMO = "Attenzione: e' richiesto un valore compreso tra %.2f e %.2f (estremi esclusi) ";

    private final String ERRORE_STRINGA_VUOTA = "Attenzione: non hai inserito alcun carattere";
    private final String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    private final String MESSAGGIO_AMMISSIBILI = "Attenzione: i caratteri ammissibili sono: ";

    private final char RISPOSTA_SI = 'S';
    private final char RISPOSTA_NO = 'N';

    public InputDati(Scanner lettore, OutputUtils output){
        this.lettore = lettore;
        this.output = output;
    }

    public String leggiStringa(String messaggio) {
        output.print(messaggio);
        return lettore.nextLine();
    }

    public String leggiStringaNonVuota(String messaggio) {
        boolean finito = false;
        String lettura;
        do {
            lettura = leggiStringa(messaggio);
            lettura = lettura.trim();
            if (lettura.length() > 0)
                finito = true;
            else
                output.println(ERRORE_STRINGA_VUOTA);
        } while (!finito);

        return lettura;
    }

    public char leggiChar(String messaggio) {
        boolean finito = false;
        char valoreLetto = '\0';
        do {
            output.print(messaggio);
            String lettura = lettore.next();
            lettore.nextLine();
            if (lettura.length() > 0) {
                valoreLetto = lettura.charAt(0);
                finito = true;
            } else {
                output.println(ERRORE_STRINGA_VUOTA);
                lettore.nextLine();
            }
        } while (!finito);
        return valoreLetto;
    }

    public char leggiUpperChar(String messaggio, String ammissibili) {
        boolean finito = false;
        char valoreLetto = '\0';
        do {
            valoreLetto = leggiChar(messaggio);
            valoreLetto = Character.toUpperCase(valoreLetto);
            if (ammissibili.indexOf(valoreLetto) != -1)
                finito = true;
            else
                output.println(MESSAGGIO_AMMISSIBILI + ammissibili);
        } while (!finito);
        return valoreLetto;
    }

    public int leggiIntero(String messaggio) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            output.print(messaggio);
            try {
                valoreLetto = lettore.nextInt();
                lettore.nextLine();
                finito = true;
            } catch (InputMismatchException e) {
                output.println(ERRORE_FORMATO);
                lettore.nextLine();
            }
        } while (!finito);
        return valoreLetto;
    }

    public int leggiInteroPositivo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 1);
    }

    public int leggiInteroNonNegativo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 0);
    }

    public int leggiInteroConMinimo(String messaggio, int minimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo)
                finito = true;
            else
                output.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    public int leggiInteroConMinimoMassimo(String messaggio, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
                finito = true;
            else
                output.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    public double leggiDouble(String messaggio) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            output.print(messaggio);
            try {
                valoreLetto = lettore.nextDouble();
                finito = true;
            } catch (InputMismatchException e) {
                output.println(ERRORE_FORMATO);
                lettore.nextLine();
            }
        } while (!finito);
        return valoreLetto;
    }

    public double leggiDoubleConMinimoEscluso(String messaggio, double minimo) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            valoreLetto = leggiDouble(messaggio);
            if (valoreLetto > minimo)
                finito = true;
            else
                output.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    public double leggiDoubleConMinimoMassimoEsclusi(String messaggio, double minimo, double massimo) {
        boolean finito = false;
        double valoreLetto = 0;
        do {
            valoreLetto = leggiDouble(messaggio);
            if (valoreLetto > minimo && valoreLetto < massimo)
                finito = true;
            else
                output.println(String.format(ERRORE_MINIMO_MASSIMO, minimo, massimo));
        } while (!finito);

        return valoreLetto;
    }

    public boolean yesOrNo(String messaggio) {
        String mioMessaggio = messaggio + "(" + RISPOSTA_SI + "/" + RISPOSTA_NO + "): ";
        char valoreLetto = leggiUpperChar(mioMessaggio, String.valueOf(RISPOSTA_SI) + String.valueOf(RISPOSTA_NO));

        if (valoreLetto == RISPOSTA_SI)
            return true;
        else
            return false;
    }

    public LocalDate leggiData(String messaggio){
        int y;
        int m;
        int d;

        LocalDate ld = null;
        boolean isValidDate = false;

        output.println(messaggio);
        do{
            d = leggiInteroPositivo("Inserisci giorno >>  ");
            m = leggiInteroPositivo("Inserisci mese >>  ");
            y = leggiInteroPositivo("Inserisci anno >>  ");

            try {
                ld = LocalDate.of( y , m , d );
                isValidDate = true;
            } catch (DateTimeException e){
                output.println("La data inserita non è valida");
            }
        }while (!isValidDate);

        return ld;
    }

    public LocalDate leggiDataConMinimo(String messaggio, LocalDate dataMinima) {
        LocalDate dataRichiesta;
        do{
            dataRichiesta = leggiData(messaggio);

            if (dataMinima.isAfter(dataRichiesta)){
                output.println("Il periodo di validità non è valido!!");
            }
        } while (dataMinima.isAfter(dataRichiesta));
        return dataRichiesta;
    }

    public Scanner getLettore() {
        return lettore;
    }

    public OutputUtils getOutput() {
        return output;
    }

    public void close() {
        lettore.close();
        output.close();
    }
}
