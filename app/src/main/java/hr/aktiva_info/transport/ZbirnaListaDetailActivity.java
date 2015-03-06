package hr.aktiva_info.transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import hr.aktiva_info.transport.data.Barcode;
import hr.aktiva_info.transport.data.TransportneJedinice;
import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;

// This activity is only used in single pane mode.
public class ZbirnaListaDetailActivity extends Activity
implements ZbirnaListaDetailFragment.Callbacks{

    private Barcode barcode=null;
    ZbirnaListaDB _db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
        barcode= new Barcode(getString(R.string.intentAction));
        if (savedInstanceState == null) {
//          Create the fragment, set its args, add it to the detail container
            ZbirnaListaDetailFragment fragment = new ZbirnaListaDetailFragment();

            // proslijedi parametar fragmentu
            Bundle b = getIntent().getBundleExtra(MainActivity.ZBIRNA_LISTA_BUNDLE);
            fragment.setArguments(b);

            getFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit();
        }

    }

//  Returns to the list activity
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
            finish();
        }
		return true;
	}


    @Override
    public void ScanBarcode(int tip_upita) {
        Intent i = new Intent();
        // set the intent action using soft scan trigger action string declared earlier
        i.setAction(barcode.ACTION_SOFTSCANTRIGGER);
        // add a string parameter to tell DW that we want to toggle the soft scan trigger
        i.putExtra(barcode.EXTRA_PARAM, barcode.DWAPI_TOGGLE_SCANNING);
        // now broadcast the intent
        ZbirnaListaDetailActivity.this.sendBroadcast(i);

    };
    //    // We need to handle any incoming intents, so let override the onNewIntent method

    @Override
    public void onNewIntent(Intent i) {
        //Toast.makeText(MainActivity.this, "Rezultat.", Toast.LENGTH_SHORT).show();
        if (i.getAction().contentEquals(barcode.ourIntentAction)) {
            barcode = barcode.handleDecodeData(i);


            // ako nisi prepoznao tj ili pl
            Toast.makeText(this, barcode.getData(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, barcode.getLabel_type(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getString(R.string.barcode_nije_prepoznat), Toast.LENGTH_SHORT).show();

        }
    }

    public void ReceiveBarcode(String barcode){
        Toast.makeText(this, "Primljen barcode", Toast.LENGTH_SHORT).show();
    }


}
