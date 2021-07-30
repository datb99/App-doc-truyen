package tiendat.example.appdoctruyen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import tiendat.example.appdoctruyen.R;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class LikedListAdapter extends ArrayAdapter<TruyenTranh>  {

    Context context;
    ArrayList<TruyenTranh> arrayList;

    public LikedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TruyenTranh> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
    }

    public void sortTruyen(String s){
        s = s.toUpperCase();

        int k = 0;

        for (int i = 0 ; i < arrayList.size() ; i ++){

            TruyenTranh t = arrayList.get(i);
            String ten = t.getComicName().toUpperCase();
            if(ten.contains(s)){
                arrayList.set(i , arrayList.get(k));
                arrayList.set(k , t);
                k ++;
            }
        }
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen , null);
        }

        if(arrayList.size() > 0 ){
            TruyenTranh truyenTranh = this.arrayList.get(position);
            TextView tenTenTruyen =  convertView.findViewById(R.id.txvTenTruyen);
            TextView tenTenChap =  convertView.findViewById(R.id.txvTenChap);
            ImageView imgAnhTruyen = convertView.findViewById(R.id.imgAnhTruyen);

            tenTenTruyen.setText(truyenTranh.getComicName());

            tenTenChap.setText("Lượt thích : " + truyenTranh.getLikedCount());

            String url = "http://" + global.ip_address + truyenTranh.getUrl();

            Glide.with(this.context).load(url).into(imgAnhTruyen);
        }
        return convertView;
    }
}
