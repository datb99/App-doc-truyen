package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayTruyenTuChap;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class ApiLayTruyenTuChap extends AsyncTask<Void, Void , Void> {

    String idChap , data;
    LayTruyenTuChap layTruyenTuChap;

    public ApiLayTruyenTuChap(LayTruyenTuChap layTruyenTuChap, String idChap){
        this.idChap = idChap;
        this.layTruyenTuChap = layTruyenTuChap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://"+ global.ip_address +"/public/api/layCurrentChap.php?idChap=" + idChap;
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
        try {
            JSONArray array = new JSONArray(data);
            JSONObject object = array.getJSONObject(0);
            TruyenTranh truyenTranh = new TruyenTranh(object);
            layTruyenTuChap.ketThucLayTruyenTuChap(truyenTranh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
