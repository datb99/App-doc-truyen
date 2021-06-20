package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;

public class ApiBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    LayBinhLuan layBinhLuan;
    String noidung , ngaydang , iduser , idtruyen;


    public ApiBinhLuan(LayBinhLuan layBinhLuan, String noidung, String ngaydang, String iduser, String idtruyen) {
        this.layBinhLuan = layBinhLuan;
        this.noidung = noidung;
        this.ngaydang = ngaydang;
        this.iduser = iduser;
        this.idtruyen = idtruyen;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/themBinhLuan.php" +
                "?noidung=" + noidung +
                "&&ngaydang=" + ngaydang +
                "&&userid=" + iduser +
                "&&truyenid=" + idtruyen).build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();

        } catch (IOException e) {
            e.printStackTrace();
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){

        }else {
            this.layBinhLuan.ketThucBinhLuan(data);
        }
    }
}
