package main_package.persistence.impl;

import main_package.persistence.*;
import main_package.persistence.user.AddettoPrenotazioniRepository;
import main_package.persistence.user.GestoreRepository;
import main_package.persistence.user.MagazziniereRepository;

public class ServicesFactory {
    DBConnection dbConnection;
    JsonManager jsonManager;

    public ServicesFactory(String connectionDBString, String parametriConfigurazionePath, String parametriRistorantePath){
        this.dbConnection = new DBConnection(connectionDBString);
        this.jsonManager = new JsonManager(parametriConfigurazionePath, parametriRistorantePath);
        dbConnection.setUpDb(jsonManager);
    }

    public AddettoPrenotazioniRepository getAddettoPrenotazioniRepository() {
        IPrenotazioniRepository prenotazioniRepository = new PrenotazioniRepository(dbConnection.getConnection());
        IPiattiRepository piattiRepository = new PiattiRepository(dbConnection.getConnection());
        IMenuTematiciRepository menuTematiciRepository = new MenuTematiciRepository(dbConnection.getConnection());
        IParametriConfigurazioneRepository parametriConfigurazioneRepository = new ParametriConfigurazioneRepository(jsonManager);
        IParametriRistoranteRepository parametriRistoranteRepository = new ParametriRistoranteRepository(jsonManager);
        IUtentiRepository utentiRepository = new UtentiRepository(dbConnection.getConnection());

        return new AddettoPrenotazioniRepository(prenotazioniRepository, piattiRepository, menuTematiciRepository, parametriConfigurazioneRepository, parametriRistoranteRepository, utentiRepository);
    }

    public GestoreRepository getGestoreRepository() {
        IParametriConfigurazioneRepository parametriConfigurazioneRepository = new ParametriConfigurazioneRepository(jsonManager);
        IParametriRistoranteRepository parametriRistoranteRepository = new ParametriRistoranteRepository(jsonManager);
        IPiattiRepository piattiRepository = new PiattiRepository(dbConnection.getConnection());
        IRicetteRepository ricetteRepository = new RicetteRepository(dbConnection.getConnection());
        IMenuTematiciRepository menuTematiciRepository = new MenuTematiciRepository(dbConnection.getConnection());
        IInsiemeBevandeRepository bevandeRepository = new InsiemeBevandeRepository(dbConnection.getConnection());
        IGeneriExtraRepository generiExtraRepository = new GeneriExtraRepository(dbConnection.getConnection());
        IUtentiRepository utentiRepository = new UtentiRepository(dbConnection.getConnection());

        return new GestoreRepository(parametriConfigurazioneRepository, parametriRistoranteRepository, piattiRepository, ricetteRepository, menuTematiciRepository, bevandeRepository, generiExtraRepository, utentiRepository);
    }

    public MagazziniereRepository getMagazziniereRepository() {
        IMagazzinoRepository magazzinoRepository = new MagazzinoRepository(dbConnection.getConnection());
        IParametriConfigurazioneRepository parametriConfigurazioneRepository = new ParametriConfigurazioneRepository(jsonManager);
        IPrenotazioniRepository prenotazioniRepository = new PrenotazioniRepository(dbConnection.getConnection());
        IUtentiRepository utentiRepository = new UtentiRepository(dbConnection.getConnection());

        return new MagazziniereRepository(magazzinoRepository, parametriConfigurazioneRepository, prenotazioniRepository, utentiRepository);
    }
}
