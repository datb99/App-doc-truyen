package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ImgAdapter;
import tiendat.example.appdoctruyen.api.ApiChapTruyen;
import tiendat.example.appdoctruyen.api.ApiLayAnh;
import tiendat.example.appdoctruyen.api.ApiLayAnhOffline;
import tiendat.example.appdoctruyen.api.ApiUpdateCurrentChap;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

import static tiendat.example.appdoctruyen.global.global.truyenTranh;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe  {

    RecyclerView imgRecycleView;
    ArrayList<String> arrUrlAnh;
    ImgAdapter adapter;
    String idChap;
    ImageButton display , next , preview;
    ConstraintLayout constraintLayout;

    ArrayList<ChapTruyen> arrayList = new ArrayList<>();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
        if (!global.isOffline){
            new ApiLayAnh(this , idChap ).execute();
        }else {
            new ApiLayAnhOffline(this , idChap , getApplicationContext()).execute();
        }


    }

    private void init() {
        Bundle b = getIntent().getBundleExtra("data");
        idChap = b.getString("idChap");

    }

    private void anhXa() {
        imgRecycleView = findViewById(R.id.img_recycleview);
        constraintLayout = findViewById(R.id.constrait_controlchap_container);
        display = findViewById(R.id.displayChapControl);
        next = findViewById(R.id.next);
        preview = findViewById(R.id.preview);
    }

    private void setUp() {

    }

    private void setClick() {
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!global.isOffline){
                    Transition transition = new Slide();
                    transition.setDuration(100);
                    transition.addTarget(constraintLayout);
                    TransitionManager.beginDelayedTransition(findViewById(R.id.chapControlContainer), transition);

                    if (constraintLayout.getVisibility() == View.VISIBLE){
                        constraintLayout.setVisibility(View.GONE);
                    }else {
                        constraintLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < arrayList.size()){
                    position ++;
                    new ApiLayAnh(DocTruyenActivity.this , arrayList.get(position).getId() ).execute();
                    new ApiUpdateCurrentChap(global.user.getEmail() , arrayList.get(position).getId()).execute();
                }

            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0){
                    position --;
                    new ApiLayAnh(DocTruyenActivity.this , arrayList.get(position).getId() ).execute();
                    new ApiUpdateCurrentChap(global.user.getEmail() , arrayList.get(position).getId()).execute();
                }

            }
        });
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
        }catch (JSONException e){

        }
        adapter = new ImgAdapter(this , arrUrlAnh);
        imgRecycleView.setAdapter(adapter);
        imgRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void ketThucOffline(ArrayList<String> arrayList) {
        arrUrlAnh = new ArrayList<>(arrayList);
        adapter = new ImgAdapter(this , arrUrlAnh);
        imgRecycleView.setAdapter(adapter);
        imgRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void biLoi() {

    }

}