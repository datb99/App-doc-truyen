package tiendat.example.appdoctruyen;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import tiendat.example.appdoctruyen.adapter.BinhLuanAdapter;
import tiendat.example.appdoctruyen.api.ApiBinhLuan;
import tiendat.example.appdoctruyen.api.ApiLayBinhLuan;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;
import tiendat.example.appdoctruyen.object.BinhLuan;
import tiendat.example.appdoctruyen.object.ChapTruyen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBinhLuan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBinhLuan extends Fragment implements LayBinhLuan  {

    ListView listView;
    ArrayList<BinhLuan> arrBinhLuan = new ArrayList<>();
    BinhLuanAdapter adapter;
    EditText edtbinhluan;
    TextView txvDang;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentBinhLuan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBinhLuan.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBinhLuan newInstance(String param1, String param2) {
        FragmentBinhLuan fragment = new FragmentBinhLuan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_binh_luan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txvDang = getView().findViewById(R.id.txvDang);
        edtbinhluan = getView().findViewById(R.id.edtBinhLuan);
        listView = getView().findViewById(R.id.lsvBinhLuan);

        new ApiLayBinhLuan(this , global.truyenTranh.getId()).execute();
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        try {
            arrBinhLuan.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                arrBinhLuan.add(new BinhLuan(o));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new BinhLuanAdapter(getActivity() , 0 , arrBinhLuan);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BinhLuan bl = arrBinhLuan.get(position);
                if(bl.getIdUser().matches(global.user.getId())){
                    ((ChapActivity)getActivity()).callEditFragment(bl);
                }
                return true;
            }
        });

        txvDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtbinhluan.getText().toString().matches("")){
                    new ApiBinhLuan(FragmentBinhLuan.this , edtbinhluan.getText().toString() , currentDate() , global.user.getId() , global.truyenTranh.getId() ).execute();
                    edtbinhluan.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtbinhluan.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public void biLoi() {

    }

    @Override
    public void ketThucBinhLuan(String data) {
        new ApiLayBinhLuan(this , global.truyenTranh.getId()).execute();
    }

    public String currentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

}