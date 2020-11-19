package P2P3;

import java.sql.SQLException;
import java.util.List;

public interface AdresDao {

    public Adres findById(int ID);

    public boolean save(Adres adres);

    public boolean update(Adres adres) throws SQLException;

    public void delete(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger);

    public List<Adres> findAll();

}
