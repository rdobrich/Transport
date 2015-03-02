package hr.aktiva_info.transport.data;

import android.os.Bundle;

/**
 * Created by radovan on 20.2.2015..
 */
public class ZbirnaLista {

    public static final String ZL_NAME = "zbirnaLista";
    public static final String ZL_PL_ZBIRNI = "id_pl_zbirni";
    public static final String ZL_BROJ_PL = "broj_prijevoznog_lista";
    public static final String ZL_ID_KOMITENTA = "id_komitenta";
    public static final String ZL_NAZIV_KOMITENTA = "naziv_primatelja";
    public static final String ZL_ADRESA_KOMITENTA = "adresa_primatelja";
    public static final String ZL_TELEFON_KOMITENTA = "telefon_primatelja";
    public static final String ZL_KOLETA = "koleta";
    public static final String ZL_TEZINA = "tezina";
    public static final String ZL_NAPOMENA = "napomena";
    public static final String ZL_UTOVARENO = "utovareno";
    public static final String ZL_ISPORUCENO ="isporuceno";
    public static final String ZL_OSTECENO = "osteceno";



  //	private fields
    private int id_pl_zbirni;
    private int broj_prijevoznog_lista;
    private int id_komitenta;
    private String naziv_komitenta;
    private String adresa_komitenta;
    private String telefon_primatelja;
    private String napomena;
    private int koleta;
    private double tezina;
    private int utovareno;
    private int isporuceno;
    private int osteceno;

    public String getTelefon_primatelja() {
        return telefon_primatelja;
    }

    public void setTelefon_primatelja(String telefon_primatelja) {
        this.telefon_primatelja = telefon_primatelja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public ZbirnaLista(int id_pl_zbirni, int broj_prijevoznog_lista, int id_komitenta, String naziv_komitenta, String adresa_komitenta,String telefon_primatelja,String napomena, int koleta, double tezina,int utovareno,int isporuceno,int osteceno) {
        this.id_pl_zbirni = id_pl_zbirni;
        this.broj_prijevoznog_lista = broj_prijevoznog_lista;
        this.id_komitenta = id_komitenta;
        this.naziv_komitenta = naziv_komitenta;
        this.adresa_komitenta = adresa_komitenta;
        this.telefon_primatelja=telefon_primatelja;
        this.napomena=napomena;
        this.koleta = koleta;
        this.tezina = tezina;
        this.utovareno=utovareno;
        this.isporuceno=isporuceno;
        this.osteceno=osteceno;

    }

    public int getUtovareno() {
        return utovareno;
    }

    public void setUtovareno(int utovareno) {
        this.utovareno = utovareno;
    }

    public int getIsporuceno() {
        return isporuceno;
    }

    public void setIsporuceno(int isporuceno) {
        this.isporuceno = isporuceno;
    }

    public int getOsteceno() {
        return osteceno;
    }

    public void setOsteceno(int osteceno) {
        this.osteceno = osteceno;
    }

    public static String getZlName() {
        return ZL_NAME;
    }

    public int getId_zbirne_liste() {
        return id_pl_zbirni;
    }

    public void setId_zbirne_liste(int id_zbirne_liste) {
        this.id_pl_zbirni = id_zbirne_liste;
    }

    public int getBroj_prijevoznog_lista() {
        return broj_prijevoznog_lista;
    }

    public void setBroj_prijevoznog_lista(int broj_prijevoznog_lista) {
        this.broj_prijevoznog_lista = broj_prijevoznog_lista;
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

            this.id_pl_zbirni = b.getInt(ZL_PL_ZBIRNI);
            this.id_komitenta = b.getInt(ZL_ID_KOMITENTA);
            this.koleta = b.getInt(ZL_KOLETA);
            this.broj_prijevoznog_lista = b.getInt(ZL_BROJ_PL);

            this.naziv_komitenta = b.getString(ZL_NAZIV_KOMITENTA);
            this.adresa_komitenta = b.getString(ZL_ADRESA_KOMITENTA);
            this.telefon_primatelja = b.getString(ZL_TELEFON_KOMITENTA);
            this.napomena = b.getString(ZL_NAPOMENA);

            this.tezina = b.getDouble(ZL_TEZINA);
            this.utovareno=b.getInt(ZL_UTOVARENO);
            this.isporuceno=b.getInt(ZL_ISPORUCENO);
            this.osteceno=b.getInt(ZL_OSTECENO);

        }
    }

    //	Package data for transfer between activities
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putInt(ZL_PL_ZBIRNI, this.id_pl_zbirni);
        b.putInt(ZL_ID_KOMITENTA, this.id_komitenta);
        b.putInt(ZL_KOLETA, this.koleta);
        b.putInt(ZL_BROJ_PL, this.broj_prijevoznog_lista);
        b.putInt(ZL_UTOVARENO, this.utovareno);
        b.putInt(ZL_ISPORUCENO, this.isporuceno);
        b.putInt(ZL_OSTECENO, this.osteceno);

        b.putString(ZL_NAZIV_KOMITENTA, this.naziv_komitenta);
        b.putString(ZL_ADRESA_KOMITENTA, this.adresa_komitenta);
        b.putString(ZL_NAPOMENA, this.napomena);
        b.putString(ZL_TELEFON_KOMITENTA, this.telefon_primatelja);

        b.putDouble(ZL_TEZINA, this.tezina);

        return b;
    }

    //	Output flower data
    @Override
    public String toString() {
        return this.naziv_komitenta;
    }

    public String toStatus(){
         return   "Koleta: "+ Integer.valueOf(this.koleta).toString() +", Težina: "+ Double.valueOf(this.tezina).toString() ;
    }

    public String toOpisLista(){
        return   "Koleta: "+ Integer.valueOf(this.koleta).toString() +", Težina: "+ Double.valueOf(this.tezina).toString() ;

    }

    public String toOpisDetail(){
        return   "Koleta: "+ Integer.valueOf(this.koleta).toString() +", Težina: "+ Double.valueOf(this.tezina).toString() ;
    }

    public String toStatusRada(){
        return    Integer.valueOf(this.utovareno).toString() +"/"+ Integer.valueOf(this.isporuceno).toString();
    }
}
