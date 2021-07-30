package tiendat.example.appdoctruyen.api;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.interfaces.LayAnhVe;

public class ApiLayAnhOffline extends AsyncTask<Void , Void , Void> {

    String idChap;
    LayAnhVe layAnhVe;
    Context context;
    ArrayList<String> arrayList = new ArrayList<>();

    public ApiLayAnhOffline(LayAnhVe layAnhVe , String idChap , Context context){
        this.layAnhVe = layAnhVe;
         this.idChap = idChap;
         this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Database database = new Database(context , "Offline.sqlite" , null , 1);
        Cursor data = database.GetData("SELECT * FROM anh WHERE idChap = "+ idChap +"");
        while (data.moveToNext()){
            String url = data.getString(0);
            arrayList.add(url);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        layAnhVe.ketThucOffline(arrayList);
    }
}
