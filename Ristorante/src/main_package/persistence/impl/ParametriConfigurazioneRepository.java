package main_package.persistence.impl;

import com.google.gson.Gson;
import main_package.persistence.IParametriConfigurazioneRepository;
import main_package.persistence.JsonManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ParametriConfigurazioneRepository implements IParametriConfigurazioneRepository {

    private JsonManager jsonManager;

    public ParametriConfigurazioneRepository(JsonManager jsonManager) {
        this.jsonManager = jsonManager;
    }

    @Override
    public void setAccessoAbilitato(boolean isAbilitato) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            parametriConfigurazione.setAccessoAbilitato(isAbilitato);

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriConfigurazionePath())) {
                writer.write(gson.toJson(parametriConfigurazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAccessoAbilitato() {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            return parametriConfigurazione.isAccessoAbilitato();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setOperazioniAutomaticheAvvenute(LocalDate dataDiRimozione) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            parametriConfigurazione.setOperazioniAutomaticheAvvenute(dataDiRimozione.toString());

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriConfigurazionePath())) {
                writer.write(gson.toJson(parametriConfigurazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean areOperazioniAutomaticheAvvenute() {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            LocalDate dataOperazioniAutomatiche = LocalDate.parse(parametriConfigurazione.getOperazioniAutomaticheAvvenute());
            return dataOperazioniAutomatiche.isEqual(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setSpesaEffettuata(LocalDate dataDiRimozione) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            parametriConfigurazione.setSpesaEffettuata(dataDiRimozione.toString());

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriConfigurazionePath())) {
                writer.write(gson.toJson(parametriConfigurazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSpesaEffettuata() {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            LocalDate dataSpesaEffettuata= LocalDate.parse(parametriConfigurazione.getSpesaEffettuata());
            return dataSpesaEffettuata.isEqual(LocalDate.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean areUtentiDefaultInseriti() {
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();
            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            return parametriConfigurazione.areUtentiDefaultInseriti();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setUtentiDefaultInseriti(boolean areUtentiDefaultInseriti) {
        // Lettura del file JSON
        try (FileReader reader = new FileReader(jsonManager.getParametriConfigurazionePath())) {
            Gson gson = jsonManager.getBuilder().create();

            ParametriConfigurazione parametriConfigurazione = gson.fromJson(reader, ParametriConfigurazione.class);
            parametriConfigurazione.setAreUtentiDefaultInseriti(areUtentiDefaultInseriti);

            // Scrittura dei valori modificati nel file JSON originale
            try (FileWriter writer = new FileWriter(jsonManager.getParametriConfigurazionePath())) {
                writer.write(gson.toJson(parametriConfigurazione));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //***************************************************************
    //* INNER CLASS PER RAPPRESENTARE I PARAMETRI DI CONFIGURAZIONE *
    //***************************************************************

    static class ParametriConfigurazione {
        private boolean accessoAbilitato;
        private String operazioniAutomaticheAvvenute;
        private String spesaEffettuata;
        private boolean areUtentiDefaultInseriti;

        // Metodi getter e setter
        public boolean isAccessoAbilitato() {
            return accessoAbilitato;
        }

        public void setAccessoAbilitato(boolean accessoAbilitato) {
            this.accessoAbilitato = accessoAbilitato;
        }

        public String getOperazioniAutomaticheAvvenute() {
            return operazioniAutomaticheAvvenute;
        }

        public void setOperazioniAutomaticheAvvenute(String operazioniAutomaticheAvvenute) {
            this.operazioniAutomaticheAvvenute = operazioniAutomaticheAvvenute;
        }

        public String getSpesaEffettuata() {
            return spesaEffettuata;
        }

        public void setSpesaEffettuata(String spesaEffettuata) {
            this.spesaEffettuata = spesaEffettuata;
        }

        public boolean areUtentiDefaultInseriti() {
            return areUtentiDefaultInseriti;
        }

        public void setAreUtentiDefaultInseriti(boolean areUtentiDefaultInseriti) {
            this.areUtentiDefaultInseriti = areUtentiDefaultInseriti;
        }
    }
}
