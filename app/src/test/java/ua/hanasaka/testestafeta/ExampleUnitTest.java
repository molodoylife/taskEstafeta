package ua.hanasaka.testestafeta;

import org.junit.Test;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ua.hanasaka.testestafeta.model.data.ImageDB;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void checkRealmData() throws Exception {

        RealmConfiguration config = new RealmConfiguration.Builder(InstrumentationRegistry.getContext())
                .name("mydb.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<ImageDB> images = realm.where(ImageDB.class)
                .findAll();

        assertNotNull(images);

    }
}