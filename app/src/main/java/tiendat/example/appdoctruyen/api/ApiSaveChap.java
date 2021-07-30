package tiendat.example.appdoctruyen.api;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.global.global;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;
import tiendat.example.appdoctruyen.object.ChapTruyen;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class ApiSaveChap extends AsyncTask<Void , Void , Void> implements LayAnhVe {

    ChapTruyen chapTruyen;
    TruyenTranh truyenTranh;
    Database database;
    Context context;

    public ApiSaveChap(Context context, ChapTruyen chapTruyen , TruyenTranh truyenTranh){
        this.chapTruyen = chapTruyen;
        this.truyenTranh = truyenTranh;
        this.context = context;
        this.database = new Database(context , "Offline.sqlite" , null , 1);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        insertTruyen();

        insertChap();

        insertAnh();

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }

    public void insertTruyen(){
        database.QueryData("CREATE TABLE IF NOT EXISTS truyen(" +
                "id INTEGER PRIMARY KEY , " +
                "imgUrl TEXT  , " +
                "comicName TEXT , " +
                "kindOfComic TEXT , " +
                "likedCount INTEGER)");

        try{
            database.QueryData("INSERT INTO truyen VALUES(" +
                    " "+ truyenTranh.getId() +" " +
                    ", '"+ truyenTranh.getUrl() +"' " +
                    ", '"+ truyenTranh.getComicName() +"' " +
                    ", '"+ truyenTranh.getKindOfComic() +"' " +
                    ", "+ truyenTranh.getLikedCount() +" )");
        }catch (Exception e){

        }

    }

    public void insertChap(){
        database.QueryData("CREATE TABLE IF NOT EXISTS chap(" +
                "id INTEGER PRIMARY KEY " +
                ", chapName TEXT " +
                ", date TEXT " +
                ", idComic INTEGER )");

        String sql = "INSERT INTO chap VALUES(" +
                " "+ chapTruyen.getId() +" " +
                ", '"+ chapTruyen.getTenChap() +"' " +
                ", '"+ chapTruyen.getNgayDang()+"' " +
                ", "+truyenTranh.getId()+")";
        try {
            database.QueryData(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor data = database.GetData("SELECT * FROM chap");
        while (data.moveToNext()){
            Log.d("TAG1432", "insertChap: " + data.getString(0) + data.getString(1) + data.getString(2) + data.getString(3));
        }
    }

    public void insertAnh(){
        new ApiLayAnh(this , chapTruyen.getId()).execute();
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        database.QueryData("CREATE TABLE IF NOT EXISTS anh(" +
                " imgUrl TEXT " +
                ", idChap INTEGER)");
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(data);
            for(int i = 0 ; i < arr.length() ; i++){
                arrayList.add(arr.getString(i));
            }
        }catch (JSONException e){
        }

        for (int i = 0 ; i < arrayList.size() ; i++){
            String url = arrayList.get(i);
            String sql = "INSERT INTO anh VALUES('"+ url +"' , "+ chapTruyen.getId() +" )";
            database.QueryData(sql);
            RequestManager rm = Glide.with(context);
            rm.load("http://" + global.ip_address + url).submit();
        }
    }

    @Override
    public void ketThucOffline(ArrayList<String> arrayList) {

    }

    @Override
    public void biLoi() {

    }
}
