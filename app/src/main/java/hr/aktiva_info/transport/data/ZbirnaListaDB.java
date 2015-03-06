package hr.aktiva_info.transport.data;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZbirnaListaDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 18;

    // Database Name
    private static final String DATABASE_NAME = "transport";

    // Contacts table name
    private static final String TABLE_ZBIRNA_LISTA = "zbirna_lista";

    // Contacts Table Columns names
    public static final String KEY_ID = "KEY_ID";
    public static final String FIELD_I_BROJ_PL = "broj_prijevoznog_lista";
    public static final String FIELD_I_BROJ_PL_ZBIRNI = "id_pl_zbirni";
    public static final String FIELD_I_KOMITENT = "id_komitenta";
    public static final String FIELD_S_NAZIV = "naziv_primatelja";
    public static final String FIELD_S_ADRESA = "adresa_primatelja";
    public static final String FIELD_S_TELEFON = "telefon_primatelja";
    public static final String FIELD_S_NAPOMENA = "napomena";
    public static final String FIELD_I_KOLETA = "koleta";
    public static final String FIELD_D_TEZINA = "tezina";
    public static final String FIELD_SI_UTOVARENO = "utovareno";
    public static final String FIELD_SI_ISPORUCENO = "isporuceno";
    public static final String FIELD_SI_OSTECENO = "osteceno";

    public TransportneJedinice _tj;

    private List<TransportneJedinice> transportne_jedinice = null;
    public List<ZbirnaLista> zbirne_liste;
    private Context _context;

    public List<TransportneJedinice> getTransportne_jedinice() {
        return transportne_jedinice;
    }

    public void setTransportne_jedinice(List<TransportneJedinice> transportne_jedinice) {
        this.transportne_jedinice = transportne_jedinice;
    }

    public ZbirnaListaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
        _tj = new TransportneJedinice();
    }

    public List<ZbirnaLista> getZbirne_liste() {
        return zbirne_liste;
    }

    public void setZbirne_liste(List<ZbirnaLista> zbirne_liste) {
        this.zbirne_liste = zbirne_liste;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ZBIRNA_LISTA + "("
                + KEY_ID + " INTEGER PRIMARY KEY"
                + ", " + FIELD_I_BROJ_PL + " INTEGER"
                + ", " + FIELD_I_BROJ_PL_ZBIRNI + " INTEGER"
                + ", " + FIELD_I_KOMITENT + " INTEGER"
                + ", " + FIELD_S_NAZIV + " TEXT"
                + ", " + FIELD_S_ADRESA + " TEXT"
                + ", " + FIELD_S_TELEFON + " TEXT"
                + ", " + FIELD_S_NAPOMENA + " TEXT"
                + ", " + FIELD_I_KOLETA + " INTEGER"
                + ", " + FIELD_D_TEZINA + " DECIMAL(15,3)"
                + ", " + FIELD_SI_UTOVARENO + " INTEGER"
                + ", " + FIELD_SI_ISPORUCENO + " INTEGER"
                + ", " + FIELD_SI_OSTECENO + " INTEGER"
                + ")";
        Log.d("TRANSPORT: ", "Creating table");
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_TJ_TABLE = "CREATE TABLE " + _tj.TJ_NAME + "("
                + _tj.TJ_KEY_ID + " INTEGER PRIMARY KEY"
                + ", " + _tj.TJ_OZNAKA + " TEXT"
                + ", " + _tj.TJ_TIP + " TEXT"
                + ", " + _tj.TJ_BROJ_PL + " INTEGER"
                + ", " + _tj.TJ_TEZINA + " DECIMAL(15,3)"
                + ", " + _tj.TJ_UTOVARENO + " INTEGER"
                + ", " + _tj.TJ_UTOVARENO+"_DATE" + " TEXT"
                + ", " + _tj.TJ_ISPORUCENO + " INTEGER"
                + ", " + _tj.TJ_ISPORUCENO +"_DATE" + " TEXT"
                + ", " + _tj.TJ_OSTECENO + " INTEGER"
                + ", " + _tj.TJ_OSTECENO +"_DATE" + " TEXT"
                + ", " + _tj.TJ_OSTECENO_STATUS + " TEXT"
                + ", " + _tj.TJ_ZAPIS_ZA_ISPORUKU + " INTEGER"
                + ")";
        db.execSQL(CREATE_TJ_TABLE);


        Log.d("TRANSPORT: ", "Table created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZBIRNA_LISTA);
        db.execSQL("DROP TABLE IF EXISTS " + _tj.TJ_NAME);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new zbirna lista
    void addZbirnaLista(ZbirnaLista zbirnalista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        db.beginTransaction();
        values.put(KEY_ID, zbirnalista.getBroj_prijevoznog_lista());
        values.put(FIELD_I_BROJ_PL, zbirnalista.getBroj_prijevoznog_lista());
        values.put(FIELD_I_BROJ_PL_ZBIRNI, zbirnalista.getId_zbirne_liste());
        values.put(FIELD_I_KOMITENT, zbirnalista.getId_komitenta());
        values.put(FIELD_S_NAZIV, zbirnalista.getNaziv_komitenta());
        values.put(FIELD_S_TELEFON, zbirnalista.getTelefon_primatelja());
        values.put(FIELD_S_NAPOMENA, zbirnalista.getNapomena());
        values.put(FIELD_S_ADRESA, zbirnalista.getAdresa_komitenta());
        values.put(FIELD_I_KOLETA, zbirnalista.getKoleta());
        values.put(FIELD_D_TEZINA, zbirnalista.getTezina());
        values.put(FIELD_SI_UTOVARENO, zbirnalista.getUtovareno());
        values.put(FIELD_SI_ISPORUCENO, zbirnalista.getIsporuceno());
        values.put(FIELD_SI_OSTECENO, zbirnalista.getOsteceno());

        // Inserting Row
        db.insert(TABLE_ZBIRNA_LISTA, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    // Adding new zbirna lista
    void addTransportnaJedinica(TransportneJedinice tj) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(_tj.TJ_KEY_ID, tj.getTransportna_jedinica_id());
        values.put(_tj.TJ_OZNAKA, tj.getOznaka_transportne_jedinice());
        values.put(_tj.TJ_BROJ_PL, tj.getBroj_prijevoznog_lista());
        values.put(_tj.TJ_TEZINA, tj.getTezina());
        values.put(_tj.TJ_TIP, tj.getSifra_tipa_transportne_jedinice());
        values.put(_tj.TJ_UTOVARENO, tj.getUtovareno());
        values.put(_tj.TJ_ISPORUCENO, tj.getIsporuceno());
        values.put(_tj.TJ_OSTECENO, tj.getOsteceno());
        values.put(_tj.TJ_OSTECENO_STATUS, tj.getOsteceno_status());
        // Inserting Row
        db.insert(_tj.TJ_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    // Getting single contact
    ZbirnaLista getZbirnaLista(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ZBIRNA_LISTA, new String[]{KEY_ID,
                        FIELD_I_BROJ_PL, FIELD_I_KOMITENT, FIELD_S_NAZIV, FIELD_S_ADRESA, FIELD_S_TELEFON, FIELD_S_NAPOMENA, FIELD_I_KOLETA, FIELD_D_TEZINA, FIELD_SI_UTOVARENO, FIELD_SI_UTOVARENO, FIELD_SI_OSTECENO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ZbirnaLista zbirnalista = new ZbirnaLista(
                Integer.parseInt(cursor.getString(0)),// id zbirne liste
                Integer.parseInt(cursor.getString(1)),//rbr
                Integer.parseInt(cursor.getString(2)),//id_komitenta
                cursor.getString(3),//naziv komitenta
                cursor.getString(4),//adresa
                cursor.getString(5),//telefon
                cursor.getString(6),//napomena
                Integer.parseInt(cursor.getString(7)),//koleta
                Double.valueOf(cursor.getString(8)), //tezina
                Integer.parseInt(cursor.getString(9)), //utovareno
                Integer.parseInt(cursor.getString(10)),//isporuceno
                Integer.parseInt(cursor.getString(11)) //osteceno
        );

        return zbirnalista;
    }


    public List<TransportneJedinice> getTransportneJedinice(int zbirna_lista_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(_tj.TJ_NAME, new String[]{_tj.TJ_KEY_ID, _tj.TJ_OZNAKA, _tj.TJ_TIP, _tj.TJ_TEZINA, _tj.TJ_UTOVARENO, _tj.TJ_ISPORUCENO, _tj.TJ_OSTECENO, _tj.TJ_OSTECENO_STATUS}, _tj.TJ_BROJ_PL + "=?",
                new String[]{String.valueOf(zbirna_lista_id)}, null, null, null, null);

        if (transportne_jedinice == null) {
            transportne_jedinice = new ArrayList<TransportneJedinice>();
        }
        transportne_jedinice.clear();


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TransportneJedinice tj = new TransportneJedinice(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        zbirna_lista_id,
                        Double.valueOf(cursor.getString(3)),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)),
                        cursor.getString(7)
                );
                // Adding contact to list
                transportne_jedinice.add(tj);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return transportne_jedinice;


    }


    // Getting All Contacts
    public void getZbirneListeFromDB() {

        // Select All Query

        SQLiteDatabase db = this.getReadableDatabase();
        if (zbirne_liste == null) {
            zbirne_liste = new ArrayList<ZbirnaLista>();
        }
        zbirne_liste.clear();
        String query = "select id_pl_zbirni,\n" +
                "a.broj_prijevoznog_lista,id_komitenta,naziv_primatelja,adresa_primatelja,telefon_primatelja,napomena,koleta,a.tezina,\n" +
                "ifnull(sum(tj.utovareno),0) as utovareno, \n" +
                "ifnull(sum(tj.isporuceno),0) as isporuceno, \n" +
                "ifnull(sum(tj.osteceno),0) as osteceno\n" +
                "from zbirna_lista a inner join transportne_jedinice tj on a.broj_prijevoznog_lista=tj.broj_prijevoznog_lista\n" +
                "group by key_id,\n" +
                "a.broj_prijevoznog_lista,id_komitenta,naziv_primatelja,adresa_primatelja,telefon_primatelja,napomena,koleta,a.tezina";
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ZbirnaLista zbirnalista = new ZbirnaLista(
                        Integer.parseInt(cursor.getString(0)),// id zbirne liste
                        Integer.parseInt(cursor.getString(1)),//rbr
                        Integer.parseInt(cursor.getString(2)),//id_komitenta
                        cursor.getString(3),//naziv komitenta
                        cursor.getString(4),//adresa
                        cursor.getString(5),//telefon
                        cursor.getString(6),//napomena
                        Integer.parseInt(cursor.getString(7)),//koleta
                        Double.valueOf(cursor.getString(8)), //tezina
                        Integer.parseInt(cursor.getString(9)),//utovareno
                        Integer.parseInt(cursor.getString(10)),//isporuceno
                        Integer.parseInt(cursor.getString(11))//osteceno
                );
                // Adding contact to list
                zbirne_liste.add(zbirnalista);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return ;
    }


    public ZbirnaLista getZbirnaLista(Integer broj_prijevoznog_lista) {
        // Select All Query
        ZbirnaLista zbirnalista = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select id_pl_zbirni,\n" +
                "a.broj_prijevoznog_lista,id_komitenta,naziv_primatelja,adresa_primatelja,telefon_primatelja,napomena,koleta,a.tezina,\n" +
                "ifnull(sum(tj.utovareno),0) as utovareno, \n" +
                "ifnull(sum(tj.isporuceno),0) as isporuceno, \n" +
                "ifnull(sum(tj.osteceno),0) as osteceno\n" +
                "from zbirna_lista a inner join transportne_jedinice tj on a.broj_prijevoznog_lista=tj.broj_prijevoznog_lista\n" +
                " where a.broj_prijevoznog_lista = " + Integer.valueOf(broj_prijevoznog_lista).toString() + " \n" +
                "group by id_pl_zbirni,\n" +
                "a.broj_prijevoznog_lista,id_komitenta,naziv_primatelja,adresa_primatelja,telefon_primatelja,napomena,koleta,a.tezina";
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            {
                zbirnalista = new ZbirnaLista(
                        Integer.parseInt(cursor.getString(0)),// id zbirne liste
                        Integer.parseInt(cursor.getString(1)),//broj_prijevoznog_lista
                        Integer.parseInt(cursor.getString(2)),//id_komitenta
                        cursor.getString(3),//naziv komitenta
                        cursor.getString(4),//adresa
                        cursor.getString(5),//telefon
                        cursor.getString(6),//napomena
                        Integer.parseInt(cursor.getString(7)),//koleta
                        Double.valueOf(cursor.getString(8)), //tezina
                        Integer.parseInt(cursor.getString(9)),//utovareno
                        Integer.parseInt(cursor.getString(10)),//isporuceno
                        Integer.parseInt(cursor.getString(11))//osteceno
                );

            }
        }

        // return contact list
        db.close();
        return zbirnalista;

    }

    // Deleting single contact
    public void deleteAllListu() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ZBIRNA_LISTA, null, null);
        db.delete(_tj.TJ_NAME, null, null);
        db.close();
    }


    // Getting contacts Count
    public int getListaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ZBIRNA_LISTA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    // Getting contacts Count
    public int broj_zapisa_za_slanje() {
        String countQuery = "SELECT  * FROM " + _tj.TJ_NAME + " where " + _tj.TJ_ZAPIS_ZA_ISPORUKU + " =1 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        Integer no=cursor.getCount();
        cursor.close();
        return no;
    }

    public void UcitajPodatkeIzSOAP(String result) throws JSONException {

        Log.d("TRANSPORT", "UcitajPodatkeIzSOAP");
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("TRANSPORT", "load podaci");
            deleteAllListu();
            JSONObject jsonInnerObjectPodaci = jsonResponse.getJSONObject("podaci");
            JSONObject jsonInnerObjectLista = jsonInnerObjectPodaci.getJSONObject("lista");
            JSONObject jsonInnerObjectListaTransportnihJedinica = jsonInnerObjectPodaci.getJSONObject("lista_tj");
            //JSONArray json_pl = jsonInnerObjectLista.optJSONArray("pl");
            deleteAllListu();
            try {
                JSONArray json_pl = jsonInnerObjectLista.getJSONArray("pl");
                for (int i = 0; i < json_pl.length(); i++) {
                    JSONObject jsonChildNode = json_pl.getJSONObject(i);
                    String key_id = jsonChildNode.optString("@key_id");
                    int id = Integer.parseInt(key_id);
                    if (id > 0) {
                        ZbirnaLista zbirnalista = new ZbirnaLista(
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_I_BROJ_PL_ZBIRNI)),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_I_BROJ_PL)),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_I_KOMITENT)),
                                jsonChildNode.optString("@" + FIELD_S_NAZIV),
                                jsonChildNode.optString("@" + FIELD_S_ADRESA),
                                jsonChildNode.optString("@" + FIELD_S_TELEFON),
                                jsonChildNode.optString("@" + FIELD_S_NAPOMENA),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_I_KOLETA)),
                                Double.parseDouble(jsonChildNode.optString("@" + FIELD_D_TEZINA)),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_SI_UTOVARENO)),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_SI_ISPORUCENO)),
                                Integer.parseInt(jsonChildNode.optString("@" + FIELD_SI_OSTECENO))
                        );

                        addZbirnaLista(zbirnalista);
                    }
                }
            } catch (JSONException e) {
                // nije array
                JSONObject jsonInnerObjectPL = jsonInnerObjectLista.getJSONObject("pl");
                ZbirnaLista zbirnalista = new ZbirnaLista(
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_I_BROJ_PL_ZBIRNI)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_I_BROJ_PL)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_I_KOMITENT)),
                        jsonInnerObjectPL.getString("@" + FIELD_S_NAZIV),
                        jsonInnerObjectPL.getString("@" + FIELD_S_ADRESA),
                        jsonInnerObjectPL.getString("@" + FIELD_S_TELEFON),
                        jsonInnerObjectPL.getString("@" + FIELD_S_NAPOMENA),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_I_KOLETA)),
                        Double.parseDouble(jsonInnerObjectPL.getString("@" + FIELD_D_TEZINA)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_SI_UTOVARENO)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_SI_ISPORUCENO)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + FIELD_SI_OSTECENO))
                );

                addZbirnaLista(zbirnalista);

            }

            TransportneJedinice tj_d = new TransportneJedinice();

            // dodavanje transportnih jedinica
            Log.d("TRANSPORT", "tj");
            try {
                JSONArray json_pl = jsonInnerObjectListaTransportnihJedinica.getJSONArray("tj");
                for (int i = 0; i < json_pl.length(); i++) {
                    JSONObject jsonChildNode = json_pl.getJSONObject(i);
                    String key_id = jsonChildNode.optString("@" + tj_d.TJ_OZNAKA);

                    if (key_id != null && key_id != "") {
                        TransportneJedinice tj = new TransportneJedinice(
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_KEY_ID)),
                                jsonChildNode.optString("@" + tj_d.TJ_OZNAKA),
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_BROJ_PL)),
                                Double.parseDouble(jsonChildNode.optString("@" + tj_d.TJ_TEZINA)),
                                jsonChildNode.optString("@" + tj_d.TJ_TIP),
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_UTOVARENO)),
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_ISPORUCENO)),
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_OSTECENO)),
                                jsonChildNode.optString("@" + tj_d.TJ_OSTECENO_STATUS)
                        );
                        addTransportnaJedinica(tj);
                    }
                }
            } catch (JSONException e) {
                // nije array
                JSONObject jsonInnerObjectPL = jsonInnerObjectListaTransportnihJedinica.getJSONObject("tj");
                TransportneJedinice tj = new TransportneJedinice(
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_KEY_ID)),
                        jsonInnerObjectPL.getString("@" + tj_d.TJ_OZNAKA),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_BROJ_PL)),
                        Double.parseDouble(jsonInnerObjectPL.getString("@" + tj_d.TJ_TEZINA)),
                        jsonInnerObjectPL.getString("@" + tj_d.TJ_TIP),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_UTOVARENO)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_ISPORUCENO)),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_OSTECENO)),
                        jsonInnerObjectPL.getString("@" + tj_d.TJ_OSTECENO_STATUS)

                );

                addTransportnaJedinica(tj);

            }

            // kraj dodahanja TJ

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //izvla�enje statusa iz primljenog JSONa
        //JSONObject jsonInnerObject = jsonInnerObjectPodaci.getJSONObject("lista_tj");
        //JSONArray jsonMainNode = jsonInnerObject.optJSONArray("status");
    }


    // Updating single contact
    public int TJ_SnimiStatus(TransportneJedinice _tj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_tj.TJ_UTOVARENO, _tj.getUtovareno());
        values.put(_tj.TJ_ISPORUCENO, _tj.getIsporuceno());
        values.put(_tj.TJ_OSTECENO, _tj.getOsteceno());
        values.put(_tj.TJ_OSTECENO_STATUS, _tj.getOsteceno_status());
        values.put(_tj.TJ_ZAPIS_ZA_ISPORUKU, 1);
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (_tj.getUtovareno()==1)
            values.put(_tj.TJ_UTOVARENO+"_DATE", currentDateandTime);
        else
            values.put(_tj.TJ_UTOVARENO + "_DATE", "");

        if (_tj.getIsporuceno()==1)
            values.put(_tj.TJ_ISPORUCENO+"_DATE", currentDateandTime);
        else
            values.put(_tj.TJ_ISPORUCENO+"_DATE", "");

        if (_tj.getOsteceno()==1)
            values.put(_tj.TJ_OSTECENO+"_DATE", currentDateandTime);
        else
            values.put(_tj.TJ_OSTECENO+"_DATE", "");

        // updating row
        db.beginTransaction();
        db.update(_tj.TJ_NAME, values, _tj.TJ_KEY_ID + " = ?",
                new String[]{String.valueOf(_tj.getTransportna_jedinica_id())});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        posaljiZapiseNaServer();

        return 1;
    }


    /**
     * Posalji karte na server
     */
    public void posaljiZapiseNaServer() {
        if (broj_zapisa_za_slanje() > 0) {
            String XML = generateStatusiXML();
            String executeString = "mobile.snimi_tj_status " + XML;

            SOAPExec task = new SOAPExec(executeString, "tj_statusi", _context);
            //passes values for the urls string array
            task.execute();


        }

    }

    private String generateStatusiXML() {


        String countQuery = "SELECT  "+_tj.TJ_KEY_ID+","+_tj.TJ_UTOVARENO+","+_tj.TJ_ISPORUCENO+","+_tj.TJ_OSTECENO+","+_tj.TJ_OSTECENO_STATUS+ ",\n"+
                _tj.TJ_UTOVARENO+"_DATE"+","+_tj.TJ_ISPORUCENO+"_DATE"+","+_tj.TJ_OSTECENO+"_DATE"+ "\n"+
                "  FROM " + _tj.TJ_NAME + " where " + _tj.TJ_ZAPIS_ZA_ISPORUKU + " =1 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String red="'<statusi>";
        String status;
        if (cursor.moveToFirst()) {
            do {
                status="0";
                if  (Integer.parseInt(cursor.getString(1))==1)
                    status ="1";
                if  (Integer.parseInt(cursor.getString(2))==1)
                    status ="2";
                if  (Integer.parseInt(cursor.getString(3))==1)
                    status ="3";
                red=red+"<status tj_id=\"" + cursor.getString(0) + "\" status=\"" + status + "\" ";

                if (cursor.getString(4).length()>0)
                    red=red + " osteceno_status=\""+  cursor.getString(4)+"\" ";

                if (cursor.getString(5).length()>0)
                    red=red + " d_uto=\""+cursor.getString(5)+"\"";

                if (cursor.getString(6).length()>0)
                    red=red + " d_isp=\""+cursor.getString(6)+"\"";

                if (cursor.getString(7).length()>0)
                    red=red + " d_ost=\""+cursor.getString(7)+"\"";

                red =red +" />\n";

            } while (cursor.moveToNext());
        }
        db.close();
        return red+"</statusi>'";

    }


    public void resetPoslatihStatusa (String  rezultat) throws SQLException {
        ArrayList<Integer> tj_liste = new ArrayList<Integer>();

        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(rezultat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("TRANSPORT", "obrada povratnih statusa ");
            Integer tj_id;
            JSONObject jsonInnerObjectPodaci = jsonResponse.getJSONObject("statusi");
            //JSONArray json_pl = jsonInnerObjectLista.optJSONArray("pl");
            try {
                JSONArray json_pl = jsonInnerObjectPodaci.getJSONArray("tj");
                for (int i = 0; i < json_pl.length(); i++) {
                    JSONObject jsonChildNode = json_pl.getJSONObject(i);
                    tj_id=Integer.parseInt(jsonChildNode.getString("@tj_id"));
                    tj_liste.add(tj_id);
                }
            } catch (JSONException e) {
                // nije array
                JSONObject jsonInnerObjectPL = jsonInnerObjectPodaci.getJSONObject("tj");
                tj_id=Integer.parseInt(jsonInnerObjectPL.getString("@tj_id"));
                tj_liste.add(tj_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

      // upiši sve liste

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < tj_liste.size(); i++) {
            Integer tj_id;
            tj_id = tj_liste.get(i);

            ContentValues values = new ContentValues();
            values.put(_tj.TJ_ZAPIS_ZA_ISPORUKU, 0);

            // updating row
            db.beginTransaction();
            db.update(_tj.TJ_NAME, values, _tj.TJ_KEY_ID + " = ?",  new String[]{String.valueOf(tj_id)});
            db.setTransactionSuccessful();
            db.endTransaction();

        }
        db.close();

    }

}