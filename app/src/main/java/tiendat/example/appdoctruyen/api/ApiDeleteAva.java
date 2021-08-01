package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.updateInfo;

public class ApiDeleteAva extends AsyncTask<Void , Void , Void> {

    String userId;
    updateInfo updateInfo;

    public ApiDeleteAva(updateInfo updateInfo , String userId){
        this.updateInfo = updateInfo;
        this.userId = userId;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/deleteAva.php?id=" + userId;

        Request request = new Request.Builder().url(url).build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        updateInfo.finishDeleteAva();
    }
}
