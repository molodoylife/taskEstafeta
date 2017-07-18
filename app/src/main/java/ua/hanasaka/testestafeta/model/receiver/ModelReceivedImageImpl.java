package ua.hanasaka.testestafeta.model.receiver;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;
import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.api.ApiInterface;
import ua.hanasaka.testestafeta.model.api.ApiModule;
import ua.hanasaka.testestafeta.model.data.Img;
import ua.hanasaka.testestafeta.presenter.Presenter;

public class ModelReceivedImageImpl extends AsyncTask<String, Void, Boolean> implements ModelReceivedImage{
    Context context;
    private ProgressDialog pd;
    private String error;
    private Presenter presenter;
    private Img img;

    public ModelReceivedImageImpl(Context context, Presenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context, R.style.MyTheme);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Response<Img> resp = getImgList(params[0]).execute();
            img = resp.body();
            if (img != null) {
                return true;
            }
            else{
                error = context.getResources().getString(R.string.nullResp);
                return false;
            }
        } catch (UnknownHostException e) {
            error = context.getResources().getString(R.string.serverNotResponding);
            return false;
        } catch (Exception e){
            error = e.getClass()+" mess "+e.getMessage();
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
                presenter.onGetError(error);
            }
        }
    }

    /**
     * @param searchWord
     * @return Call object
     * Getting Img object that contains list of Images
     */
    public Call<Img> getImgList(String searchWord) {
        ApiInterface apiInterface = ApiModule.getApiInterface();
        return apiInterface.getImg(searchWord);
    }

    /**
     * @param searchWord
     * Starts work in asynk mode
     */
    @Override
    public void getImages(String searchWord){
        this.execute(searchWord);
    }
}
