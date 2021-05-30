package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;


public class ApiChapTruyen extends AsyncTask<Void , Void , Void> {

    String data;
    LayChapVe layChapVe;
    String idTruyen;

    public ApiChapTruyen(LayChapVe layChapVe , String idTruyen){
        this.layChapVe = layChapVe;
        this.layChapVe.batDau();
        this.idTruyen = idTruyen;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
        //Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layTruyen.php").build();
        //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layChap.php?id=" + idTruyen).build();
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
            this.layChapVe.biLoi();
        }else {
            this.layChapVe.ketThuc(data);
        }
    }
}
