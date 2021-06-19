package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.DangKy;
import tiendat.example.appdoctruyen.interfaces.DangNhap;

public class ApiDangKy extends AsyncTask<Void , Void , Void> {

    String data;
    DangKy dangKy;
    String id , password;

    public ApiDangKy(DangKy dangKy , String id , String password){
        this.dangKy = dangKy;
        this.id = id;
        this.password = password;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/DangKy.php?id=" + id + "&&password=" + password).build();
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
            this.dangKy.biLoi();
        }else {
            this.dangKy.ketThuc(data);
        }
    }
}
