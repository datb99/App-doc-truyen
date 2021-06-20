package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.EditBinhLuan;

public class ApiXoaBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    EditBinhLuan editBinhLuan;
    String id ;


    public ApiXoaBinhLuan(EditBinhLuan editBinhLuan, String id) {
        this.editBinhLuan = editBinhLuan;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/xoaBinhLuan.php?id=" + id).build();
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
