package zhujiafanx.control;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2015/8/13.
 */
public class AlertDialogHelper {

    public static AlertDialog CreateProgressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        ProgressBar progressBar = new ProgressBar(context);
        progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(android.R.color.white), PorterDuff.Mode.MULTIPLY);
        progressBar.setBackground(new ColorDrawable((Color.BLACK)));

        builder.setView(progressBar);


        AlertDialog progressDialog = builder.create();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        //this.progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }
}
