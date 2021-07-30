package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.UpdateReadLaterList;

public class ApiUpdateReadLaterList extends AsyncTask<Void , Void , Void> {

    ArrayList<String> arrayList;
    String id , data;
    UpdateReadLaterList updateReadLaterList;

    public ApiUpdateReadLaterList (ArrayList<String> arrayList , UpdateReadLaterList updateReadLaterList , String id){
        this.id = id;
        this.arrayList = arrayList;
        this.updateReadLaterList = updateReadLaterList;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String newList = "";
        for(int i = 0 ; i < arrayList.size() ; i ++){
            newList = newList + arrayList.get(i) + "/";
        }

        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/updateReadLaterList.php?" +
                "id="+ id +
                "&&readLaterList=" + newList;

        Request request = new Request.Builder().url(url).build();

        data = null;

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            data = responseBody.string();
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (data!= null){
            updateReadLaterList.finishUpdateReadLaterList();
        }
    }
}
