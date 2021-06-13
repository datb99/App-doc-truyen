package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.api.ApiLayAnh;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe {

    ImageView img;
    ArrayList<String> arrUrlAnh;
    int soTrang , soTrangDangDoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiLayAnh(this).execute();

    }

    private void init() {




    }

    private void anhXa() {
        img = findViewById(R.id.imgAnh);

    }

    private void setUp() {
        //docTheoTrang(0);
    }

    private void setClick() {

    }

    public void left(View view) {
        docTheoTrang(-1);
    }

    public void right(View view) {
        docTheoTrang( 1);
    }

    private void docTheoTrang(int i){
        soTrangDangDoc  = soTrangDangDoc + i;
        if(soTrangDangDoc == 0 ){
            soTrangDangDoc ++;
        }
        if(soTrangDangDoc > soTrang){
            soTrangDangDoc = soTrang;
        }
        Glide.with(this).load(arrUrlAnh.get(soTrangDangDoc - 1)).into(img);
    }

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
            soTrang = arrUrlAnh.size();
            soTrangDangDoc = 1;
            docTheoTrang(0);
        }catch (JSONException e){

        }


    }

    @Override
    public void biLoi() {

    }
}