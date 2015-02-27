package hr.aktiva_info.transport.data;

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



    private String  oznaka_transportne_jedinice;
    private Double tezina;
    private Integer broj_prijevoznog_lista;
    private String Sifra_tipa_transportne_jedinice;
    private Integer transportna_jedinica_id;

    public TransportneJedinice(Integer transportna_jedinica_id,String oznaka_transportne_jedinice, Integer broj_prijevoznog_lista, Double tezina,  String sifra_tipa_transportne_jedinice) {
        this.transportna_jedinica_id=transportna_jedinica_id;
        this.oznaka_transportne_jedinice = oznaka_transportne_jedinice;
        this.tezina = tezina;
        this.broj_prijevoznog_lista = broj_prijevoznog_lista;
        this.Sifra_tipa_transportne_jedinice = sifra_tipa_transportne_jedinice;
    }

    public TransportneJedinice() {
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
        return this.Sifra_tipa_transportne_jedinice;
    }

    public void setSifra_tipa_transportne_jedinice(String sifra_tipa_transportne_jedinice) {
        this.Sifra_tipa_transportne_jedinice = sifra_tipa_transportne_jedinice;
    }


    public Integer getTransportna_jedinica_id() {
        return transportna_jedinica_id;
    }

    public void setTransportna_jedinica_id(Integer transportna_jedinica_id) {
        this.transportna_jedinica_id = transportna_jedinica_id;
    }
}
