package hr.aktiva_info.transport.data;

import java.util.ArrayList;
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
    private static final int DATABASE_VERSION = 8;

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

    private TransportneJedinice _tj;

    private List<TransportneJedinice> transportne_jedinice ;

    public List<TransportneJedinice> getTransportne_jedinice() {
        return transportne_jedinice;
    }

    public void setTransportne_jedinice(List<TransportneJedinice> transportne_jedinice) {
        this.transportne_jedinice = transportne_jedinice;
    }

    public ZbirnaListaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _tj=new TransportneJedinice();

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ZBIRNA_LISTA + "("
                + KEY_ID + " INTEGER PRIMARY KEY"
                + ", "+ FIELD_I_BROJ_PL + " INTEGER"
                + ", "+ FIELD_I_BROJ_PL_ZBIRNI + " INTEGER"
                + ", "+ FIELD_I_KOMITENT + " INTEGER"
                + ", "+ FIELD_S_NAZIV + " TEXT"
                + ", "+ FIELD_S_ADRESA + " TEXT"
                + ", "+ FIELD_S_TELEFON + " TEXT"
                + ", "+ FIELD_S_NAPOMENA + " TEXT"
                + ", "+ FIELD_I_KOLETA + " INTEGER"
                + ", "+ FIELD_D_TEZINA + " DECIMAL(15,3)"
                + ")";
        Log.d("TRANSPORT: ", "Creating table");
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_TJ_TABLE = "CREATE TABLE " + _tj.TJ_NAME + "("
                + _tj.TJ_KEY_ID + " INTEGER PRIMARY KEY"
                + ", "+ _tj.TJ_OZNAKA + " TEXT"
                + ", "+ _tj.TJ_TIP + " TEXT"
                + ", "+ _tj.TJ_BROJ_PL + " INTEGER"
                + ", "+ _tj.TJ_TEZINA + " DECIMAL(15,3)"
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
    void addContact(ZbirnaLista zbirnalista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,zbirnalista.getBroj_prijevoznog_lista());
        values.put(FIELD_I_BROJ_PL,zbirnalista.getBroj_prijevoznog_lista());
        values.put(FIELD_I_BROJ_PL_ZBIRNI,zbirnalista.getId_zbirne_liste());
        values.put(FIELD_I_KOMITENT,zbirnalista.getId_komitenta());
        values.put(FIELD_S_NAZIV, zbirnalista.getNaziv_komitenta());
        values.put(FIELD_S_TELEFON, zbirnalista.getTelefon_primatelja());
        values.put(FIELD_S_NAPOMENA, zbirnalista.getNapomena());
        values.put(FIELD_S_ADRESA, zbirnalista.getAdresa_komitenta());
        values.put(FIELD_I_KOLETA, zbirnalista.getKoleta());
        values.put(FIELD_D_TEZINA, zbirnalista.getTezina());

        // Inserting Row
        db.insert(TABLE_ZBIRNA_LISTA, null, values);
        db.close(); // Closing database connection
    }


    // Adding new zbirna lista
    void addTransportnaJedinica(TransportneJedinice tj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_tj.TJ_KEY_ID,tj.getTransportna_jedinica_id());
        values.put(_tj.TJ_OZNAKA,tj.getOznaka_transportne_jedinice());
        values.put(_tj.TJ_BROJ_PL,tj.getBroj_prijevoznog_lista());
        values.put(_tj.TJ_TEZINA,tj.getTezina());
        values.put(_tj.TJ_TIP,tj.getSifra_tipa_transportne_jedinice());

        // Inserting Row
        db.insert(_tj.TJ_NAME, null, values);
        db.close(); // Closing database connection
    }


    // Getting single contact
    ZbirnaLista getZbirnaLista(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ZBIRNA_LISTA, new String[] { KEY_ID,
                        FIELD_I_BROJ_PL, FIELD_I_KOMITENT,FIELD_S_NAZIV,FIELD_S_ADRESA,FIELD_S_TELEFON,FIELD_S_NAPOMENA,FIELD_I_KOLETA,FIELD_D_TEZINA }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
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
                Double.valueOf(  cursor.getString(8)) //tezina
                );

        return zbirnalista;
    }


    public List<TransportneJedinice> getTransportneJedinice(int zbirna_lista_id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(_tj.TJ_NAME, new String[] {_tj.TJ_KEY_ID,_tj.TJ_OZNAKA, _tj.TJ_TIP,_tj.TJ_TEZINA }, _tj.TJ_BROJ_PL + "=?",
                new String[] { String.valueOf(zbirna_lista_id) }, null, null, null, null);

        List<TransportneJedinice> transportne_jedinice = new ArrayList<TransportneJedinice>();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TransportneJedinice tj = new TransportneJedinice(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        zbirna_lista_id,
                        Double.valueOf(cursor.getString(3)),
                        cursor.getString(2)

                );
                // Adding contact to list
                transportne_jedinice.add(tj);
            } while (cursor.moveToNext());
        }

        // return contact list
        return transportne_jedinice;


    }


    // Getting All Contacts
    public List<ZbirnaLista> getZbirneListe() {
        List<ZbirnaLista> zbirneliste = new ArrayList<ZbirnaLista>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ZBIRNA_LISTA;

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_ZBIRNA_LISTA, new String[] { KEY_ID,
                        FIELD_I_BROJ_PL, FIELD_I_KOMITENT,FIELD_S_NAZIV,FIELD_S_ADRESA,FIELD_S_TELEFON,FIELD_S_NAPOMENA,FIELD_I_KOLETA,FIELD_D_TEZINA}, null,
                null, null, null, null, null);

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
                        Double.valueOf(  cursor.getString(8)) //tezina
                );
                // Adding contact to list
                zbirneliste.add(zbirnalista);
            } while (cursor.moveToNext());
        }

        // return contact list
        return zbirneliste;
    }




