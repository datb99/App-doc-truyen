package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;

public class ApiLayLikedListComic extends AsyncTask<Void , Void , Void> {

    String id , data;
    LayTruyenVe layTruyenVe;

    public ApiLayLikedListComic(String id , LayTruyenVe layTruyenVe){
        this.id = id;
        this.layTruyenVe = layTruyenVe;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/layListLikedComic.php?id=" + id;

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
            layTruyenVe.ketThuc(data);
    }
}
