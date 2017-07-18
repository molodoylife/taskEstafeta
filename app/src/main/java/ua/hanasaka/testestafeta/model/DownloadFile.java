package ua.hanasaka.testestafeta.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import it.sephiroth.android.library.exif2.ExifInterface;
import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Created by Oleksandr Molodykh on 18.07.2017.
 */

public class DownloadFile extends AsyncTask<List<Image>, Integer, Long> {
    String TAG = "log";
    Context ctx;
    ProgressDialog mProgressDialog;
    Location location;
    DataBaseSaver dataBaseSaver;

    public DownloadFile(Context ctx, Location loc) {
        this.ctx = ctx;
        this.location = loc;
        mProgressDialog = new ProgressDialog(ctx);
        dataBaseSaver = new DataBaseSaver(loc);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(10);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    @Override
    protected Long doInBackground(List<Image>... listImg) {
        int count;
        List<Image> list = listImg[0];
        try {
            Log.d(TAG, "listsize="+list.size());
            for (int i=0;i<list.size();i++) {
                Log.d(TAG, "i="+i);
                Image image = list.get(i);
                dataBaseSaver.saveToDb(image);
                String targetFileName = image.getTitle()+".JPG";
                URL url = new URL(image.getDisplaySizes().get(0).getUri());
                Log.d(TAG, "url=" + url.toString());
                URLConnection conexion = url.openConnection();
                conexion.connect();
                Log.d(TAG, "fileName=" + targetFileName);
                int lenghtOfFile = conexion.getContentLength();

                File folder = new File(Environment.getExternalStorageDirectory() + "/Estafeta");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(new File(folder, targetFileName));
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                String filePath = new File(folder, targetFileName).getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                ExifInterface exif = new ExifInterface();
                try {
                    exif.readExif(filePath, ExifInterface.Options.OPTION_ALL);
                } catch (IOException e) {
                    Log.d(TAG, e.getClass() + "-----" + e.getMessage());
                }
                exif.addGpsTags(location.getLatitude(), location.getLongitude());
                try {
                    exif.writeExif(filePath);
                    //output.flush();
                    output.close();
                    input.close();
                    publishProgress(i);
                } catch (IOException e) {
                    Log.d(TAG, e.getClass() + "++++++++" + e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.getClass() + " mess=" + e.getMessage());
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        mProgressDialog.setProgress(progress[0]);
        if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
            mProgressDialog.dismiss();
            Toast.makeText(ctx, "File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
}