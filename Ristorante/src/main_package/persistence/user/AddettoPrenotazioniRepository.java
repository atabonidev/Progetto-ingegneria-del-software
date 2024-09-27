package main_package.persistence.user;

import main_package.persistence.*;

public class AddettoPrenotazioniRepository {
    private IPrenotazioniRepository prenotazioniRepository;
    private IPiattiRepository piattiRepository;
    private IMenuTematiciRepository menuTematiciRepository;
    private IParametriConfigurazioneRepository parametriConfigurazioneRepository;
    private IParametriRistoranteRepository parametriRistoranteRepository;
    private IUtentiRepository utentiRepository;

    public AddettoPrenotazioniRepository(IPrenotazioniRepository prenotazioniRepository, IPiattiRepository piattiRepository, IMenuTematiciRepository menuTematiciRepository, IParametriConfigurazioneRepository parametriConfigurazioneRepository, IParametriRistoranteRepository parametriRistoranteRepository, IUtentiRepository utentiRepository) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.piattiRepository = piattiRepository;
        this.menuTematiciRepository = menuTematiciRepository;
        this.parametriConfigurazioneRepository = parametriConfigurazioneRepository;
        this.parametriRistoranteRepository = parametriRistoranteRepository;
        this.utentiRepository = utentiRepository;
    }

    public IPrenotazioniRepository getPrenotazioniRepository() {
        return prenotazioniRepository;
    }

    public IPiattiRepository getPiattiRepository() {
        return piattiRepository;
    }

    public IMenuTematiciRepository getMenuTematiciRepository() {
        return menuTematiciRepository;
    }

    public IParametriConfigurazioneRepository getParametriConfigurazioneRepository() {
        return parametriConfigurazioneRepository;
    }

    public IParametriRistoranteRepository getParametriRistoranteRepository() {
        return parametriRistoranteRepository;
    }

    public IUtentiRepository getUtentiRepository() {
        return utentiRepository;
    }
}
