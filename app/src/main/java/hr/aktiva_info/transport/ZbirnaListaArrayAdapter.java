package hr.aktiva_info.transport;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import hr.aktiva_info.transport.data.ZbirnaLista;

import java.util.List;

public class ZbirnaListaArrayAdapter extends ArrayAdapter<ZbirnaLista> {

	private Context context;
	private List<ZbirnaLista> objects;
	
	public ZbirnaListaArrayAdapter(Context context, int resource, List<ZbirnaLista> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ZbirnaLista zbirna_lista = objects.get(position);
		
		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.zbirna_lista_listitem, null);
		
		//ImageView image = (ImageView) view.findViewById(R.id.ivFlowerImage);
		//image.setImageResource(zbirna_lista.getImageResource());
		
		TextView tv = (TextView) view.findViewById(R.id.tvNazivKomitenta);
		tv.setText(zbirna_lista.getNaziv_komitenta());

        TextView tv2 = (TextView) view.findViewById(R.id.tvAdresaKomitenta);
        tv2.setText(zbirna_lista.getAdresa_komitenta());

        TextView tv3 = (TextView) view.findViewById(R.id.tvStatusnaLinija);
        tv3.setText(zbirna_lista.toStatus());

        TextView tv4 = (TextView) view.findViewById(R.id.status_pl);
        tv4.setText(zbirna_lista.toStatusRada());

        ImageView image = (ImageView) view.findViewById(R.id.list_image);
        if (zbirna_lista.getUtovareno()==0)
            image.setImageResource(R.drawable.lorry_flatbed);
        if (zbirna_lista.getUtovareno()>0 && zbirna_lista.getUtovareno()==zbirna_lista.getKoleta())
            image.setImageResource(R.drawable.lorry);
        if (zbirna_lista.getUtovareno()>0 && zbirna_lista.getIsporuceno()==0 && zbirna_lista.getUtovareno()<zbirna_lista.getKoleta())
            image.setImageResource(R.drawable.lorry_add);
        if (zbirna_lista.getIsporuceno()>0 && zbirna_lista.getIsporuceno()<zbirna_lista.getKoleta())
            image.setImageResource(R.drawable.lorry_go);
        if (zbirna_lista.getIsporuceno()>0 && zbirna_lista.getIsporuceno()==zbirna_lista.getKoleta())
            image.setImageResource(R.drawable.lorry_flatbed);
		return view;
	}

}
