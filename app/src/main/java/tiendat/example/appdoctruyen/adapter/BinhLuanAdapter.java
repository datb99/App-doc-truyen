package tiendat.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import tiendat.example.appdoctruyen.R;
import tiendat.example.appdoctruyen.object.BinhLuan;
import tiendat.example.appdoctruyen.object.ChapTruyen;

public class BinhLuanAdapter extends ArrayAdapter<BinhLuan> {
    private Context ct ;
    private ArrayList<BinhLuan> arr;

    public BinhLuanAdapter(@NonNull Context context, int resource, @NonNull List<BinhLuan> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_binhluan , null);
        }
        if(arr.size() > 0){
            TextView txvUser , txvNgayDang , txvNoiDung;
            txvUser = convertView.findViewById(R.id.binhluan_user);
            txvNoiDung = convertView.findViewById(R.id.binhluan_noidung);
            txvNgayDang = convertView.findViewById(R.id.binhluan_ngay);

            BinhLuan binhLuan = arr.get(position);
            txvUser.setText(binhLuan.getIdUser() + " :");
            txvNoiDung.setText(binhLuan.getNoiDung());
            txvNgayDang.setText("Ngày đăng : " + binhLuan.getNgayDang());
        }
        return convertView;
    }
}
