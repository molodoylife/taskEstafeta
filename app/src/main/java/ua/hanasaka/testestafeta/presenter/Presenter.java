package ua.hanasaka.testestafeta.presenter;


import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public interface Presenter {
    void onSearch();

    void onStop();

    void onReceiveData (Img img);
}
