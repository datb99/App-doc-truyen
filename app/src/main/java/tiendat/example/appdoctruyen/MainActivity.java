package tiendat.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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

import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiLayTruyen;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class MainActivity extends AppCompatActivity implements LayTruyenVe {

    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    EditText edtTimKiem;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView toolbar;
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
    }

    private void setUp() {
        gdvDSTruyen.setAdapter(adapter);
        nav_textview.setText("user ID: " + global.user.getId());
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
            for(int i = 0 ; i < arr.length() ; i ++){
                JSONObject o = arr.getJSONObject(i);
                truyenTranhArrayList.add(new TruyenTranh(o));
                gdvDSTruyen.setAdapter(adapter);
            }
            adapter = new TruyenTranhAdapter(this , 0 , truyenTranhArrayList);

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
}