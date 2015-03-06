package hr.aktiva_info.transport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import hr.aktiva_info.transport.data.Barcode;
import hr.aktiva_info.transport.data.SOAPExec;
import hr.aktiva_info.transport.data.TransportneJedinice;
import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;

/*
 * To receive data via intents from DataWedge, the DataWedge intent plug-in will need to be configured.
 * The following steps will help you get started...
 * 1. Launch DataWedge
 * 2. Create a new profile and give it a name such as "dwdemosample"
 * 3. Edit the profile
 * 4. Go into Associated apps, tap the menu button, and add a new app/activity
 * 5. For the application select com.motorolasolutions.emdk.sample.dwdemosample
 * 6. For the activity select com.motorolasolutions.emdk.sample.dwdemosample.MainActivty
 * 7. Go back and disable the keystroke output plug-in (KRIVO!!!!. mora biti enable)
 * 8. Enable the intent output plug-in
 * 9. For the intent action enter com.motorolasolutions.emdk.sample.dwdemosample.RECVR
 * 10. For the intent category enter android.intent.category.DEFAULT
 *
 * Now when you run this activity and scan a barcode you should see the barcode data
 * preceded with additional info (source, symbology and length); see handleDecodeData below.
 */

public class MainActivity extends FragmentActivity
implements ZbirnaListaListFragment.Callbacks
{


    private boolean isTwoPane = false      ;

    private Barcode barcode=null;
    public static final String ZBIRNA_LISTA_BUNDLE = "ZBIRNA_LISTA_BUNDLE";
    public static final String TJ_BUNDLE = "TJ_BUNDLE";
    private static final int REQUEST_CODE = 1001;
    private  ZbirnaListaListFragment fragment;

    ZbirnaListaDB _db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcode= new Barcode(getString(R.string.intentAction));
        fragment= new ZbirnaListaListFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.listContainer, fragment)
                .commit();


        if (findViewById(R.id.detailContainer)!= null ){
            isTwoPane=true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1002){
            SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
            boolean mypref = pref.getBoolean("pref1",false);
            //Toast.makeText(this,"Preference "+mypref,Toast.LENGTH_SHORT).show();
            MyDialogFragment dialog= new MyDialogFragment();

            Bundle b = new Bundle();
            b.putString("message","Ovo je poruka");
            dialog.setArguments(b);

            dialog.show(getFragmentManager(),"MyDialog");

        }

        if (requestCode==REQUEST_CODE ) {
            //super.onActivityResult(requestCode, resultCode, data);
//            // Perform your task and get data from Intent (data parameter)

            fragment.refreshAdapter();
           // Toast.makeText (this,"Osvježi listu",Toast.LENGTH_SHORT).show();

//
//
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

           /* ScreenUtility utility = new ScreenUtility(this);
//            String output = "Width: " + utility.getWidth() + ", " +
//                    "Height: " + utility.getHeight();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(output)
//                    .setTitle("Dimensions")
//                    .create()
//                    .show();
*/


            Intent intent=new Intent();
            intent.setClass(this,MyPrefsActivity.class);
            startActivityForResult(intent,1002);

            return true;
        }
        if (id == R.id.action_seop) {


                SOAPExec task = new SOAPExec("mobile.get_zbirni_prijevozni_list 25 ", "zbirna_lista", this);
                //passes values for the urls string array
                task.execute();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void do_soap_postback(){
        if (fragment != null) {
            fragment.refreshAdapter();
        }

    }



    @Override
    public void onItemSelected(ZbirnaLista zbirnaLista) {
        Bundle b = zbirnaLista.toBundle();

        if (isTwoPane ) {
            ZbirnaListaDetailFragment fragment= new ZbirnaListaDetailFragment();
            fragment.setArguments(b);
            getFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer,fragment)
                    .commit();
        }
        else {
            // start new activity
            Intent intent = new Intent(this, ZbirnaListaDetailActivity.class);
            // send bundle to activity
            intent.putExtra(ZBIRNA_LISTA_BUNDLE, b);
            // start with result code
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void ScanBarcode(int tip_upita) {
        Intent i = new Intent();
        // set the intent action using soft scan trigger action string declared earlier
        i.setAction(barcode.ACTION_SOFTSCANTRIGGER);
        // add a string parameter to tell DW that we want to toggle the soft scan trigger
        i.putExtra(barcode.EXTRA_PARAM, barcode.DWAPI_TOGGLE_SCANNING);
        // now broadcast the intent
        MainActivity.this.sendBroadcast(i);

    };
    //    // We need to handle any incoming intents, so let override the onNewIntent method

    @Override
    public void onNewIntent(Intent i) {
        //Toast.makeText(MainActivity.this, "Rezultat.", Toast.LENGTH_SHORT).show();
        barcode=barcode.handleDecodeData(i);
        _db = new ZbirnaListaDB(MainActivity.this);
        TransportneJedinice tj=_db.getTransportnaJedinica(barcode.getData());
        if (tj == null){
            Toast.makeText(this,barcode.getData(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,barcode.getLabel_type(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,getString(R.string.barcode_nije_prepoznat),Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle b = tj.toBundle();
            Intent intent = new Intent(MainActivity.this, TransportnaJedinicaStatusActivity.class);
            intent.putExtras(b);

            startActivityForResult(intent, 1030);
        }
    }



}
