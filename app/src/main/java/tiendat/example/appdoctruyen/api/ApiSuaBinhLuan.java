package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.EditBinhLuan;
import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;

public class ApiSuaBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    EditBinhLuan editBinhLuan;
    String id , noidung , ngaydang ;


    public ApiSuaBinhLuan(EditBinhLuan editBinhLuan, String id ,String noidung, String ngaydang) {
        this.editBinhLuan = editBinhLuan;
        this.id = id;
        this.noidung = noidung;
        this.ngaydang = ngaydang;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Log.d("TAG1432", "https://mydatabase30619.000webhostapp.com/suaBinhLuan.php" +
                "?id=" + id +
                "&&noidung=" + noidung +
                "&&ngaydang=" + ngaydang);
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/suaBinhLuan.php" +
                "?id=" + id +
                "&&noidung=" + noidung +
                "&&ngaydang=" + ngaydang).build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
            Log.d("TAG1432", "doInBackground: sua thanh cong");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("TAG1432", "doInBackground: sua khong thanh cong");
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){

        }else {
            this.editBinhLuan.ketThuc(data);
        }
    }
}
