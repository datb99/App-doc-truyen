package tiendat.example.appdoctruyen.api;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.interfaces.LoadOfflineComic;
import tiendat.example.appdoctruyen.object.TruyenTranh;

public class ApiLoadOfflineComic extends AsyncTask<Void , Void , ArrayList<TruyenTranh>> {

    LoadOfflineComic loadOfflineComic;
    ArrayList<TruyenTranh> arrayList = new ArrayList<>();
    Database database;
    Context context;

    public ApiLoadOfflineComic(Context context , LoadOfflineComic loadOfflineComic){
        this.loadOfflineComic = loadOfflineComic;
        //this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    protected ArrayList<TruyenTranh> doInBackground(Void... voids) {
        database = new Database(context , "Offline.sqlite" , null , 1);
        try {
            Cursor data = database.GetData("SELECT * FROM truyen");
            while (data.moveToNext()){
                String id = data.getString(0);
                String url = data.getString(1);
                String name = data.getString(2);
                String kindOfComic = data.getString(3);
                int likedCount = Integer.parseInt(data.getString(4));
                TruyenTranh truyenTranh = new TruyenTranh(id , url , name , kindOfComic );
                arrayList.add(truyenTranh);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<TruyenTranh> arrayList) {
        super.onPostExecute(arrayList);
        loadOfflineComic.finishLoadOfflineComic(arrayList);
    }
}
