package tiendat.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ChapTruyenAdapter;
import tiendat.example.appdoctruyen.adapter.TruyenTranhAdapter;
import tiendat.example.appdoctruyen.api.ApiChapTruyen;
import tiendat.example.appdoctruyen.api.ApiLayBinhLuan;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.object.BinhLuan;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class  ChapActivity extends AppCompatActivity implements LayChapVe {

    TextView txvTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyenTranh;
    ArrayList<ChapTruyen> arrChap = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentChap chapter;

    SectionsPagerAdapter sectionsPagerAdapter;

    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);

        init();
        new ApiChapTruyen(this, truyenTranh.getId()).execute();
        anhxa();
        setup();
        setclick();



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
        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tablayout);
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
    }

    private void setup(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

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
            chapter = new FragmentChap(arrChap);
            sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {

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