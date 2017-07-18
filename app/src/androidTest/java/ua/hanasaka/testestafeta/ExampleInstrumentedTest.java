package ua.hanasaka.testestafeta;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ua.hanasaka.testestafeta.model.data.ImageDB;
import ua.hanasaka.testestafeta.utils.InternetChecker;
import ua.hanasaka.testestafeta.utils.LocationChecker;
import ua.hanasaka.testestafeta.utils.LogTagsHolder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void checkRealmData() throws Exception {

        RealmConfiguration config = new RealmConfiguration.Builder(InstrumentationRegistry.getTargetContext())
                .name("mydb.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<ImageDB> images = realm.where(ImageDB.class)
                .findAll();

        assertNotNull(images);

        for (ImageDB image : images) {
            assertNotNull(image.getTitle());
            assertNotNull(image.getDate());
            assertNotNull(image.getUrl());
            assertNotNull(image.getLat());
            assertNotNull(image.getLon());
            Log.d(LogTagsHolder.TAG, image.getTitle() + " " + image.getDate());
        }
    }

    @Test
    public void isInternetConnectionPresent() throws Exception {
        assertTrue(InternetChecker.isInternetAvailable(InstrumentationRegistry.getTargetContext()));
    }

    @Test
    public void isLocationEnabled() throws  Exception {
        assertTrue(LocationChecker.isLocationEnabled(InstrumentationRegistry.getTargetContext()));
    }
}
