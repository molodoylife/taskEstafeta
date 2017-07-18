package ua.hanasaka.testestafeta.model;

import android.location.Location;
import android.util.Log;

import io.realm.Realm;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.data.ImageDB;

/**
 * Created by Oleksandr Molodykh on 18.07.2017.
 */

public class DataBaseSaver {
    private final Location location;
    String TAG = "logs";

    public DataBaseSaver(Location loc) {
        location = loc;
    }

    public void saveToDb(Image image){
        try{
            ImageDB imageDB = new ImageDB();
            imageDB.setTitle(image.getTitle());
            imageDB.setDate(image.getDateCreated());
            imageDB.setUrl(image.getDisplaySizes().get(0).getUri());
            imageDB.setLat(location.getLatitude());
            imageDB.setLon(location.getLongitude());

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealm(imageDB);
            realm.commitTransaction();

            Log.d(TAG, "data saved to db!");
        } catch (Exception e){
            Log.d(TAG, e.getClass() + " mess=" + e.getMessage());
        }
    }
}
