package tiendat.example.appdoctruyen.api;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import tiendat.example.appdoctruyen.Sqlite.Database;
import tiendat.example.appdoctruyen.interfaces.LayChapVe;

public class ApiDeleteOfflineChap extends AsyncTask<Void , Void , Void> {

    String idChap;
    String idTruyen;
    Database database;
    Context context;
    LayChapVe layChapVe;

    public ApiDeleteOfflineChap(LayChapVe layChapVe, Context context , String idChap , String idTruyen){
        this.layChapVe = layChapVe;
        this.context = context;
        this.idChap = idChap;
        this.idTruyen = idTruyen;
        this.database = new Database(context , "Offline.sqlite" , null , 1);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        deleteChap();

        deleteAnh();

        deleteTruyen();

        return null;
    }

    private void deleteChap() {
        String sql = "DELETE FROM chap WHERE id = " + idChap;
        database.QueryData(sql);
    }

    private void deleteAnh() {
        String sql = "DELETE FROM anh WHERE idChap = " + idChap;
        database.QueryData(sql);
    }

    private void deleteTruyen() {
        String sql = "SELECT * FROM chap WHERE idComic = " + idTruyen;
        Cursor cursor = database.GetData(sql);
        if (cursor.getCount() == 0){
            String sql1 =  "DELETE FROM truyen WHERE id = " + idTruyen;
            database.QueryData(sql1);
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        layChapVe.ketThucDeleteChapOffline();
    }
}
