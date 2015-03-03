

package hr.aktiva_info.transport;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import hr.aktiva_info.transport.data.TransportneJedinice;
import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;


/**
 * Created by radovan on 20.2.2015..
 */
public class TransportnaJedinicaStatusActivity  extends Activity {
    TransportneJedinice _tj;
    ZbirnaListaDB _db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _db = new ZbirnaListaDB(this);
        setContentView(R.layout.activity_status_tj);
        Bundle b = getIntent().getExtras();
        //Bundle b = getIntent().getBundleExtra(MainActivity.TJ_BUNDLE);
        if (b != null && b.containsKey(_db._tj.TJ_KEY_ID)) {
            _tj=new TransportneJedinice(b);

        }

        TextView tv1 =(TextView) findViewById(R.id.txtTJOznaka);
        tv1.setText(_tj.getOznaka_transportne_jedinice());

        TextView tv2 =(TextView) findViewById(R.id.txtTJOpis);
        tv2.setText(_tj.getOpisRada());



    }

        public void on_click_utovar (View view){
            _tj.setUtovareno(1);
            _tj.setIsporuceno(0);
            _tj.setOsteceno(0);

            _db.TJ_SnimiStatus(_tj);
            finish();
        }


    public void on_click_istovar (View view){
        _tj.setUtovareno(1);
        _tj.setIsporuceno(1);
        _tj.setOsteceno(0);

        _db.TJ_SnimiStatus(_tj);
        finish();
    }


    public void on_click_osteceno (View view){
        _tj.setUtovareno(1);
        _tj.setIsporuceno(0);
        _tj.setOsteceno(1);

        _db.TJ_SnimiStatus(_tj);
        finish();
    }

}
