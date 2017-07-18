package ua.hanasaka.testestafeta.view;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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


public class MainActivity extends AppCompatActivity implements ua.hanasaka.testestafeta.view.View {

    private static final String TAG = "log";
    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Presenter presenter;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        presenter = new ImageListPresenter(this);


        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("mydb.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (etSearch.getText().toString().equals("")) {
                                Log.d(TAG, "word=" + getResources().getString(R.string.etEmpty));
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.etEmpty), Toast.LENGTH_SHORT).show();
                                return false;
                            } else {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                clear();
                                presenter.onSearch();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void showData(List<Image> list) {
        adapter.setImageList(list);
    }

    @Override
    public void showError(String error) {

    }

    public void clear() {
        recyclerView.removeAllViewsInLayout();
    }

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

    private String getRealPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
