package tiendat.example.appdoctruyen.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;


public class ApiLayAnh extends AsyncTask<Void , Void , Void> {

    String data;
    String idChap;
    LayAnhVe layAnhVe;

    public ApiLayAnh(LayAnhVe layAnhVe , String idChap){
        this.layAnhVe = layAnhVe;
        this.idChap = idChap;
        this.layAnhVe.batDau();
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/layAnh.php?idChap=";
        Request request = new Request.Builder().url(url + idChap).build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
            Log.d("TAG1432", "doInBackground: done lay truyen ve");

        } catch (IOException e) {
            e.printStackTrace();
            data = null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.layAnhVe.biLoi();
        }else {
            this.layAnhVe.ketThuc(data);
        }
    }
}
