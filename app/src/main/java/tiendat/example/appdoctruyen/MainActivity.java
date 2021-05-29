package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiLayTruyen;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class MainActivity extends AppCompatActivity implements LayTruyenVe {

    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    EditText edtTimKiem;


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
    }

    private void setUp() {
        gdvDSTruyen.setAdapter(adapter);
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
            Log.d("TAG1432", "ketThuc: aray size " + truyenTranhArrayList.size());
            Log.d("TAG1432", "ketThuc: load xong truyen");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void biLoi() {
        Toast.makeText(this , "Lỗi kết nối" , Toast.LENGTH_SHORT).show();
    }
}