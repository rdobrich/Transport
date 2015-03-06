package hr.aktiva_info.transport;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private View _view;

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
        _view = inflater.inflate(R.layout.zbirna_lista_detail_fragment, container, false);

        if (zbirna_lista != null) {

            //Display values and image
            TextView tvName = (TextView) _view.findViewById(R.id.tvNazivKomitenta);
            tvName.setText(zbirna_lista.getNaziv_komitenta());

            //TextView tvInstructions = (TextView) view.findViewById(R.id.tvInstructions);
            //tvInstructions.setText(flower.getInstructions());

            NumberFormat fmt = NumberFormat.getCurrencyInstance();
            TextView tv2 = (TextView) _view.findViewById(R.id.tvAdresaKomitenta);
            tv2.setText(zbirna_lista.getAdresa_komitenta());

            TextView tv3 = (TextView) _view.findViewById(R.id.tvTelefonKomitenta);
            tv3.setText(zbirna_lista.getTelefon_primatelja());


            TextView tv5= (TextView) _view.findViewById(R.id.tvNapomena);
            tv5.setText(zbirna_lista.getNapomena());


            TextView tv6 = (TextView) _view.findViewById(R.id.tvStatusnaLinija);
            tv6.setText(zbirna_lista.toOpisDetail()+"   " + zbirna_lista.toStatusRada());

           // ImageView ivPicture = (ImageView) view.findViewById(R.id.ivFlowerImage);
            //ivPicture.setImageResource(flower.getImageResource());


            _db.setTransportne_jedinice(_db.getTransportneJedinice(zbirna_lista.getBroj_prijevoznog_lista()));

            adapter = new TransportnaJedinicaArrayAdapter(getActivity(),
                    R.layout.transportna_jedinica_listitem,
                    _db.getTransportne_jedinice());


            final ListView listViewTJ = (ListView) _view.findViewById(R.id.lista_transportih_jedinica);


            listViewTJ.setOnItemClickListener(new   AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    TransportneJedinice tj=_db.getTransportne_jedinice().get(position);
                    Bundle b = tj.toBundle();
                    Intent intent=new Intent(getActivity(),TransportnaJedinicaStatusActivity.class);
                    intent.putExtras(b);

                    startActivityForResult(intent,1010);
                }
            });



            listViewTJ.setAdapter(adapter);

        }

        return _view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==1010 ) {
              //super.onActivityResult(requestCode, resultCode, data);
//            // Perform your task and get data from Intent (data parameter)
            _db.setTransportne_jedinice(_db.getTransportneJedinice(zbirna_lista.getBroj_prijevoznog_lista()));


            zbirna_lista=_db.getZbirnaLista(zbirna_lista.getBroj_prijevoznog_lista());
            TextView tv6 = (TextView) _view.findViewById(R.id.tvStatusnaLinija);
            tv6.setText(zbirna_lista.toOpisDetail()+"   " + zbirna_lista.toStatusRada());

            adapter.notifyDataSetChanged();
//
//              zbirne_liste.clear();
//            zbirne_liste = zbirnalistaDB.getZbirneListe();
//            adapter.notifyDataSetChanged();
//
//
       }
    }




}
