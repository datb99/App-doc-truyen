package tiendat.example.appdoctruyen.api;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;
import tiendat.example.appdoctruyen.interfaces.LayTruyenVe;
import tiendat.example.appdoctruyen.object.ChapTruyen;


public class ApiChapTruyen extends AsyncTask<Void , Void , Void> {

    String data;
    LayChapVe layChapVe;
    String idTruyen;
    ArrayList<ChapTruyen> arrayList = new ArrayList<>();
    Context context;

    public ApiChapTruyen(LayChapVe layChapVe , String idTruyen , Context context){
        this.layChapVe = layChapVe;
        this.layChapVe.batDau();
        this.idTruyen = idTruyen;
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        if(!global.isOffline){
            OkHttpClient client = new OkHttpClient();
            //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
            //Request request = new Request.Builder().url("https://mydatabase30619.000webhostapp.com/layTruyen.php").build();
            //Request request = new Request.Builder().url("https://60ae66cf5b8c300017dea6f3.mockapi.io/api/v1/TruyenTranh").build();
            //Request request = new Request.Builder().url("http://"+ global.ip_address +"/public/api/layChap.php?id=" + idTruyen).build();
            String url = "http://"+ global.ip_address +"/fashi/api/layChap.php?id="+ idTruyen;
            Request request = new Request.Builder().url(url).build();
            data = null;
            try {
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                data = body.string();
                Log.d("TAG1432", "doInBackground: done lay truyen ve");

            } catch (IOException e) {
                e.printStackTrace();
                data = null;
            }
        }else {
            Database database = new Database(context , "Offline.sqlite" , null , 1);
            Cursor data = database.GetData("SELECT * FROM chap WHERE idComic = "+ idTruyen +"");
            while (data.moveToNext()){
                String id = data.getString(0);
                String chapName = data.getString(1);
                String date = data.getString(2);
                String idComic = data.getString(3);
                ChapTruyen chapTruyen = new ChapTruyen(id , chapName , date , idComic);
                arrayList.add(chapTruyen);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.layChapVe.biLoi();
        }else {
            this.layChapVe.ketThuc(data);
        }
        if(global.isOffline){
            this.layChapVe.ketThucOffline(arrayList);
        }
    }
}
