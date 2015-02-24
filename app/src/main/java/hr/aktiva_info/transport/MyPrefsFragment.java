package hr.aktiva_info.transport;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by radovan on 20.2.2015..
 */
public class MyPrefsFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

}
