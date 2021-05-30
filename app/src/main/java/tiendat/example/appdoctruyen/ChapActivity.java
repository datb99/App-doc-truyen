package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ChapTruyenAdapter;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class ChapActivity extends AppCompatActivity {

    TextView txvTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyenTranh;
    ListView lsvDanhSachChap;
    ArrayList<ChapTruyen> arrChap;
    ChapTruyenAdapter chapTruyenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);

        init();
        anhxa();
        setup();
        setclick();

    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh = (TruyenTranh) b.getSerializable("Truyen");

        //tao du lieu ao
        arrChap = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i++){
            arrChap.add(new ChapTruyen("chapter " + i , "30 - 06 - 1999"));
        }

        chapTruyenAdapter = new ChapTruyenAdapter(this , 0 , arrChap);
    }

    private void anhxa(){
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        lsvDanhSachChap = findViewById(R.id.lsvDanhSachChap);
    }

    private void setup(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

        lsvDanhSachChap.setAdapter(chapTruyenAdapter);
    }

    private void setclick(){

    }

}