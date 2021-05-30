package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ChapTruyenAdapter;
import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiChapTruyen;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class ChapActivity extends AppCompatActivity implements LayChapVe {

    TextView txvTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyenTranh;
    ListView lsvDanhSachChap;
    ArrayList<ChapTruyen> arrChap = new ArrayList<>();
    ChapTruyenAdapter chapTruyenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);

        init();
        anhxa();
        setup();
        setclick();

        new ApiChapTruyen(this).execute();

    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh = (TruyenTranh) b.getSerializable("Truyen");

        //tao du lieu ao
        /*arrChap = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i++){
            arrChap.add(new ChapTruyen("chapter " + i , "30 - 06 - 1999"));
        }

        chapTruyenAdapter = new ChapTruyenAdapter(this , 0 , arrChap);*/
    }

    private void anhxa(){
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        lsvDanhSachChap = findViewById(R.id.lsvDanhSachChap);
    }

    private void setup(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

        //lsvDanhSachChap.setAdapter(chapTruyenAdapter);
    }

    private void setclick(){

    }

    @Override
    public void batDau() {
        Toast.makeText(this , "Lay chap ve", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void ketThuc(String data) {
        try {
            arrChap.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                arrChap.add(new ChapTruyen(o));
            }
            chapTruyenAdapter = new ChapTruyenAdapter(this, 0, arrChap);
            lsvDanhSachChap.setAdapter(chapTruyenAdapter);
            Log.d("TAG1432", "ketThuc: aray size " + arrChap.size());
            Log.d("TAG1432", "ketThuc: load xong truyen");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {

    }
}