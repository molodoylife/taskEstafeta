package ua.hanasaka.testestafeta.model.dataBase;

import android.location.Location;

import io.realm.Realm;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.model.data.ImageDB;
import ua.hanasaka.testestafeta.presenter.Presenter;

/**
 * Implementing DataBaseModel interface
 * Saving data to Realm database
 */
public class ModelDataBaseImpl implements ModelDataBase{
    private final Location location;
    private final Presenter presenter;

    public ModelDataBaseImpl(Location loc, Presenter presenter) {
        location = loc;
        this.presenter = presenter;
    }

    /**
     * @param image
     * Save Image to Realm db
     */
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

        } catch (Exception e){
            presenter.onGetError(e.getClass()+" mess "+e.getMessage());
        }
    }
}
