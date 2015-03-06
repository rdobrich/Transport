package hr.aktiva_info.transport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hr.aktiva_info.transport.data.TransportneJedinice;

/**
 * Created by radovan on 27.2.2015..
 */
public class TransportnaJedinicaArrayAdapter extends ArrayAdapter {

    private Context context;
    private List<TransportneJedinice> objects;

    private LayoutInflater mLayoutInflater = null;

    public TransportnaJedinicaArrayAdapter(Context context, int resource, List<TransportneJedinice> objects) {
        super(context,resource,objects);
        this.context = context;
        this.objects = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TransportneJedinice tj = objects.get(position);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.transportna_jedinica_listitem, null);


        //ImageView image = (ImageView) view.findViewById(R.id.ivFlowerImage);
        //image.setImageResource(zbirna_lista.getImageResource());

        TextView tv = (TextView) view.findViewById(R.id.tvTJOznaka);
        tv.setText(tj.getOznaka_transportne_jedinice());

        TextView tv2 = (TextView) view.findViewById(R.id.tvTJPrikaz);
        tv2.setText(tj.getOpisRada());

        TextView tv3 = (TextView) view.findViewById(R.id.tvTJStatus);
        tv3.setText("");
        if (tj.getUtovareno()==1)
            tv3.setText(this.context.getString(R.string.status_tj_utovareno));
        if (tj.getIsporuceno()==1)
            tv3.setText(this.context.getString(R.string.status_tj_isporuceno));
        if (tj.getOsteceno()==1)
            tv3.setText(this.context.getString(R.string.status_tj_osteceno));



        return view;
    }

    @Override
    public int getCount() {
        return objects.size();
    }
    @Override
    public Object getItem(int pos) {
        return objects.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}

