package ua.hanasaka.testestafeta.model.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Assign interface API for Retrofit2
 */
public interface ApiInterface {
    @Headers("Api-Key: yaf8t844drgr6xa8p2rv2bj4")
    @GET("v3/search/images?fields=date_created%2Cdisplay_set%2Cid%2Ctitle%2Curi_oembed&page_size=10")
    Call<Img> getImg(@Query("phrase") String searchWord);
}
