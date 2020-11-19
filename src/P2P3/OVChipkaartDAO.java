package P2P3;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    public OVChipkaart findByKaartNummer(int kaart_nummer) throws SQLException;

    public boolean save (OVChipkaart ovChipkaart);

    public boolean update(OVChipkaart ovChipkaart) throws SQLException;

    public void delete(OVChipkaart ovChipkaart) throws SQLException;

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);

    public List<OVChipkaart> findAll();



}
