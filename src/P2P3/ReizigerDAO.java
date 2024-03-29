package P2P3;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {


    public boolean save(Reiziger reiziger);

    public boolean update(Reiziger reiziger) throws SQLException;

    public void delete(Reiziger reiziger) throws SQLException;

    public Reiziger findById(int id);

    public List<Reiziger> findByGbdatum(String datum);

    public List<Reiziger> findAll();

}
