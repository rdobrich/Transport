package hr.aktiva_info.transport;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import hr.aktiva_info.transport.data.TransportneJedinice;
import hr.aktiva_info.transport.data.ZbirnaLista;
import hr.aktiva_info.transport.data.ZbirnaListaDB;

import java.text.NumberFormat;
import java.util.List;

public class ZbirnaListaDetailFragment extends Fragment {

    ZbirnaLista zbirna_lista;
    ZbirnaListaDB _db;
    private List<TransportneJedinice> transportne_jedinice ;
    private TransportnaJedinicaArrayAdapter adapter;


//    Required no-args constructor
    public ZbirnaListaDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _db = new ZbirnaListaDB(getActivity());

        Bundle b = getArguments();
        if (b != null && b.containsKey(ZbirnaLista.ZL_BROJ_PL)) {
            zbirna_lista=new ZbirnaLista(b);
        }


      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Load the layout
        View view = inflater.inflate(R.layout.zbirna_lista_detail_fragment, container, false);

        if (zbirna_lista != null) {

            //Display values and image
            TextView tvName = (TextView) view.findViewById(R.id.tvNazivKomitenta);
            tvName.setText(zbirna_lista.getNaziv_komitenta());

            //TextView tvInstructions = (TextView) view.findViewById(R.id.tvInstructions);
            //tvInstructions.setText(flower.getInstructions());

            NumberFormat fmt = NumberFormat.getCurrencyInstance();
            TextView tv2 = (TextView) view.findViewById(R.id.tvAdresaKomitenta);
            tv2.setText(zbirna_lista.getAdresa_komitenta());

            TextView tv3 = (TextView) view.findViewById(R.id.tvTelefonKomitenta);
            tv3.setText(zbirna_lista.getTelefon_primatelja());


            TextView tv5= (TextView) view.findViewById(R.id.tvNapomena);
            tv5.setText(zbirna_lista.getNapomena());


            TextView tv6 = (TextView) view.findViewById(R.id.tvStatusnaLinija);
            tv6.setText(zbirna_lista.toOpisDetail());

           // ImageView ivPicture = (ImageView) view.findViewById(R.id.ivFlowerImage);
            //ivPicture.setImageResource(flower.getImageResource());


            _db.setTransportne_jedinice(_db.getTransportneJedinice(zbirna_lista.getBroj_prijevoznog_lista()));

            adapter = new TransportnaJedinicaArrayAdapter(getActivity(),
                    R.layout.transportna_jedinica_listitem,
                    _db.getTransportne_jedinice());


            ListView listViewTJ = (ListView) view.findViewById(R.id.lista_transportih_jedinica);

            listViewTJ.setAdapter(adapter);

        }

        return view;
    }

}
