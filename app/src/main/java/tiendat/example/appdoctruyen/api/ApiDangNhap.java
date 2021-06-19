package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.DangNhap;

public class ApiDangNhap extends AsyncTask<Void , Void , Void> {

    String data;
    DangNhap dangNhap;
    String id , password;

    public ApiDangNhap (DangNhap dangNhap , String id , String password){
        this.dangNhap = dangNhap;
        this.id = id;
        this.password = password;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layUser.php?id=" + id + "&&password=" + password).build();
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
            this.dangNhap.biLoi();
        }else {
            this.dangNhap.ketThuc(data);
        }
    }
}
