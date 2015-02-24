package hr.aktiva_info.transport;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

/**
 * Created by radovan on 20.2.2015..
 */
public class MyPrefsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("test", "On create MyPrefsActivity");
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new MyPrefsFragment())
                .commit();
    }
}
