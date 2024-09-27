package main_package.persistence.user;

import main_package.persistence.IMagazzinoRepository;
import main_package.persistence.IParametriConfigurazioneRepository;
import main_package.persistence.IPrenotazioniRepository;
import main_package.persistence.IUtentiRepository;

public class MagazziniereRepository {
    private IMagazzinoRepository magazzinoRepository;
    private IParametriConfigurazioneRepository parametriConfigurazioneRepository;
    private IPrenotazioniRepository prenotazioniRepository;
    private IUtentiRepository utentiRepository;

    public MagazziniereRepository(IMagazzinoRepository magazzinoRepository, IParametriConfigurazioneRepository parametriConfigurazioneRepository, IPrenotazioniRepository prenotazioniRepository, IUtentiRepository utentiRepository) {
        this.magazzinoRepository = magazzinoRepository;
        this.parametriConfigurazioneRepository = parametriConfigurazioneRepository;
        this.prenotazioniRepository = prenotazioniRepository;
        this.utentiRepository = utentiRepository;
    }

    public IMagazzinoRepository getMagazzinoRepository() {
        return magazzinoRepository;
    }

    public IParametriConfigurazioneRepository getParametriConfigurazioneRepository() {
        return parametriConfigurazioneRepository;
    }

    public IPrenotazioniRepository getPrenotazioniRepository() {
        return prenotazioniRepository;
    }

    public IUtentiRepository getUtentiRepository() {
        return utentiRepository;
    }
}
