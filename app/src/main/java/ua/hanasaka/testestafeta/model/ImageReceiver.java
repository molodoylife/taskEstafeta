package ua.hanasaka.testestafeta.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.api.ApiInterface;
import ua.hanasaka.testestafeta.model.api.ApiModule;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.data.Img;
import ua.hanasaka.testestafeta.presenter.Presenter;
import ua.hanasaka.testestafeta.view.View;

public class ImageReceiver extends AsyncTask<String, Void, Boolean> {
    String TAG = "log";
    private ProgressDialog pd;
    private String error;
    private View view;
    private Presenter presenter;
    private Img img;

    public ImageReceiver(View view, Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }


    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(view.getContext(), R.style.MyTheme);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            ApiInterface apiInterface = ApiModule.getApiInterface();
            Response<Img> resp = apiInterface.getImg(params[0]).execute();
            Log.d(TAG, "onResp");
            img = resp.body();
            if (img != null) {
                return true;
            }
            else{
                error = view.getContext().getResources().getString(R.string.nullResp);
                return false;
            }
        } catch (Exception e) {
            error = e.getClass() + " " + e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean flag) {
        super.onPostExecute(flag);
        if (flag) {
            pd.dismiss();
            presenter.onReceiveData(img);
        } else {
            pd.dismiss();
            if (error != null) {
                showAlert(error);
            }
        }
    }

    private void showAlert(String mess) {
        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setMessage(mess)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }

}
