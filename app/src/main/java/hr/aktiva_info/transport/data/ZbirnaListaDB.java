package hr.aktiva_info.transport.data;

import java.util.ArrayList;
import java.util.List;

import hr.aktiva_info.transport.data.ZbirnaLista;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ZbirnaListaDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "transport";

    // Contacts table name
    private static final String TABLE_ZBIRNA_LISTA = "zbirna_lista";

    // Contacts Table Columns names
    private static final String KEY_ID = "KEY_ID";
    private static final String FIELD_I_RBR = "rbr";
    private static final String FIELD_I_KOMITENT = "id_komitenta";
    private static final String FIELD_S_NAZIV = "naziv_komitenta";
    private static final String FIELD_S_ADRESA = "adresa_komitenta";
    private static final String FIELD_I_KOLETA = "koleta";
    private static final String FIELD_D_TEZINA = "tezina";

    public ZbirnaListaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ZBIRNA_LISTA + "("
                + KEY_ID + " INTEGER PRIMARY KEY"
                + ", "+ FIELD_I_RBR + " INTEGER"
                + ", "+ FIELD_I_KOMITENT + " INTEGER"
                + ", "+ FIELD_S_NAZIV + " TEXT"
                + ", "+ FIELD_S_ADRESA + " TEXT"
                + ", "+ FIELD_I_KOLETA + " INTEGER"
                + ", "+ FIELD_D_TEZINA + " DECIMAL(15,3)"
                + ")";
        Log.d("TRANSPORT: ", "Creating table");
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("TRANSPORT: ", "Table created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZBIRNA_LISTA);

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
        values.put(KEY_ID,zbirnalista.getId_zbirne_liste());
        values.put(FIELD_I_RBR,zbirnalista.getRbr());
        values.put(FIELD_I_KOMITENT,zbirnalista.getId_komitenta());
        values.put(FIELD_S_NAZIV, zbirnalista.getNaziv_komitenta());
        values.put(FIELD_S_ADRESA, zbirnalista.getAdresa_komitenta());
        values.put(FIELD_I_KOLETA, zbirnalista.getKoleta());
        values.put(FIELD_D_TEZINA, zbirnalista.getTezina());

        // Inserting Row
        db.insert(TABLE_ZBIRNA_LISTA, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    ZbirnaLista getZbirnaLista(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ZBIRNA_LISTA, new String[] { KEY_ID,
                        FIELD_I_RBR, FIELD_I_KOMITENT,FIELD_S_NAZIV,FIELD_S_ADRESA,FIELD_I_KOLETA,FIELD_D_TEZINA }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ZbirnaLista zbirnalista = new ZbirnaLista(
                Integer.parseInt(cursor.getString(0)),// id zbirne liste
                Integer.parseInt(cursor.getString(1)),//rbr
                Integer.parseInt(cursor.getString(2)),//id_komitenta
                cursor.getString(3),//naziv komitenta
                cursor.getString(4),//adresa
                Integer.parseInt(cursor.getString(5)),//koleta
                Double.valueOf(  cursor.getString(6)) //tezina
                );

        return zbirnalista;
    }

    // Getting All Contacts
    public List<ZbirnaLista> getZbirneListe() {
        List<ZbirnaLista> zbirneliste = new ArrayList<ZbirnaLista>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ZBIRNA_LISTA;

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_ZBIRNA_LISTA, new String[] { KEY_ID,
                        FIELD_I_RBR, FIELD_I_KOMITENT,FIELD_S_NAZIV,FIELD_S_ADRESA,FIELD_I_KOLETA,FIELD_D_TEZINA }, null,
                null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ZbirnaLista zbirnalista = new ZbirnaLista(
                (Integer.parseInt(cursor.getString(0))),
                (Integer.parseInt(cursor.getString(1))),
                (Integer.parseInt(cursor.getString(2))),
                (cursor.getString(3)),
                (cursor.getString(4)),
                (Integer.parseInt(cursor.getString(5))),
                (Double.valueOf(  cursor.getString(6))));
                // Adding contact to list
                zbirneliste.add(zbirnalista);
            } while (cursor.moveToNext());
        }

        // return contact list
        return zbirneliste;
    }


    public void InitTestData(){
        addContact( new ZbirnaLista(1,1,1,"Komitent 1","Adresa 1",5,25.56));
        addContact( new ZbirnaLista(2,2,1,"Komitent 2","Adresa 2",55,225.6));
        addContact( new ZbirnaLista(3,3,1,"Komitent 3","Adresa 3",65,255));

    }
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

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
*/

    // Getting contacts Count
    public int getListaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ZBIRNA_LISTA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}