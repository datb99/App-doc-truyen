package tiendat.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiLayLikedListComic;
import tiendat.example.appdoctruyen.api.ApiLayReadLaterListComic;
import tiendat.example.appdoctruyen.api.ApiLayTruyen;
import tiendat.example.appdoctruyen.api.ApiLoadOfflineComic;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;
import tiendat.example.appdoctruyen.interfaces.LoadOfflineComic;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class MainActivity extends AppCompatActivity implements LayTruyenVe , LoadOfflineComic {

    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    EditText edtTimKiem;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView toolbar , nav_imageview;
    TextView nav_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiLayTruyen(this).execute();
    }

    private void init() {
        //this.deleteDatabase("Offline.sqlite");
        truyenTranhArrayList = new ArrayList<>();
        //truyenTranhArrayList.add(new TruyenTranh("" , "" , ""));
        adapter = new TruyenTranhAdapter(this , 0 , truyenTranhArrayList);
    }

    private void anhXa() {
        gdvDSTruyen = (GridView) findViewById(R.id.gdvDSTruyen);
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        View headerview = navigationView.getHeaderView(0);
        nav_textview = headerview.findViewById(R.id.nav_textview);
        nav_imageview = headerview.findViewById(R.id.nav_imageview);
    }

    private void setUp() {
        gdvDSTruyen.setAdapter(adapter);
        if (global.user == null){
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userData" , getApplicationContext().MODE_PRIVATE);
            String id = sharedPreferences.getString("id", null);
            nav_textview.setText("user ID: " + id);
        }else {
            nav_textview.setText("user ID: " + global.user.getId());
            nav_imageview.setImageResource(R.drawable.ava_flurry);
        }


    }

    private void setClick() {
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtTimKiem.getText().toString();
                adapter.sortTruyen(s);
                gdvDSTruyen.setAdapter(adapter);

            }
        });

        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TruyenTranh truyenTranh = truyenTranhArrayList.get(position);
                global.truyenTranh = truyenTranhArrayList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("Truyen" , truyenTranh);
                Intent intent = new Intent(MainActivity.this , ChapActivity.class);
                intent.putExtra("data" , b);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_menu_main_screen:
                        global.isOffline = false;
                        new ApiLayTruyen(MainActivity.this).execute();
                        break;
                    case R.id.nav_menu_like:
                        global.isOffline = false;
                        new ApiLayLikedListComic(global.user.getId() , MainActivity.this).execute();
                        break;
                    case R.id.nav_menu_curent_chap:
                        global.isOffline = false;

                        break;
                    case R.id.nav_menu_read_later:
                        global.isOffline = false;
                        new ApiLayReadLaterListComic(global.user.getId() , MainActivity.this).execute();
                        break;
                    case R.id.nav_menu_offline:
                        global.isOffline = true;
                        new ApiLoadOfflineComic(getApplicationContext() , MainActivity.this ).execute();
                        break;
                    case R.id.nav_menu_setting:
                        global.isOffline = false;

                        break;

                    case R.id.nav_menu_logout:
                        global.isOffline = false;

                        break;

                }


                //code to do after choose menu
                return true;
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this , "Đang lấy về" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            truyenTranhArrayList.clear();
            JSONArray arr = new JSONArray(data);
            if (arr.length() > 0){
                for(int i = 0 ; i < arr.length() ; i ++){
                    JSONObject o = arr.getJSONObject(i);
                    truyenTranhArrayList.add(new TruyenTranh(o));
                    gdvDSTruyen.setAdapter(adapter);
                }
                adapter = new TruyenTranhAdapter(this , 0 , truyenTranhArrayList);
            }else {
                adapter.clearData();
                gdvDSTruyen.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this , "Lỗi kết nối" , Toast.LENGTH_SHORT).show();
    }

    public void update(View view) {
        new ApiLayTruyen(this).execute();
    }

    @Override
    public void finishLoadOfflineComic(ArrayList<TruyenTranh> arrayList) {
        try {
            truyenTranhArrayList.clear();
            if (arrayList.size() > 0){
                for (int i = 0 ; i < arrayList.size() ; i ++){
                    truyenTranhArrayList.add(arrayList.get(i));
                    gdvDSTruyen.setAdapter(adapter);
                }
                adapter = new TruyenTranhAdapter(this , 0 , truyenTranhArrayList);
            }else {
                adapter.clearData();
                gdvDSTruyen.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}