//    public void InitTestData(){
//        addContact( new ZbirnaLista(1,1,1,"Komitent 1","Adresa 1","telefon1","napomena1",5,25.56));
//        addContact( new ZbirnaLista(2,2,1,"Komitent 2","Adresa 2","telefon2","napomena2",55,225.6));
//        addContact( new ZbirnaLista(3,3,1,"Komitent 3","Adresa 3","telefon3","napomena3",65,255));
//
//    }
/*
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_NAZIV, contact.getName());
        values.put(KEY_ADRESA, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_ZBIRNA_LISTA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
*/
    // Deleting single contact
    public void deleteAllListu( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ZBIRNA_LISTA,null,null);
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


    public void UcitajPodatkeIzSOAP(String result ) throws JSONException {
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonInnerObjectPodaci = jsonResponse.getJSONObject("podaci");
            JSONObject jsonInnerObjectLista = jsonInnerObjectPodaci.getJSONObject("lista");
            JSONObject jsonInnerObjectListaTransportnihJedinica = jsonInnerObjectPodaci.getJSONObject("lista_tj");
            //JSONArray json_pl = jsonInnerObjectLista.optJSONArray("pl");
            deleteAllListu();
            try {
                JSONArray json_pl = jsonInnerObjectLista.getJSONArray("pl");
                for(int i = 0; i<json_pl.length();i++) {
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
                                Double.parseDouble(jsonChildNode.optString("@" + FIELD_D_TEZINA))
                        );

                        addContact(zbirnalista);
                    }
                }
            }
                catch (JSONException e) {
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
                            Double.parseDouble(jsonInnerObjectPL.getString("@" + FIELD_D_TEZINA))
                    );

                    addContact(zbirnalista);

            }

            TransportneJedinice tj_d = new TransportneJedinice();

            // dodavanje transportnih jedinica
            try {
                JSONArray json_pl = jsonInnerObjectListaTransportnihJedinica.getJSONArray("tj");
                for(int i = 0; i<json_pl.length();i++) {
                    JSONObject jsonChildNode = json_pl.getJSONObject(i);
                    String key_id = jsonChildNode.optString("@"+tj_d.TJ_OZNAKA);

                    if (key_id != null & key_id != "") {
                        TransportneJedinice tj = new TransportneJedinice(
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_KEY_ID)),
                                jsonChildNode.optString("@" + tj_d.TJ_OZNAKA),
                                Integer.parseInt(jsonChildNode.optString("@" + tj_d.TJ_BROJ_PL)),
                                Double.parseDouble(jsonChildNode.optString("@" + tj_d.TJ_TEZINA)),
                                jsonChildNode.optString("@" + tj_d.TJ_TIP)

                        );
                        addTransportnaJedinica(tj);
                    }
                }
            }
            catch (JSONException e) {
                // nije array
                JSONObject jsonInnerObjectPL = jsonInnerObjectListaTransportnihJedinica.getJSONObject("tj");
                TransportneJedinice tj = new TransportneJedinice(
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_KEY_ID)),
                        jsonInnerObjectPL.getString("@" + tj_d.TJ_OZNAKA),
                        Integer.parseInt(jsonInnerObjectPL.getString("@" + tj_d.TJ_BROJ_PL)),
                        Double.parseDouble(jsonInnerObjectPL.getString("@" + tj_d.TJ_TEZINA)),
                        jsonInnerObjectPL.getString("@" + tj_d.TJ_TIP)
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


}