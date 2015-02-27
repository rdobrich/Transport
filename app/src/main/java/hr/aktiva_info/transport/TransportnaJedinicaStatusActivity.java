

package hr.aktiva_info.transport;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by radovan on 20.2.2015..
 */
public class TransportnaJedinicaStatusActivity  extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_tj);

        Bundle b = getIntent().getBundleExtra(MainActivity.ZBIRNA_LISTA_BUNDLE);

    }



}
