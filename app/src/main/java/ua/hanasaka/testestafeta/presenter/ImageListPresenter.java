package ua.hanasaka.testestafeta.presenter;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ua.hanasaka.testestafeta.model.DownloadFile;
import ua.hanasaka.testestafeta.model.ImageReceiver;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.data.Img;
import ua.hanasaka.testestafeta.view.View;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public class ImageListPresenter implements Presenter, LocationListener {
    View view;
    String TAG = "log";
    Location myLocation = null;
    LocationManager locationManager;
    List<Image> imgList;
    boolean isDownloaded = false;

    public ImageListPresenter(View view) {
        this.view = view;
    }

    @Override
    public void onSearch() {
        ImageReceiver imageReceiver = new ImageReceiver(view, this);
        imageReceiver.execute(view.getSearchWord());
    }

    @Override
    public void onStop() {

    }

    public void onReceiveData(Img img) {
        imgList = img.getImages();
        locationManager = (LocationManager) view.getContext().getSystemService(LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 500, 10, this);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500, 10, this);
        }catch (SecurityException e){
            Log.d(TAG, e.getClass() + " mess=" + e.getMessage());
        }

        if (imgList != null && !imgList.isEmpty()) {
            view.showData(imgList);
        } else {
            view.showEmptyList();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocChang");
        if(!isDownloaded){
            if(myLocation==null){
                myLocation = getLastBestLocation();
                Log.d(TAG, "LAT="+location.getLatitude()+" LONG="+location.getLongitude());
            }
            DownloadFile downloadFile = new DownloadFile(view.getContext(), myLocation);
            downloadFile.execute(imgList);
            isDownloaded = true;
        }



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProvEnab");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProvDisab");
    }

    private Location getLastBestLocation() {
        Log.d(TAG, "getBestLoc");
        Location locationGPS=null;
        Location locationNet=null;

        try{
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }catch (SecurityException e){

        }

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }
}
