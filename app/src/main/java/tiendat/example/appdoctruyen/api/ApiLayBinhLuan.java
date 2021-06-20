package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;


public class ApiLayBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    LayBinhLuan layBinhLuan;
    String idTruyen;

    public ApiLayBinhLuan(LayBinhLuan layBinhLuan , String idTruyen){
        this.layBinhLuan = layBinhLuan;
        this.layBinhLuan.batDau();
        this.idTruyen = idTruyen;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
        //Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layTruyen.php").build();
        //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
        Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layBinhLuan.php?id=" + idTruyen).build();
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
            this.layBinhLuan.biLoi();
        }else {
            this.layBinhLuan.ketThuc(data);
        }
    }
}
