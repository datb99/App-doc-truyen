package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.EditBinhLuan;
import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;

public class ApiSuaBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    EditBinhLuan editBinhLuan;
    String id , noidung , ngaydang ;


    public ApiSuaBinhLuan(EditBinhLuan editBinhLuan, String id ,String noidung, String ngaydang) {
        this.editBinhLuan = editBinhLuan;
        this.id = id;
        this.noidung = noidung;
        this.ngaydang = ngaydang;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/suaBinhLuan.php?" +
                "id=" + id +
                "&&content="+ noidung +
                "&&date=" + ngaydang;

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
    protected void onPostExecute(Void aVoid) {
        if(data == null){

        }else {
            this.editBinhLuan.ketThuc(data);
        }
    }
}
