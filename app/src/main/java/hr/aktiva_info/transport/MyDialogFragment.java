package hr.aktiva_info.transport;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by radovan on 20.2.2015..
 */
public class MyDialogFragment extends DialogFragment {
    public MyDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle b = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Result")
                .setMessage(b.getString("message"))
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();

                    }
                });

        return builder.create();
    }
}
