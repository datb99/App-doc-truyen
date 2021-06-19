package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ImgAdapter;
import tiendat.example.appdoctruyen.api.ApiLayAnh;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe {

    RecyclerView imgRecycleView;
    ArrayList<String> arrUrlAnh;
    ImgAdapter adapter;

    String idChap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiLayAnh(this , idChap).execute();

    }

    private void init() {
        Bundle b = getIntent().getBundleExtra("data");
        idChap = b.getString("idChap");

    }

    private void anhXa() {
        imgRecycleView = findViewById(R.id.img_recycleview);
    }

    private void setUp() {

    }

    private void setClick() {

    }


    /*private void docTheoTrang(int i){
        soTrangDangDoc  = soTrangDangDoc + i;
        if(soTrangDangDoc == 0 ){
            soTrangDangDoc ++;
        }
        if(soTrangDangDoc > soTrang){
            soTrangDangDoc = soTrang;
        }
        Glide.with(this).load(arrUrlAnh.get(soTrangDangDoc - 1)).into(img);
    }*/

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        arrUrlAnh = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(data);
            for(int i = 0 ; i < arr.length() ; i++){
                arrUrlAnh.add(arr.getString(i));
            }
        }catch (JSONException e){

        }
        adapter = new ImgAdapter(this , arrUrlAnh);
        imgRecycleView.setAdapter(adapter);
        imgRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void biLoi() {

    }

}