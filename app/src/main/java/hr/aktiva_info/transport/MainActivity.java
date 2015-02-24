package hr.aktiva_info.transport;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import hr.aktiva_info.transport.data.ZbirnaLista;


public class MainActivity extends Activity
implements ZbirnaListaListFragment.Callbacks
{

    private boolean isTwoPane = false      ;


    public static final String ZBIRNA_LISTA_BUNDLE = "ZBIRNA_LISTA_BUNDLE";
    private static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        return super.onOptionsItemSelected(item);
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
}
