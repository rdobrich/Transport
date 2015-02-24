package hr.aktiva_info.transport;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hr.aktiva_info.transport.data.ZbirnaLista;

import java.text.NumberFormat;

public class ZbirnaListaDetailFragment extends Fragment {

    ZbirnaLista zbirna_lista;

//    Required no-args constructor
    public ZbirnaListaDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null && b.containsKey(ZbirnaLista.ZL_ID)) {
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

            TextView tv3 = (TextView) view.findViewById(R.id.tvStatusnaLinija);
            tv3.setText(zbirna_lista.toStatus());


           // ImageView ivPicture = (ImageView) view.findViewById(R.id.ivFlowerImage);
            //ivPicture.setImageResource(flower.getImageResource());

        }

        return view;
    }

}
