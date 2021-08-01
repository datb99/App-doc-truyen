package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;

public class ApiUpdateCurrentChap extends AsyncTask<Void , Void , Void> {

    String userId , currentChap ;

    public ApiUpdateCurrentChap(String userId , String currentChap){
        this.userId = userId;
        this.currentChap = currentChap;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/updateCurrentChap.php" +
                "?id="+ userId +"" +
                "&&currentChap="+ currentChap +"";
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
