package main_package.persistence.impl;

import main_package.model.Prodotto;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.persistence.IInsiemeBevandeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InsiemeBevandeRepository implements IInsiemeBevandeRepository {

    private Connection connection;

    public InsiemeBevandeRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setInsiemeBevande(Map<Prodotto, Double> insiemeBevande) {
        String sql = "INSERT INTO Prodotto VALUES (?, ?, ?, ?)";

        for(Prodotto prodotto: insiemeBevande.keySet()){
            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, prodotto.getNome());
                pstmt.setString(2, "bevanda_servita");
                pstmt.setString(3, "litri");
                pstmt.setString(4, insiemeBevande.get(prodotto).toString());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Inserimento non riuscito -> " + prodotto.getNome());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<Prodotto, Double> getInsiemeBevande() {
        String sql = "SELECT * " +
                "FROM Prodotto " +
                "WHERE TipoProdotto = 'bevanda_servita'";

        Map<Prodotto, Double> insiemeBevande = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeProdotto = resultSet.getString("NomeProdotto");
                    double consumoProCapite = resultSet.getDouble("ConsumoProCapite");

                    Prodotto prodotto = new Prodotto(nomeProdotto, TipoProdotto.BEVANDA_SERVITA, UnitaMisura.LITRI);
                    insiemeBevande.put(prodotto, consumoProCapite);
                }
            }
        }catch (SQLException e) {
            System.out.println("Errore nel recupero delle bevande servite");
            e.printStackTrace();
        }

        return insiemeBevande;
    }

    @Override
    public void removeBevanda(Prodotto bevanda) {
        Map<Prodotto, Double> insiemeBevande = this.getInsiemeBevande();

        if (insiemeBevande.containsKey(bevanda)) {
            String sql = "DELETE FROM Prodotto WHERE NomeProdotto = ? AND TipoProdotto = 'bevanda_servita'";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, bevanda.getNome());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Eliminazione non riuscita -> " + bevanda.getNome());
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean isListaBevandeVuota() {
        return getInsiemeBevande().isEmpty();
    }
}
