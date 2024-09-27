package main_package.persistence.impl;

import main_package.model.Prodotto;
import main_package.model.TipoProdotto;
import main_package.model.UnitaMisura;
import main_package.persistence.IGeneriExtraRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GeneriExtraRepository implements IGeneriExtraRepository {
    private Connection connection;

    public GeneriExtraRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setInsiemeGeneriExtra(Map<Prodotto, Double> insiemeGeneriExtra) {
        String sql = "INSERT INTO Prodotto " +
                "VALUES (?, ?, ?, ?)";

        for(Prodotto prodotto: insiemeGeneriExtra.keySet()){
            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, prodotto.getNome());
                pstmt.setString(2, "genere_extra");
                pstmt.setString(3, "ettogrammi");
                pstmt.setString(4, insiemeGeneriExtra.get(prodotto).toString());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Inserimento non riuscito -> " + prodotto.getNome());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<Prodotto, Double> getInsiemeGeneriExtra() {
        String sql = "SELECT * " +
                "FROM Prodotto " +
                "WHERE TipoProdotto = 'genere_extra'";

        Map<Prodotto, Double> insiemeGeneriExtra = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeProdotto = resultSet.getString("NomeProdotto");
                    double consumoProCapite = resultSet.getDouble("ConsumoProCapite");

                    Prodotto prodotto = new Prodotto(nomeProdotto, TipoProdotto.GENERE_EXTRA, UnitaMisura.ETTOGRAMMI);
                    insiemeGeneriExtra.put(prodotto, consumoProCapite);
                }
            }
        }catch (SQLException e) {
            System.out.println("Errore nel recupero dei generi extra");
            e.printStackTrace();
        }

        return insiemeGeneriExtra;
    }

    @Override
    public void removeGenereExtra(Prodotto genereExtra) {
        Map<Prodotto, Double> insiemeGeneriExtra = this.getInsiemeGeneriExtra();

        if (insiemeGeneriExtra.containsKey(genereExtra)) {
            String sql = "DELETE FROM Prodotto WHERE NomeProdotto = ? AND TipoProdotto = 'genere_extra'";

            try{
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, genereExtra.getNome());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Eliminazione non riuscita -> " + genereExtra.getNome());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isListaGeneriExtraVuota() {
        return getInsiemeGeneriExtra().isEmpty();
    }
}
