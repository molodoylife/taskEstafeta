package ua.hanasaka.testestafeta.presenter;


import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Define interface for presenter
 */
public interface Presenter {
    void initMainComponents();

    void onSearch();

    void onReceiveData (Img img);

    void onGetError(String error);
}
