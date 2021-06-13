package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DocTruyenActivity extends AppCompatActivity {

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

    }

    private void init() {
        arrUrlAnh = new ArrayList<>();
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-01-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-02-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-03-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-04-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-05-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-06-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-07-BLOGTRUYEN-31.jpg");
        arrUrlAnh.add("https://truyenkinhdien.com/wp-content/uploads/2020/08/Trang-08-BLOGTRUYEN-31.jpg");
        soTrang = arrUrlAnh.size();
        soTrangDangDoc = 1;



    }

    private void anhXa() {
        img = findViewById(R.id.imgAnh);

    }

    private void setUp() {
        docTheoTrang(0);
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
}