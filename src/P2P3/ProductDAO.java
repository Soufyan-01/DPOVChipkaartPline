package P2P3;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    public boolean save(Product product);

    public boolean update(Product product) throws SQLException;

    public void delete(Product product) throws SQLException;

    public Product findByOVChipkaart(OVChipkaart ovChipkaart);

    public List<Product> findAll();

}
