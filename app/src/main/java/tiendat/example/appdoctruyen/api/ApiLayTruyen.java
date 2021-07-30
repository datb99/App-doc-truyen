package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;


public class ApiLayTruyen extends AsyncTask<Void , Void , Void> {

    String data;
    LayTruyenVe layTruyenVe;

    public ApiLayTruyen(LayTruyenVe layTruyenVe){
        this.layTruyenVe = layTruyenVe;
        this.layTruyenVe.batDau();
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
        //Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layTruyen.php").build();
        Request request = new Request.Builder().url("http://" + global.ip_address +"/public/api/layTruyen.php").build();
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
            this.layTruyenVe.biLoi();
        }else {
            this.layTruyenVe.ketThuc(data);
        }
    }

}
