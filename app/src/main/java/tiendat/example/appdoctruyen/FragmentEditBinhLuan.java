package tiendat.example.appdoctruyen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import tiendat.example.appdoctruyen.api.ApiLayBinhLuan;
import tiendat.example.appdoctruyen.api.ApiSuaBinhLuan;
import tiendat.example.appdoctruyen.api.ApiXoaBinhLuan;
import tiendat.example.appdoctruyen.interfaces.EditBinhLuan;
import tiendat.example.appdoctruyen.object.BinhLuan;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditBinhLuan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditBinhLuan extends Fragment implements EditBinhLuan {

    EditText txv_edit_binhluan;
    Button sua , xoa , exit;
    BinhLuan binhLuan;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentEditBinhLuan() {
        // Required empty public constructor
    }

    public FragmentEditBinhLuan(BinhLuan binhLuan){
        this.binhLuan = binhLuan;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEditBinhLuan.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditBinhLuan newInstance(String param1, String param2) {
        FragmentEditBinhLuan fragment = new FragmentEditBinhLuan();
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
        return inflater.inflate(R.layout.fragment_edit_binh_luan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txv_edit_binhluan = getView().findViewById(R.id.txv_edit_binhluan);
        sua = getView().findViewById(R.id.btn_edit_sua);
        xoa = getView().findViewById(R.id.btn_edit_delete);
        exit = getView().findViewById(R.id.btn_edit_exit);

        txv_edit_binhluan.setText(binhLuan.getNoiDung());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChapActivity)getActivity()).closeEditFragment();
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApiSuaBinhLuan(FragmentEditBinhLuan.this , binhLuan.getId() , txv_edit_binhluan.getText().toString() , binhLuan.getNgayDang()).execute();
            }
        });

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApiXoaBinhLuan(FragmentEditBinhLuan.this , binhLuan.getId()).execute();
            }
        });
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        ((ChapActivity)getActivity()).updateBinhluan();
        ((ChapActivity)getActivity()).closeEditFragment();
    }

    @Override
    public void biLoi() {

    }
}