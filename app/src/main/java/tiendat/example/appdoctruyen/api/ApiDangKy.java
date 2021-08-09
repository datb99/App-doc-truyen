package tiendat.example.appdoctruyen.api;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.DangKy;
import tiendat.example.appdoctruyen.interfaces.DangNhap;

public class ApiDangKy extends AsyncTask<Void , Void , Void> {

    String data;
    DangKy dangKy;
    String id , password , number , email , address , avatar;

    public ApiDangKy(DangKy dangKy , String id , String password , String number , String email , String address , String avatar){
        this.dangKy = dangKy;
        this.password = password;
        this.number = number;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
        String[] parts = email.split("@");
        this.id = parts[0];
    }


    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30 , TimeUnit.SECONDS);
        client.setWriteTimeout(1 , TimeUnit.MINUTES);

        String url = "http://"+ global.ip_address + "/fashi/api/dangKy.php" +
                "?id=" + id +
                "&&password=" + password +
                "&&number=" + number +
                "&&email=" + email +
                "&&address=" + address +
                "&&avatar=" + avatar;

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
            this.dangKy.biLoi();
        }else {
            this.dangKy.ketThuc(data);
        }
    }
}
