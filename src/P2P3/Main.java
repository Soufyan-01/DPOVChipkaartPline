package P2P3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String [] args) throws SQLException {
        getConnection();
        try {
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            AdresDAOPsql adao = new AdresDAOPsql(connection);
            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
            ProductDAOPsql pDAO = new ProductDAOPsql(connection);
            odao.setRdao(rdao);
            adao.setRdao(rdao);
            rdao.setAdao(adao);
            rdao.setOdao(odao);
            pDAO.setOdao(odao);

            //testReizigerDAO(new ReizigerDAOPsql(connection));
            testReizigerDAO(rdao);
            testAdresDAO(adao, rdao);
            testOVChipkaartDAO(new OVChipkaartDAOPsql(connection, rdao), rdao);
            testProductDAOPSql(pDAO, odao, rdao);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    private static void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Soufyan01";
        connection = DriverManager.getConnection(url);
    }

    private static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println();

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r );
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1999-11-11";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //Update
        System.out.println("[Test] Eerst " + "ReizigerDAO.update();");
        System.out.println("Voor wijziging: " + sietske);
        sietske.setVoorletters("A");
        sietske.setAchternaam("Hendriks");
        sietske.setGeboortedatum(java.sql.Date.valueOf("1999-11-11"));
        rdao.update(sietske);
        System.out.println("Na wijziging: " +sietske + "\n");

        //zoeken op ID
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reizigers: ");
        rdao.findById(77);
        System.out.println();

        //zoek op geboortedatum
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers: ");
        reizigers = rdao.findByGbdatum(gbdatum);
        for (Reiziger reiziger1 : reizigers) {
            System.out.println(reiziger1.getVoorletters());
        }
        System.out.println();

        //Delete
        System.out.println("[TEST] ReizigerDao.delete" + reizigers.size());
        //rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size());

    }
    private static void testAdresDAO(AdresDao adao, ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres r : adressen) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe adres aan en persisteer deze in de database
        String gbdatum = "1998-09-09";
        Reiziger Henk = new Reiziger(78, "D", "", "Henk", java.sql.Date.valueOf(gbdatum));
        Adres adres3 = new Adres(7, "2000NJ", "4","Dunantsingel", "Gouda", Henk);

        //Het opslaan van het adres
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres3);
        adressen = adao.findAll();
        System.out.println(adressen.size() + "\n");


        //Het updaten van het adres
        System.out.println("[Test] Eerst " + "AdresDAO.update();");
        System.out.println("voor wijziging: " + adres3);
        Adres gewijzigdAdres = new Adres(7, "1111AA", "6", "GoudseSingelstraat", "Gouda", Henk);
        adao.update(gewijzigdAdres);
        System.out.println("Na wijziging: " + gewijzigdAdres + "\n");

        //Het zoeken op reiziger
        System.out.println("[Test] AdresDAO.findByReiziger; ");
        System.out.println(adao.findByReiziger(Henk));
        System.out.println();

        //Het zoeken van een adres op id
        System.out.println("[TEST] AdresDAo.findById();");
        System.out.println(adao.findById(5));
        System.out.println();

        //Het verwijderen van een adres
        System.out.println("[TEST] adressen: " + adressen.size() + "AdresDAO.delete();");
        adao.delete(gewijzigdAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + "\n");

    }

    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test OVCHIPKAARTDAO -------------");

        // Haal alle adressen op uit de database
        List<OVChipkaart> ovChipkaarts = odao.findAll();
        System.out.println();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende Kaarten:");
        for (OVChipkaart r : ovChipkaarts) {
            System.out.println(r );
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1998-09-09";
        Reiziger Henk = new Reiziger(78, "D", "", "Henk", java.sql.Date.valueOf(gbdatum));
        OVChipkaart ovChipkaart = new OVChipkaart(11234, Date.valueOf("2021-11-11"), 2, 75.00, Henk);

        //Het opslaan van een OVChipkaart
        System.out.print("[Test] Eerst " + ovChipkaarts.size() + " ovchip, na OVChipkaartDAO.save() ");
        System.out.println();
        odao.save(ovChipkaart);
        ovChipkaarts = odao.findAll();
        System.out.println("Na opslaan van OVChipkaart: " + ovChipkaarts.size() + " OVChipkaarten\n");

        //Het updaten van een OVChipkaart
        System.out.println("[Test] Eerst " + "OVChipkaart.update();");
        System.out.println("voor wijziging: " + ovChipkaart);
        ovChipkaart.setSaldo(65.00);
        odao.update(ovChipkaart);
        System.out.println("Na wijziging: " + ovChipkaart + "\n");

        //Het zoeken op reiziger
        System.out.println("[Test] OVChipkaartDAO.findByReiziger: ");
        System.out.println(odao.findByReiziger(Henk));
        System.out.println();

        //verwijderen van een adres
        //ovChipkaarts = odao.findAll();
        System.out.println("[TEST] Ovchipkaarten.delete Ovchipkaarten: " + ovChipkaarts.size());
        odao.delete(ovChipkaart);
        ovChipkaarts = odao.findAll();
        System.out.println("Na verwijderen OVChipkaart: " + ovChipkaarts.size() + "\n");

    }

    private static void testProductDAOPSql(ProductDAO pDAO, OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test PRODUCTDAO -------------");

        // Haal alle adressen op uit de database
        List<Product> products = pDAO.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende Kaarten:");
        for (Product p : products) {
            System.out.println(p );
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Product product1 = new Product(9, "Test8", "Alles van test8", 58.00);
        OVChipkaart ovChipkaart1 = new OVChipkaart(999999, Date.valueOf("2021-11-11"), 2, 75.00, rdao.findById(3));

        //Het opslaan van een Product en OVChipkaart
        System.out.print("[Test] Eerst " + products.size() + " product, na ProductDAO.save() ");
        pDAO.save(product1);
        odao.save(ovChipkaart1);
        product1.addOVKaart(ovChipkaart1);
        products = pDAO.findAll();
        System.out.println(products.size() + " producten\n");

        //Het updaten van een product
        System.out.println("[Test] Eerst " + "ProductDAO.update();");
        System.out.println("voor wijziging: " + product1);
        product1.setPrijs(100.00);
        pDAO.update(product1);
        System.out.println("Na wijziging: " + product1 + "\n");


        //Het verwijderen van een product
        System.out.println("[TEST] ProductDAOPsql " + products.size());
        pDAO.delete(product1);
        products = pDAO.findAll();
        System.out.println("Na verwijderen "+products.size() + "\n");



    }



}
