package ua.hanasaka.testestafeta.view;

import android.content.Context;

import java.util.List;

import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public interface View {
    void showData(List<Image> list);

    void showError(String error);

    void showEmptyList();

    String getSearchWord();

    Context getContext();
}
