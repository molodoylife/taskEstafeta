package ua.hanasaka.testestafeta.view;

import android.content.Context;

import java.util.List;

import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Define interface for View
 */
public interface View {
    void showData(List<Image> list);

    void showMess(String error);

    void showEmptyList();

    String getSearchWord();

    Context getContext();
}
