package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.updateInfo;

public class ApiUpdateInfo extends AsyncTask<Void , Void , Void> {
    String id , password , number , email , address;
    updateInfo updateInfo;

    public ApiUpdateInfo(updateInfo updateInfo ,String id, String password , String number , String email , String address){
        this.id = id;
        this.password = password;
        this.number = number;
        this.email = email;
        this.address = address;
        this.updateInfo = updateInfo;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address+"/public/api/updateInfo.php" +
                "?id="+ id +
                "&&password="+ password +
                "&&number="+ number +
                "&&email="+ email +
                "&&address="+ address;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        updateInfo.finishUpdateInfo();
    }
}
