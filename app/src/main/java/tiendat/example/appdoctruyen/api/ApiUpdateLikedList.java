package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.UpdateLikedList;

public class ApiUpdateLikedList extends AsyncTask<Void , Void , Void> {

    ArrayList<String> arrayList;
    String id , idComic;
    int likeCount;
    String data , data1;
    UpdateLikedList updateLikedList;

    public ApiUpdateLikedList(UpdateLikedList updateLikedList,ArrayList<String> arrayList , String id , String idComic , int likeCount){
        this.updateLikedList = updateLikedList;
        this.arrayList = arrayList;
        this.id = id;
        this.idComic = idComic;
        this.likeCount = likeCount;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String newList = "";
        for (int i = 0 ; i < arrayList.size() ; i ++){
            newList = newList + arrayList.get(i) + "/" ;
        }


        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/updateLikedList.php?" +
                "id=" + id +
                "&&likeList=" + newList;

        String url1 = "http://"+ global.ip_address +"/public/api/updateLikeCount.php?" +
                "idComic=" + idComic +
                "&&likedCount=" + likeCount;

        Request request = new Request.Builder().url(url).build();
        Request request1 = new Request.Builder().url(url1).build();

        data = null;
        data1 = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
            Response response1 = client.newCall(request1).execute();
            ResponseBody body1 = response1.body();
            data1 = body1.string();
        } catch (IOException e) {
            data = null;
            data1 = null;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (data != null && data1 != null){
            updateLikedList.finishUpdateLikedList();
        }
    }
}
