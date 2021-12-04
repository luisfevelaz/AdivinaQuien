package mx.lfvl.proyecto_final;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

public class LoaderDialog {
    private Activity activity;
    private AlertDialog dialog;

    LoaderDialog(Activity activity){
        this.activity = activity;
        this.startLoader();
    }

    void startLoader(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.searching_dialog, null));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
    }

    void showLoader(){
        dialog.show();
    }

    void dismissLoader(){
        dialog.dismiss();
    }

    boolean loaderActive(){
        return dialog.isShowing();
    }
}
