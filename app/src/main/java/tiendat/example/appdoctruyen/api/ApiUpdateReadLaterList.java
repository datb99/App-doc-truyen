package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

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
    String email , data;
    UpdateReadLaterList updateReadLaterList;

    public ApiUpdateReadLaterList (ArrayList<String> arrayList ,String email , UpdateReadLaterList updateReadLaterList){
        this.email = email;
        this.arrayList = arrayList;
        this.updateReadLaterList = updateReadLaterList;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String newList = "";
        for(int i = 0 ; i < arrayList.size() ; i ++){
            newList = newList + arrayList.get(i) + "/";
        }

        Log.d("TAG1432", "doInBackground: " + newList);

        OkHttpClient client = new OkHttpClient();

        String url = "http://"+ global.ip_address +"/fashi/api/updateReadLaterList.php" +
                "?email=" + email +
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
