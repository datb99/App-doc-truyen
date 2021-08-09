package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ChapTruyenAdapter;
import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiChapTruyen;
import tiendat.example.appdoctruyen.api.ApiDeleteOfflineChap;
import tiendat.example.appdoctruyen.api.ApiLayBinhLuan;
import tiendat.example.appdoctruyen.api.ApiSaveChap;
import tiendat.example.appdoctruyen.api.ApiUpdateCurrentChap;
import tiendat.example.appdoctruyen.api.ApiUpdateLikedList;
import tiendat.example.appdoctruyen.api.ApiUpdateReadLaterList;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.interfaces.UpdateLikedList;
import tiendat.example.appdoctruyen.interfaces.UpdateReadLaterList;
import tiendat.example.appdoctruyen.object.BinhLuan;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class  ChapActivity extends AppCompatActivity implements LayChapVe , UpdateLikedList , UpdateReadLaterList {

    TextView txvTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyenTranh;
    ArrayList<ChapTruyen> arrChap = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentChap chapter;

    SectionsPagerAdapter sectionsPagerAdapter;

    FragmentManager fm;
    ImageView fab_liked , fab_read_later;

    Boolean isLiked = false , isReadLater = false;

    int posLiked , posRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);

        init();
        new ApiChapTruyen(this, truyenTranh.getId() , getApplicationContext()).execute();
        anhxa();
        setup();
        if (!global.isOffline){
            setclick();
        }
    }

    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh = (TruyenTranh) b.getSerializable("Truyen");
        String curentChap = b.getString("check current chap");
        if (curentChap != null){
            ChapTruyen chapTruyen = null;
            for (int i = 0 ; i < arrChap.size() ; i ++){
                if (arrChap.get(i).getId().equals(curentChap)){
                    chapTruyen = arrChap.get(i);
                }
            }
            final String[] option = {"Đọc tiếp" , "Huỷ"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item , option);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("select option");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            final AlertDialog a = builder.create();
            a.show();
        }
    }

    private void anhxa(){
        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tablayout);
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        fab_liked = findViewById(R.id.btn_like);
        fab_read_later = findViewById(R.id.btn_read_later);
    }

    private void setup(){
        txvTenTruyens.setText(truyenTranh.getComicName());
        String url = "http://" + global.ip_address + truyenTranh.getUrl();
        Glide.with(this).load(url).into(imgAnhTruyens);
        try {
            for (int i = 0 ; i < global.user.getArrayComicLiked().size() ; i ++){
                if(global.user.getArrayComicLiked().get(i).equals(global.truyenTranh.getId())){
                    Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab_after_liked);
                    fab_liked.setBackground(drawable);
                    isLiked = true;
                    posLiked = i;
                    break;
                }
            }

            for (int i = 0 ; i < global.user.getArrayReadLater().size() ; i ++){
                if(global.user.getArrayReadLater().get(i).equals(global.truyenTranh.getId())){
                    Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab_after_liked);
                    fab_read_later.setBackground(drawable);
                    isReadLater = true;
                    posRead = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setclick(){

        fab_liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLiked){
                    global.truyenTranh.setLikedCount(global.truyenTranh.getLikedCount() - 1);
                    global.user.getArrayComicLiked().remove(posLiked);
                }else {
                    global.truyenTranh.setLikedCount(global.truyenTranh.getLikedCount() + 1);
                    global.user.getArrayComicLiked().add(posLiked , global.truyenTranh.getId() );
                }
                new ApiUpdateLikedList(ChapActivity.this
                        , global.user.getArrayComicLiked()
                        , global.truyenTranh.getId()
                        , global.truyenTranh.getLikedCount()
                        , global.user.getEmail())
                        .execute();
            }
        });

        fab_read_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isReadLater){
                    global.user.getArrayReadLater().remove(posRead);
                }else {
                    global.user.getArrayReadLater().add(posRead , global.truyenTranh.getId());
                }
                new ApiUpdateReadLaterList(global.user.getArrayReadLater()
                        ,global.user.getEmail()
                        ,ChapActivity.this)
                        .execute();
            }
        });

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
            chapter = new FragmentChap(arrChap , this);
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            Bundle b = getIntent().getBundleExtra("data");
            String curentChap = b.getString("check current chap");
            if (curentChap != null){
                ChapTruyen chapTruyen = null;
                for (int i = 0 ; i < arrChap.size() ; i ++){
                    if (arrChap.get(i).getId().equals(curentChap)){
                        chapTruyen = arrChap.get(i);
                    }
                }
                final String[] option = {"Đọc tiếp" , "Huỷ"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item , option);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String title = "bạn đang đọc dở chap " + chapTruyen.getTenChap();
                builder.setTitle(title);
                ChapTruyen finalChapTruyen = chapTruyen;
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            Bundle b = new Bundle();
                            b.putString("idChap" , finalChapTruyen.getId());

                            Intent intent = new Intent(getApplicationContext() , DocTruyenActivity.class);
                            intent.putExtra("data" , b);
                            startActivity(intent);
                            if (!global.isOffline){
                                new ApiUpdateCurrentChap(global.user.getEmail() , finalChapTruyen.getId()).execute();
                            }
                        }else {

                        }

                    }
                });
                final AlertDialog a = builder.create();
                a.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ketThucOffline(ArrayList<ChapTruyen> arrayList) {
        arrChap.clear();
        arrChap = new ArrayList<>(arrayList);
        chapter = new FragmentChap(arrChap , this);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void ketThucDeleteChapOffline() {
        new ApiChapTruyen(this, truyenTranh.getId() , getApplicationContext()).execute();
    }

    @Override
    public void biLoi() {

    }

    @Override
    public void finishUpdateLikedList() {
        if (isLiked){
            Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab);
            fab_liked.setBackground(drawable);
            isLiked = false;
        }else {
            Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab_after_liked);
            fab_liked.setBackground(drawable);
            isLiked = true;
        }
    }

    @Override
    public void finishUpdateReadLaterList() {
        if(isReadLater){
            Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab);
            fab_read_later.setBackground(drawable);
            isReadLater = false;
        }else {
            Drawable drawable = getApplicationContext().getDrawable(R.drawable.item_fab_after_liked);
            fab_read_later.setBackground(drawable);
            isReadLater = true;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = chapter;
                    break;
                case 1:
                    fragment = new FragmentBinhLuan();
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Chapter";
                case 1:
                    return "Bình luận";
            }
            return null;
        }
    }

    public void callEditFragment(BinhLuan binhLuan){
        FragmentEditBinhLuan fragmentEditBinhLuan = new FragmentEditBinhLuan(binhLuan);
        fm  = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("edit fragment");
        ft.add(R.id.one_above_all , fragmentEditBinhLuan , "edit fragment");
        ft.commit();
    }

    public void closeEditFragment(){
        fm.popBackStackImmediate();
        hideKeyboard(this);
    }

    public void updateBinhluan(){
        FragmentBinhLuan fragmentBinhLuan = (FragmentBinhLuan) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
        new ApiLayBinhLuan(fragmentBinhLuan , global.truyenTranh.getId()).execute();
    }

    public static void hideKeyboard(Activity activity) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}