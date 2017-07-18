package ua.hanasaka.testestafeta.view;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.data.Image;
import ua.hanasaka.testestafeta.presenter.ImageListPresenter;
import ua.hanasaka.testestafeta.presenter.Presenter;
import ua.hanasaka.testestafeta.view.adapter.RecyclerViewAdapter;


/**
 * MainActivity class. Implements View interface
 */
public class MainActivity extends AppCompatActivity implements ua.hanasaka.testestafeta.view.View {

    /**
     * EditText for entering search words
     */
    @BindView(R.id.etSearch)
    EditText etSearch;

    /**
     * RecyclerView for displaying images fromInternet
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Presenter presenter;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setting LLManager and RecyclerViewAdapter to RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        //Getting instance of presenter
        presenter = new ImageListPresenter(this);
        presenter.initMainComponents();

        //Presenter onSearch() will fire only if word to search not empty and not cyrillic
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:


                            if (etSearch.getText().toString().equals("")) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.etEmpty), Toast.LENGTH_SHORT).show();
                                return false;
                            } else {


                                //checking if word to search contains cyrillic symbols
                                boolean isStringCyrillic = false;
                                for (int i = 0; i < etSearch.getText().toString().length(); i++) {
                                    if (Character.UnicodeBlock.of(etSearch.getText().toString()
                                            .charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                                        isStringCyrillic = true;
                                        break;
                                    }
                                }
                                if (isStringCyrillic) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    Toast.makeText(MainActivity.this, "Enter English word please!", Toast.LENGTH_SHORT).show();
                                    return false;
                                } else {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    clear();

                                    //start presenter work
                                    presenter.onSearch();
                                    return true;
                                }
                            }
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    /**
     * @param list
     * Transfer list of results to adapter
     */
    @Override
    public void showData(List<Image> list) {
        adapter.setImageList(list);
    }

    @Override
    public void showMess(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Clearing RecyclerView
     */
    public void clear() {
        recyclerView.removeAllViewsInLayout();
    }

    /**
     * If no results to display
     */
    @Override
    public void showEmptyList() {
        clear();
        Toast.makeText(this, getResources().getString(R.string.noImagesFound), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getSearchWord() {
        return etSearch.getText().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
