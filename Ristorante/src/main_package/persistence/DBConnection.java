package main_package.persistence;
import main_package.persistence.impl.ParametriConfigurazioneRepository;

import java.io.File;
import java.sql.*;

public class DBConnection {
    private Connection connection;

    public DBConnection(String connectionDBString) {
        try {
            this.connection = DriverManager.getConnection(connectionDBString);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setUpDb(JsonManager jsonManager) {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS Prodotto (\n" +
                    "    NomeProdotto varchar(255) NOT NULL,\n" +
                    "    TipoProdotto varchar(255) NOT NULL,\n" +
                    "    UnitaDiMisura varchar(255) NOT NULL,\n" +
                    "    ConsumoProCapite double NULL,\n" +
                    "    PRIMARY KEY(NomeProdotto, TipoProdotto),\n" +
                    "    CONSTRAINT CHK_TipoProdotto CHECK (TipoProdotto IN ('ingrediente', 'bevanda_servita', 'genere_extra')),\n" +
                    "    CONSTRAINT CHK_UnitaDiMisura CHECK (UnitaDiMisura IN ('kg', 'litri', 'ettogrammi'))\n" +
                    ");";

            Statement statement = this.connection.createStatement();
            boolean result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS Ricetta (\n" +
                    "    NomeRicetta varchar(255) NOT NULL,\n" +
                    "    Porzioni int NOT NULL,\n" +
                    "    CaricoLavoroPorzione double NOT NULL,\n" +
                    "    PRIMARY KEY(NomeRicetta)\n" +
                    ");\n";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS Magazzino (\n" +
                    "    NomeProdotto varchar(255) NOT NULL,\n" +
                    "    TipoProdotto varchar(255) NOT NULL,\n" +
                    "    Quantita double NOT NULL,\n" +
                    "    PRIMARY KEY(NomeProdotto, TipoProdotto),\n" +
                    "    FOREIGN KEY(NomeProdotto, TipoProdotto) REFERENCES Prodotto(NomeProdotto, TipoProdotto)\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS Piatto (\n" +
                    "    NomePiatto varchar(255) NOT NULL,\n" +
                    "    Ricetta varchar(255) NOT NULL,\n" +
                    "    DataInizioValidita date NOT NULL,\n" +
                    "    DataFineValidita date NOT NULL,\n" +
                    "    CaricoLavoroPiatto double NOT NULL,\n" +
                    "    CONSTRAINT CHK_PeriodoValidita CHECK (DataInizioValidita <= DataFineValidita),\n" +
                    "    PRIMARY KEY(NomePiatto),\n" +
                    "    FOREIGN KEY(Ricetta) REFERENCES Ricetta(NomeRicetta) ON DELETE CASCADE\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS MenuTematico (\n" +
                    "    NomeMenuTematico varchar(255) NOT NULL,\n" +
                    "    DataInizioValidita date NOT NULL,\n" +
                    "    DataFineValidita date NOT NULL,\n" +
                    "    CaricoLavoroMenuTematico double NOT NULL,\n" +
                    "    CONSTRAINT CHK_PeriodoValidita CHECK (DataInizioValidita <= DataFineValidita),\n" +
                    "    PRIMARY KEY(NomeMenuTematico)\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS Prenotazione (\n" +
                    "    IdPrenotazione varchar(255) NOT NULL,\n" +
                    "    NumeroCoperti int NOT NULL,\n" +
                    "    DataDiPrenotazione date NOT NULL,\n" +
                    "    PRIMARY KEY(IdPrenotazione)\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS Utente (\n" +
                    "    Id INTEGER PRIMARY KEY,\n" +
                    "    NomeUtente varchar(255) NOT NULL,\n" +
                    "    Password varchar(255) NOT NULL,\n" +
                    "    TipoUtente varchar(255) NOT NULL,\n" +
                    "    CONSTRAINT CHK_TipoUtente CHECK (TipoUtente IN ('gestore', 'magazziniere', 'addetto_prenotazioni'))\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS RicettaIngrediente (\n" +
                    "    Ricetta varchar(255) NOT NULL,\n" +
                    "    Ingrediente varchar(255) NOT NULL,\n" +
                    "    TipoProdotto varchar(255) NOT NULL,\n" +
                    "    Quantita double NOT NULL,\n" +
                    "    CONSTRAINT CHK_TipoProdotto CHECK (TipoProdotto = 'ingrediente'),\n" +
                    "    PRIMARY KEY(Ricetta, Ingrediente, TipoProdotto),\n" +
                    "    FOREIGN KEY(Ricetta) REFERENCES Ricetta(NomeRicetta) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY(Ingrediente, TipoProdotto) REFERENCES Prodotto(NomeProdotto, TipoProdotto)\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS MenuTematicoPiatto (\n" +
                    "    MenuTematico varchar(255) NOT NULL,\n" +
                    "    Piatto varchar(255) NOT NULL,\n" +
                    "    PRIMARY KEY(MenuTematico, Piatto),\n" +
                    "    FOREIGN KEY(MenuTematico) REFERENCES MenuTematico(NomeMenuTematico) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY(Piatto) REFERENCES Piatto(NomePiatto) ON DELETE CASCADE\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS PrenotazionePiatto (\n" +
                    "    Prenotazione varchar(255) NOT NULL,\n" +
                    "    Piatto varchar(255) NOT NULL,\n" +
                    "    Quantita int NOT NULL,\n" +
                    "    PRIMARY KEY(Prenotazione, Piatto),\n" +
                    "    FOREIGN KEY(Prenotazione) REFERENCES Prenotazione(IdPrenotazione) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY(Piatto) REFERENCES Piatto(NomePiatto) ON DELETE CASCADE\n" +
                    ");";
            result = statement.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS PrenotazioneMenuTematico (\n" +
                    "    Prenotazione varchar(255) NOT NULL,\n" +
                    "    MenuTematico varchar(255) NOT NULL,\n" +
                    "    Quantita int NOT NULL,\n" +
                    "    PRIMARY KEY(Prenotazione, MenuTematico),\n" +
                    "    FOREIGN KEY(Prenotazione) REFERENCES Prenotazione(IdPrenotazione) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY(MenuTematico) REFERENCES MenuTematico(NomeMenuTematico) ON DELETE CASCADE\n" +
                    ");";
            result = statement.execute(sql);

            IParametriConfigurazioneRepository parametriConfigurazioneRepository = new ParametriConfigurazioneRepository(jsonManager);
            if(!parametriConfigurazioneRepository.areUtentiDefaultInseriti()){
                sql = "INSERT INTO Utente (NomeUtente, Password, TipoUtente) " +
                        " VALUES ('g', 'pg', 'gestore'), ('m', 'pm', 'magazziniere'), ('a', 'pa', 'addetto_prenotazioni')";
                result = statement.execute(sql);
                parametriConfigurazioneRepository.setUtentiDefaultInseriti(true);
            }

            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
