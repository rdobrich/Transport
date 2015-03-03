package hr.aktiva_info.transport;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;

import java.util.List;

/**
 * Created by David on 7/16/2014.
 */
public class ZbirnaListaListFragment extends ListFragment {


    private List<ZbirnaLista> zbirne_liste =null;
    private ZbirnaListaDB zbirnalistaDB;
    private  Callbacks activity;
    private ZbirnaListaArrayAdapter adapter;

    private ListView zbirnalistaListView; // the ListActivity's ListView
    private CursorAdapter zbirnalistaAdapter; // adapter for ListView


    public ZbirnaListaListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zbirnalistaDB=new ZbirnaListaDB(getActivity());

        zbirne_liste = zbirnalistaDB.getZbirneListe();
        adapter = new ZbirnaListaArrayAdapter(getActivity(),
                R.layout.zbirna_lista_listitem,
                zbirne_liste);

        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.zbirna_lista_list_fragment, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.zbirna_lista);
        if (zbirne_liste.size()>0) {
            Integer broj = zbirne_liste.get(0).getId_zbirne_liste();
            tv.setText(broj.toString());
        }
        return rootView;
    }

    public interface Callbacks {
        public void onItemSelected (ZbirnaLista zbirna_lista);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ZbirnaLista zbirna_lista=zbirne_liste.get(position);
        activity.onItemSelected(zbirna_lista);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity= (Callbacks) activity;
    }

    public void refreshAdapter() {
        zbirne_liste.clear();
        zbirne_liste = zbirnalistaDB.getZbirneListe();
        adapter.notifyDataSetChanged();
    }


}
