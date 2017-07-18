package ua.hanasaka.testestafeta.model;

import retrofit2.Call;
import ua.hanasaka.testestafeta.model.api.ApiInterface;
import ua.hanasaka.testestafeta.model.api.ApiModule;
import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public class ModelImpl implements Model {

    ApiInterface apiInterface = ApiModule.getApiInterface();

    @Override
    public Call<Img> getImgList(String searchWord) {
        return apiInterface.getImg(searchWord);
    }
}
