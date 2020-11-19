package P2P3;




import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int ID;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private java.sql.Date geboortededatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarts = new ArrayList<>();


    public Reiziger(int ID, String voorletters, String tussenvoegsel,
                    String achternaam, java.sql.Date geboortededatum) {
        this.ID = ID;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortededatum = geboortededatum;
    }

    public Reiziger(int ID, String voorletters, String tussenvoegsel,
                    String achternaam, java.sql.Date geboortededatum, Adres adres){
        this.ID = ID;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortededatum = geboortededatum;
        this.adres = adres;

    }

    public List<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(List<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    public Adres getAdres(){
        return adres;
    }

    public  int getID(){
       return ID;
   }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaam(){
       return voorletters + achternaam;
    }


    // vanaf hier begin ik met de statement update
    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public java.sql.Date getGeboortedatum() {
        return geboortededatum;
    }

    public void setVoorletters(String voorletters) {
      this.voorletters = voorletters;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(java.sql.Date geboortededatum){
        this.geboortededatum = geboortededatum;

    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public Date getGeboortededatum() {
        return geboortededatum;
    }

    public void setGeboortededatum(Date geboortededatum) {
        this.geboortededatum = geboortededatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String toString(){
        String resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                + " tussenvoegsels: " + tussenvoegsel + " achternaam: " + achternaam
                + " geboortedatum: " + geboortededatum);
        if(tussenvoegsel == null){
            resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                    + " achternaam: " + achternaam
                    + " geboortedatum: " + geboortededatum);
        }
        if (adres == null) {
            resultaat += " adres niet gevonden ";
        }else{
            resultaat+= " het adres is: " + adres.toString();
        }
        return resultaat;
    }


    public String ownString(){
        String resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                + " tussenvoegsels: " + tussenvoegsel + " achternaam: " + achternaam
                + " geboortedatum: " + geboortededatum);
        return resultaat;
    }



}
