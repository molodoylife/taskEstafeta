package ua.hanasaka.testestafeta.model.downloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import it.sephiroth.android.library.exif2.ExifInterface;
import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.dataBase.ModelDataBaseImpl;
import ua.hanasaka.testestafeta.presenter.Presenter;

/**
 * Implementing ModelDownloader
 */
public class DownloadFile extends AsyncTask<List<Image>, Void, Void> implements ModelDownloader {
    Context ctx;
    Location location;

    ModelDataBaseImpl modelDataBaseImpl;
    Presenter presenter;

    public DownloadFile(Context ctx, Location loc, Presenter p) {
        this.ctx = ctx;
        this.location = loc;
        this.presenter = p;
        modelDataBaseImpl = new ModelDataBaseImpl(loc, presenter);
    }

    /**
     * @param listImg
     * @return
     *
     * Downloading list of images from urls
     * and setting them EXIF (GPS coordinates of user phone)
     */
    @Override
    protected Void doInBackground(List<Image>... listImg) {
        int count;
        List<Image> list = listImg[0];
        try {
            for (int i = 0; i < list.size(); i++) {

                //Getting Image from list
                Image image = list.get(i);

                //Saving data in db
                modelDataBaseImpl.saveToDb(image);

                //Make URLConnection
                String targetFileName = image.getTitle() + ".JPG";
                URL url = new URL(image.getDisplaySizes().get(0).getUri());
                URLConnection conection = url.openConnection();
                conection.connect();

                File folder = new File(Environment.getExternalStorageDirectory() + "/Estafeta");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                //Read data in InputStream
                InputStream input = new BufferedInputStream(url.openStream());

                //Write data in OutputStream
                OutputStream output = new FileOutputStream(new File(folder, targetFileName));
                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                //Set EXIF (GPS coordinates)
                String filePath = new File(folder, targetFileName).getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                ExifInterface exif = new ExifInterface();
                exif.readExif(filePath, ExifInterface.Options.OPTION_ALL);
                exif.addGpsTags(location.getLatitude(), location.getLongitude());
                exif.writeExif(filePath);

                //close streams
                output.flush();
                output.close();
                input.close();
            }
        } catch (Exception e) {
            presenter.onGetError(e.getClass() + " mess=" + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        presenter.onGetError(ctx.getResources().getString(R.string.imagesDownloaded));
    }

    /**
     * @param list
     * Start downloading list of files
     */
    @Override
    public void downloadFiles(List<Image> list) {
        this.execute(list);
    }
}