package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayBinhLuan;

public class ApiBinhLuan extends AsyncTask<Void , Void , Void> {

    String data;
    LayBinhLuan layBinhLuan;
    String noidung , ngaydang , iduser , idtruyen;


    public ApiBinhLuan(LayBinhLuan layBinhLuan, String noidung, String ngaydang, String iduser, String idtruyen) {
        this.layBinhLuan = layBinhLuan;
        this.noidung = noidung;
        this.ngaydang = ngaydang;
        this.iduser = iduser;
        this.idtruyen = idtruyen;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
//        String url = "http://"+ global.ip_address +"/public/api/themBinhLuan.php?" +
//                "content=" + noidung +
//                "&&date="+ ngaydang +
//                "&&idUser="+ iduser +
//                "&&idComic=" + idtruyen;
        String url = "http://"+ global.ip_address +"/fashi/api/themBinhLuan.php" +
                "?idUser=" + iduser +
                "&&idComic=" + idtruyen +
                "&&content=" + noidung +
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
            this.layBinhLuan.ketThucBinhLuan(data);
        }
    }
}
