package ua.hanasaka.testestafeta.model;

import retrofit2.Call;
import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public interface Model {
    Call<Img> getImgList(String searchWord);
}
