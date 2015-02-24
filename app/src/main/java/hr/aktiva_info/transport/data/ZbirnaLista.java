package hr.aktiva_info.transport.data;

import android.os.Bundle;

/**
 * Created by radovan on 20.2.2015..
 */
public class ZbirnaLista {

    public static final String ZL_NAME = "zbirnaLista";
    public static final String ZL_RBR = "id";
    public static final String ZL_ID = "rbr";
    public static final String ZL_ID_KOMITENTA = "id_komitenta";
    public static final String ZL_NAZIV_KOMITENTA = "naziv_komitenta";
    public static final String ZL_ADRESA_KOMITENTA = "naziv_komitenta";
    public static final String ZL_KOLETA = "koleta";
    public static final String ZL_TEZINA = "tezina";

    //	private fields
    private int id_zbirne_liste;
    private int rbr;
    private int id_komitenta;
    private String naziv_komitenta;
    private String adresa_komitenta;
    private int koleta;
    private double tezina;

    public ZbirnaLista(int id_zbirne_liste, int rbr, int id_komitenta, String naziv_komitenta, String adresa_komitenta, int koleta, double tezina) {
        this.id_zbirne_liste = id_zbirne_liste;
        this.rbr = rbr;
        this.id_komitenta = id_komitenta;
        this.naziv_komitenta = naziv_komitenta;
        this.adresa_komitenta = adresa_komitenta;
        this.koleta = koleta;
        this.tezina = tezina;
    }

    public static String getZlName() {
        return ZL_NAME;
    }

    public int getId_zbirne_liste() {
        return id_zbirne_liste;
    }

    public void setId_zbirne_liste(int id_zbirne_liste) {
        this.id_zbirne_liste = id_zbirne_liste;
    }

    public int getRbr() {
        return rbr;
    }

    public void setRbr(int rbr) {
        this.rbr = rbr;
    }

    public int getId_komitenta() {
        return id_komitenta;
    }

    public void setId_komitenta(int id_komitenta) {
        this.id_komitenta = id_komitenta;
    }

    public String getNaziv_komitenta() {
        return naziv_komitenta;
    }

    public void setNaziv_komitenta(String naziv_komitenta) {
        this.naziv_komitenta = naziv_komitenta;
    }

    public String getAdresa_komitenta() {
        return adresa_komitenta;
    }

    public void setAdresa_komitenta(String adresa_komitenta) {
        this.adresa_komitenta = adresa_komitenta;
    }

    public int getKoleta() {
        return koleta;
    }

    public void setKoleta(int koleta) {
        this.koleta = koleta;
    }

    public double getTezina() {
        return tezina;
    }

    public void setTezina(double tezina) {
        this.tezina = tezina;
    }

    //	Create from a bundle
    public ZbirnaLista(Bundle b) {
        if (b != null) {

            this.id_zbirne_liste = b.getInt(ZL_ID);
            this.id_komitenta = b.getInt(ZL_ID_KOMITENTA);
            this.koleta = b.getInt(ZL_KOLETA);
            this.rbr = b.getInt(ZL_RBR);

            this.naziv_komitenta = b.getString(ZL_NAZIV_KOMITENTA);
            this.adresa_komitenta = b.getString(ZL_ADRESA_KOMITENTA);

            this.tezina = b.getDouble(ZL_TEZINA);
        }
    }

    //	Package data for transfer between activities
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putInt(ZL_ID, this.id_zbirne_liste);
        b.putInt(ZL_ID_KOMITENTA, this.id_komitenta);
        b.putInt(ZL_KOLETA, this.koleta);
        b.putInt(ZL_RBR, this.rbr);

        b.putString(ZL_NAZIV_KOMITENTA, this.naziv_komitenta);
        b.putString(ZL_ADRESA_KOMITENTA, this.adresa_komitenta);

        b.putDouble(ZL_TEZINA, this.tezina);

        return b;
    }

    //	Output flower data
    @Override
    public String toString() {
        return this.naziv_komitenta;
    }

    public String toStatus(){
        return    Integer.valueOf(this.koleta).toString() + " " + Double.valueOf(this.tezina).toString();
    }

}
