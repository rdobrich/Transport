package hr.aktiva_info.transport;

import android.app.Activity;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;

import java.util.List;

/**
 * Created by David on 7/16/2014.
 */
public class ZbirnaListaListFragment extends ListFragment {


    //private List<ZbirnaLista> zbirne_liste ;
    private ZbirnaListaDB zbirnalistaDB;
    private Callbacks activity;
    private ZbirnaListaArrayAdapter adapter;
    String enumeratedList = "com.motorolasolutions.emdk.datawedge.api.ACTION_ENUMERATEDSCANNERLIST";

    String KEY_ENUMERATEDSCANNERLIST = "DataWedgeAPI_KEY_ENUMERATEDSCANNERLIST";

    private ListView zbirnalistaListView; // the ListActivity's ListView
    private CursorAdapter zbirnalistaAdapter; // adapter for ListView


    public ZbirnaListaListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zbirnalistaDB = new ZbirnaListaDB(getActivity());


        zbirnalistaDB.getZbirneListeFromDB();
        adapter = new ZbirnaListaArrayAdapter(getActivity(),
                R.layout.zbirna_lista_listitem,
                zbirnalistaDB.zbirne_liste);

        setListAdapter(adapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.zbirna_lista_list_fragment, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.zbirna_lista);
        if (zbirnalistaDB.zbirne_liste.size() > 0) {
            Integer broj = zbirnalistaDB.zbirne_liste.get(0).getId_zbirne_liste();
            tv.setText(broj.toString());
        }

        Button btnScan = (Button) rootView.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                do_scan_barcode(view);
            }
        });
        return rootView;
    }

    public interface Callbacks {
        public void onItemSelected(ZbirnaLista zbirna_lista);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ZbirnaLista zbirna_lista = zbirnalistaDB.zbirne_liste.get(position);
        activity.onItemSelected(zbirna_lista);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

    public void refreshAdapter() {
        zbirnalistaDB.zbirne_liste.clear();
        zbirnalistaDB.getZbirneListeFromDB();
        adapter.notifyDataSetChanged();

    }


    public void do_scan_barcode(View view) {

        Intent intent = new Intent();
        intent.setAction("com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER");
        intent.putExtra("com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER", "TOGGLE_SCANNING");
        getActivity().sendBroadcast(intent);

        getActivity().registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String code = intent.getExtras().getString("com.motorolasolutions.emdk.datawedge.data_string");

                // do something
                Toast.makeText(getActivity(),"Scan",Toast.LENGTH_SHORT).show();
                getActivity().unregisterReceiver(this);
            }
        }
                , new IntentFilter("android.intent.action.MAIN"));

    };
}
