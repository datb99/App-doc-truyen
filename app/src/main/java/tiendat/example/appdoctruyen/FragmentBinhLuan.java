package tiendat.example.appdoctruyen;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.BinhLuanAdapter;
import tiendat.example.appdoctruyen.object.BinhLuan;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBinhLuan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBinhLuan extends Fragment {

    ListView listView;
    ArrayList<BinhLuan> arrBinhLuan = new ArrayList<>();
    BinhLuanAdapter adapter;

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

        for (int i = 0 ; i < 15 ; i++){
            BinhLuan bl = new BinhLuan("truyen nay hay lam moi nguoi ne vao xem" , "30/06/2021" , "datbcva");
            arrBinhLuan.add(bl);
        }

        return inflater.inflate(R.layout.fragment_binh_luan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getView().findViewById(R.id.lsvBinhLuan);

        adapter = new BinhLuanAdapter(getActivity() , 0 , arrBinhLuan);
        listView.setAdapter(adapter);
    }
}