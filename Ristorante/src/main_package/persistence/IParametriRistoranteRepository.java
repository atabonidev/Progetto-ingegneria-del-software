package main_package.persistence;

public interface IParametriRistoranteRepository {
    void setCaricoLavoroPerPersona(int caricoLavoro);
    int getCaricoLavoroPerPersona();
    void setNumeroPostiASedere(int numPosti);
    int getNumeroPostiASedere();
}
