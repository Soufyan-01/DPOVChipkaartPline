package P2P3;

import java.util.ArrayList;

public class Product {

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    ArrayList<OVChipkaart> ovChipkaarts = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }



    public ArrayList<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(ArrayList<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    public void addOVKaart(OVChipkaart ovChipkaart){
        this.ovChipkaarts.add(ovChipkaart);
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString(){
        return "Product: " + product_nummer + " naam: " + naam + " beschrijving: " + beschrijving + " prijs: " + prijs;
    }


}

