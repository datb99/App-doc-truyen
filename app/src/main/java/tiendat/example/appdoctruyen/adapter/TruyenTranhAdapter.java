package tiendat.example.appdoctruyen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tiendat.example.appdoctruyen.R;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class TruyenTranhAdapter extends ArrayAdapter<TruyenTranh> {

    private Context ct;
    private ArrayList<TruyenTranh> arr;

    public TruyenTranhAdapter(@NonNull Context context, int resource, @NonNull List<TruyenTranh> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = (ArrayList<TruyenTranh>) objects;
    }

    public void sortTruyen(String s){
        s = s.toUpperCase();

        int k = 0;

        for (int i = 0 ; i < arr.size() ; i ++){

            TruyenTranh t = arr.get(i);
            String ten = t.getTenTruyen().toUpperCase();
            if(ten.contains(s)){
                arr.set(i , arr.get(k));
                arr.set(k , t);
                k ++;

            }
        }
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen , null);
        }

        if(arr.size() > 0 ){
            TruyenTranh truyenTranh = this.arr.get(position);
            TextView tenTenTruyen =  convertView.findViewById(R.id.txvTenTruyen);
            TextView tenTenChap =  convertView.findViewById(R.id.txvTenChap);
            ImageView imgAnhTruyen = convertView.findViewById(R.id.imgAnhTruyen);

            tenTenTruyen.setText(truyenTranh.getTenTruyen());
            tenTenChap.setText(truyenTranh.getTenChap());
            Glide.with(this.ct).load(truyenTranh.getLinkAnh()).into(imgAnhTruyen);
            Log.d("TAG1432", "getView: convertview");
        }
        return convertView;
    }
}
