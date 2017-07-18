package ua.hanasaka.testestafeta;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.hanasaka.testestafeta.model.Model;
import ua.hanasaka.testestafeta.model.ModelImpl;
import ua.hanasaka.testestafeta.model.api.ApiInterface;
import ua.hanasaka.testestafeta.model.api.ApiModule;
import ua.hanasaka.testestafeta.model.data.Img;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    String TAG = "log";

    @Ignore
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("ua.hanasaka.testestafeta", appContext.getPackageName());
    }


}
