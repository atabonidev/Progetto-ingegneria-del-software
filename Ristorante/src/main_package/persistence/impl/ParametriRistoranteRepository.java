package main_package.persistence.impl;

import com.google.gson.Gson;
import main_package.persistence.IParametriRistoranteRepository;
import main_package.persistence.JsonManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParametriRistoranteRepository implements IParametriRistoranteRepository {

    private JsonManager jsonManager;

    public ParametriRistoranteRepository(JsonManager jsonManager) {
        this.jsonManager = jsonManager;
    }

    @Override
    public void setCaricoLavoroPerPersona(int caricoLavoro) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriRistorantePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriRistorante parametriRistorazione = gson.fromJson(reader, ParametriRistorante.class);
            parametriRistorazione.setCaricoLavoroPerPersona(caricoLavoro);

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriRistorantePath())) {
                writer.write(gson.toJson(parametriRistorazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCaricoLavoroPerPersona() {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriRistorantePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriRistorante parametriRistorazione = gson.fromJson(reader, ParametriRistorante.class);
            return parametriRistorazione.getCaricoLavoroPerPersona();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setNumeroPostiASedere(int numPosti) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriRistorantePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriRistorante parametriRistorazione = gson.fromJson(reader, ParametriRistorante.class);
            parametriRistorazione.setNumeroPostiASedere(numPosti);

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriRistorantePath())) {
                writer.write(gson.toJson(parametriRistorazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumeroPostiASedere() {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriRistorantePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriRistorante parametriRistorazione = gson.fromJson(reader, ParametriRistorante.class);
            return parametriRistorazione.getNumeroPostiASedere();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //***********************************************************
    //* INNER CLASS PER RAPPRESENTARE I PARAMETRI DEL RISORANTE *
    //***********************************************************

    static class ParametriRistorante {
        private int caricoLavoroPerPersona;
        private int numeroPostiASedere;

        // Metodi getter e setter

        public int getCaricoLavoroPerPersona() {
            return caricoLavoroPerPersona;
        }

        public void setCaricoLavoroPerPersona(int caricoLavoroPerPersona) {
            this.caricoLavoroPerPersona = caricoLavoroPerPersona;
        }

        public int getNumeroPostiASedere() {
            return numeroPostiASedere;
        }

        public void setNumeroPostiASedere(int numeroPostiASedere) {
            this.numeroPostiASedere = numeroPostiASedere;
        }
    }

}

