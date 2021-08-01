package tiendat.example.appdoctruyen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.adapter.ChapTruyenAdapter;
import tiendat.example.appdoctruyen.api.ApiSaveChap;
import tiendat.example.appdoctruyen.api.ApiUpdateCurrentChap;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.object.ChapTruyen;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentChap extends Fragment {

    ListView listView;
    ArrayList<ChapTruyen> arrChap = new ArrayList<>();
    ChapTruyenAdapter chapTruyenAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentChap() {
        // Required empty public constructor
    }

    public FragmentChap(ArrayList<ChapTruyen> arrChap) {
        this.arrChap = arrChap;
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
        return inflater.inflate(R.layout.fragment_chap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.lsvDanhSachChap);

        chapTruyenAdapter = new ChapTruyenAdapter(getActivity(), 0, arrChap);

        listView.setAdapter(chapTruyenAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("idChap" , arrChap.get(position).getId());
                Intent intent = new Intent(getContext() , DocTruyenActivity.class);
                intent.putExtra("data" , b);
                startActivity(intent);
                if (!global.isOffline){
                    new ApiUpdateCurrentChap(global.user.getId() , arrChap.get(position).getId()).execute();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String[] option = {"thêm vào đọc sau"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext() , android.R.layout.select_dialog_item , option);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("tuỳ chọn");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do this after click
                        new ApiSaveChap(getContext() , arrChap.get(position) , global.truyenTranh).execute();
                        Toast.makeText(getContext() , option[which] + " selected" , Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog a = builder.create();
                a.show();
                return true;
            }
        });
    }
}