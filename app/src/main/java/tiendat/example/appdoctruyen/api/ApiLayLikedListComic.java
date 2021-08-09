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

    String data , email;
    LayTruyenVe layTruyenVe;

    public ApiLayLikedListComic(LayTruyenVe layTruyenVe , String email){
        this.layTruyenVe = layTruyenVe;
        this.email = email;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/fashi/api/layListLikeComic.php?email=" + email;

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
