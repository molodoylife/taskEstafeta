package ua.hanasaka.testestafeta.presenter;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.downloader.DownloadFile;
import ua.hanasaka.testestafeta.model.downloader.ModelDownloader;
import ua.hanasaka.testestafeta.model.receiver.ModelReceivedImage;
import ua.hanasaka.testestafeta.model.receiver.ModelReceivedImageImpl;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.data.Img;
import ua.hanasaka.testestafeta.utils.InternetChecker;
import ua.hanasaka.testestafeta.utils.LocationChecker;
import ua.hanasaka.testestafeta.utils.LogTagsHolder;
import ua.hanasaka.testestafeta.view.View;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Presenter for managing interaction between components
 */
public class ImageListPresenter implements Presenter, LocationListener {

    View view;

    Location myLocation = null;
    LocationManager locationManager;

    List<Image> imgList;

    Context context;
    boolean isDownloaded = false;

    public ImageListPresenter(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    /**
     * Receiving
     */
    @Override
    public void onSearch() {
        ModelReceivedImage modelReceivedImage = new ModelReceivedImageImpl(context, this);
        modelReceivedImage.getImages(view.getSearchWord());
    }

    /**
     * @param img
     * Getting data from ModelReceivedImage
     */
    public void onReceiveData(Img img) {
        imgList = img.getImages();
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 500, 10, this);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500, 10, this);
        } catch (SecurityException e) {
            onGetError(e.getClass() + " mess=" + e.getMessage());
        }

        if (imgList != null && !imgList.isEmpty()) {
            view.showData(imgList);
        } else {
            view.showEmptyList();
        }

    }

    /**
     * @param location
     * Saved images to storage only at once
     * when get location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (!isDownloaded) {
            if (myLocation == null) {
                myLocation = getLastBestLocation();
                Log.d(LogTagsHolder.TAG, "LAT=" + location.getLatitude() + " LONG=" + location.getLongitude());
            }

            //starting download images
            ModelDownloader modelDownloader = new DownloadFile(context, myLocation, ImageListPresenter.this);
            modelDownloader.downloadFiles(imgList);
            isDownloaded = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        view.showMess(context.getResources().getString(R.string.turnOnGPS));
    }

    /**
     * @return Location object
     * get best location
     */
    private Location getLastBestLocation() {
        Log.d(LogTagsHolder.TAG, "getBestLoc");
        Location locationGPS = null;
        Location locationNet = null;

        try {
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException e) {

        }

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    /**
     * @param mess
     * Transfer mess to View
     */
    public void onGetError(String mess) {
        view.showMess(mess);
    }

    @Override
    public void initMainComponents() {
        //check Internet connection
        checkConnection();

        //check Location Enabled
        checkLocationEnabled();

        //Configuring Realm
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .name("mydb.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void checkConnection() {
        boolean isConnection = InternetChecker.isInternetAvailable(context);
        if (!isConnection) {
            view.showMess(context.getResources().getString(R.string.noInternetConnection));
        }
    }

    private void checkLocationEnabled() {
        boolean isLocationEnabled = LocationChecker.isLocationEnabled(context);
        Log.d(LogTagsHolder.TAG, "isLocEnabled="+isLocationEnabled);
        if (!isLocationEnabled) {
            view.showMess(context.getResources().getString(R.string.noLocationEnabled));
        }
    }

}
