package hr.aktiva_info.transport.data;

import android.os.Bundle;

/**
 * Created by radovan on 26.2.2015..
 */
public class TransportneJedinice {

    public static final String TJ_NAME = "transportne_jedinice";
    public static final String TJ_KEY_ID = "transportna_jedinica_id";
    public static final String TJ_OZNAKA = "oznaka_transportne_jedinice";
    public static final String TJ_TEZINA = "tezina";
    public static final String TJ_BROJ_PL = "broj_prijevoznog_lista";
    public static final String TJ_TIP = "sifra_tipa_transportne_jedinice";
    public static final String TJ_OPIS = "opis_transportne_jedinice";
    public static final String TJ_UTOVARENO = "utovareno";
    public static final String TJ_ISPORUCENO = "isporuceno";
    public static final String TJ_OSTECENO = "osteceno";
    public static final String TJ_OSTECENO_STATUS = "osteceno_status";



    private String  oznaka_transportne_jedinice;
    private Double tezina;
    private Integer broj_prijevoznog_lista;
    private String sifra_tipa_transportne_jedinice;
    private Integer transportna_jedinica_id;
    private Integer utovareno;
    private Integer isporuceno;
    private Integer osteceno;
    private String osteceno_status;


    public TransportneJedinice(Integer transportna_jedinica_id,String oznaka_transportne_jedinice, Integer broj_prijevoznog_lista, Double tezina,  String sifra_tipa_transportne_jedinice, int utovareno,int isporuceno,int osteceno, String osteceno_status) {
        this.transportna_jedinica_id=transportna_jedinica_id;
        this.oznaka_transportne_jedinice = oznaka_transportne_jedinice;
        this.tezina = tezina;
        this.broj_prijevoznog_lista = broj_prijevoznog_lista;
        this.sifra_tipa_transportne_jedinice = sifra_tipa_transportne_jedinice;
        this.utovareno=utovareno;
        this.isporuceno=isporuceno;
        this.osteceno_status=osteceno_status;
        this.osteceno=osteceno;
    }


    //	Create from a bundle
    public TransportneJedinice(Bundle b) {
        if (b != null) {
            this.oznaka_transportne_jedinice=b.getString(TJ_OZNAKA);
            this.sifra_tipa_transportne_jedinice=b.getString(TJ_TIP);
            this.tezina=b.getDouble(TJ_TEZINA);
            this.broj_prijevoznog_lista=b.getInt((TJ_BROJ_PL));
            this.transportna_jedinica_id=b.getInt((TJ_KEY_ID));
            this.utovareno=b.getInt((TJ_UTOVARENO));
            this.isporuceno=b.getInt((TJ_ISPORUCENO));
            this.osteceno=b.getInt((TJ_OSTECENO));
            this.osteceno_status=b.getString(TJ_OSTECENO_STATUS);

        }
    }



    public TransportneJedinice() {
    }


    public Integer getUtovareno() {
        return utovareno;
    }

    public void setUtovareno(Integer utovareno) {
        this.utovareno = utovareno;
    }

    public Integer getIsporuceno() {
        return isporuceno;
    }

    public void setIsporuceno(Integer isporuceno) {
        this.isporuceno = isporuceno;
    }

    public Integer getOsteceno() {
        return osteceno;
    }

    public void setOsteceno(Integer osteceno) {
        this.osteceno = osteceno;
    }

    public String getOsteceno_status() {
        return osteceno_status;
    }

    public void setOsteceno_status(String osteceno_status) {
        this.osteceno_status = osteceno_status;
    }

    public String getOznaka_transportne_jedinice() {
        return oznaka_transportne_jedinice;
    }

    public void setOznaka_transportne_jedinice(String oznaka_transportne_jedinice) {
        this.oznaka_transportne_jedinice = oznaka_transportne_jedinice;
    }

    public Double getTezina() {
        return tezina;
    }

    public void setTezina(Double tezina) {
        this.tezina = tezina;
    }

    public Integer getBroj_prijevoznog_lista() {
        return broj_prijevoznog_lista;
    }

    public void setBroj_prijevoznog_lista(Integer broj_prijevoznog_lista) {
        this.broj_prijevoznog_lista = broj_prijevoznog_lista;
    }

    public String getSifra_tipa_transportne_jedinice() {
        return this.sifra_tipa_transportne_jedinice;
    }

    public void setSifra_tipa_transportne_jedinice(String sifra_tipa_transportne_jedinice) {
        this.sifra_tipa_transportne_jedinice = sifra_tipa_transportne_jedinice;
    }


    public Integer getTransportna_jedinica_id() {
        return transportna_jedinica_id;
    }

    public void setTransportna_jedinica_id(Integer transportna_jedinica_id) {
        this.transportna_jedinica_id = transportna_jedinica_id;
    }

    public String getOpisRada() {
       return this.getSifra_tipa_transportne_jedinice()+", Težina"+Double.valueOf(this.tezina).toString() ;
    }


    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(TJ_OZNAKA, this.oznaka_transportne_jedinice);
        b.putString(TJ_TIP, this.sifra_tipa_transportne_jedinice );
        b.putDouble(TJ_TEZINA, this.tezina);
        b.putInt(TJ_BROJ_PL, this.broj_prijevoznog_lista);
        b.putInt(TJ_KEY_ID, this.transportna_jedinica_id);
        b.putInt(TJ_UTOVARENO,this.utovareno);
        b.putInt(TJ_OSTECENO,this.osteceno);
        b.putInt(TJ_UTOVARENO,this.utovareno);
        b.putString(TJ_OSTECENO_STATUS, this.osteceno_status );
        return b;
    }



}
