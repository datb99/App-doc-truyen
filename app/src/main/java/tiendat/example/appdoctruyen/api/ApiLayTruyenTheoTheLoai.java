package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;

public class ApiLayTruyenTheoTheLoai extends AsyncTask<Void , Void , Void> {

    String type , data;
    LayTruyenVe layTruyenVe;

    public ApiLayTruyenTheoTheLoai(LayTruyenVe layTruyenVe , String type){
        this.type = type;
        this.layTruyenVe = layTruyenVe;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();

        String url = "http://"+ global.ip_address +"/public/api/layTruyenTheoType.php?kindOfComic=" + type;

        Request request = new Request.Builder().url(url).build();
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
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (data != null){
            this.layTruyenVe.ketThuc(data);
        }else {
            this.layTruyenVe.biLoi();
        }
    }
}
