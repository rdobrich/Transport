package hr.aktiva_info.transport;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

// This activity is only used in single pane mode.
public class ZbirnaListaDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flower_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

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

}
