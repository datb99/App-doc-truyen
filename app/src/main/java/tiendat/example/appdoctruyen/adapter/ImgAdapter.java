package tiendat.example.appdoctruyen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.R;
import tiendat.example.appdoctruyen.global.global;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrImg;

    public ImgAdapter(Context context, ArrayList<String> arrImg) {
        this.context = context;
        this.arrImg = arrImg;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;

        public ViewHolder(@NonNull View item) {
            super(item);
            imgView = item.findViewById(R.id.img_view);

        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.image_item, parent , false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImgAdapter.ViewHolder holder, int position) {
        String url  = arrImg.get(position);
        Log.d("TAG1432", "onBindViewHolder: " + url);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return arrImg.size();
    }
}
