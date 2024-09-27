package main_package.persistence.user;

import main_package.persistence.*;

public class GestoreRepository {
    private IParametriConfigurazioneRepository parametriDiConfig;
    private IParametriRistoranteRepository parametriRistoranteRepository;
    private IPiattiRepository piattiRepository;
    private IRicetteRepository ricetteRepository;
    private IMenuTematiciRepository menuTematiciRepository;
    private IInsiemeBevandeRepository bevandeRepository;
    private IGeneriExtraRepository generiExtraRepository;
    private IUtentiRepository utentiRepository;

    public GestoreRepository(IParametriConfigurazioneRepository parametriDiConfig, IParametriRistoranteRepository parametriRistoranteRepository, IPiattiRepository piattiRepository, IRicetteRepository ricetteRepository, IMenuTematiciRepository menuTematiciRepository, IInsiemeBevandeRepository bevandeRepository, IGeneriExtraRepository generiExtraRepository, IUtentiRepository utentiRepository) {
        this.parametriDiConfig = parametriDiConfig;
        this.parametriRistoranteRepository = parametriRistoranteRepository;
        this.piattiRepository = piattiRepository;
        this.ricetteRepository = ricetteRepository;
        this.menuTematiciRepository = menuTematiciRepository;
        this.bevandeRepository = bevandeRepository;
        this.generiExtraRepository = generiExtraRepository;
        this.utentiRepository = utentiRepository;
    }

    public IParametriConfigurazioneRepository getParametriDiConfig() {
        return parametriDiConfig;
    }

    public IPiattiRepository getPiattiRepository() {
        return piattiRepository;
    }

    public IRicetteRepository getRicetteRepository() {
        return ricetteRepository;
    }

    public IMenuTematiciRepository getMenuTematiciRepository() {
        return menuTematiciRepository;
    }

    public IInsiemeBevandeRepository getBevandeRepository() {
        return bevandeRepository;
    }

    public IGeneriExtraRepository getGeneriExtraRepository() {
        return generiExtraRepository;
    }

    public IParametriRistoranteRepository getParametriRistoranteRepository(){
        return  parametriRistoranteRepository;
    }

    public IUtentiRepository getUtentiRepository() {
        return utentiRepository;
    }
}
