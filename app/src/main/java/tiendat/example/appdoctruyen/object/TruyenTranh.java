package tiendat.example.appdoctruyen.object;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TruyenTranh implements Serializable {

    private String id , url , comicName , kindOfComic  ;
    private int likedCount;

    public TruyenTranh(){
    }

    public TruyenTranh(String id  , String url , String comicName , String kindOfComic){
        this.id = id;
        this.url = url;
        this.comicName = comicName;
        this.kindOfComic = kindOfComic;
        this.likedCount = 0;
    }

    public TruyenTranh(JSONObject o) throws JSONException {
        this.id                 = o.getString("id");
        this.url                = o.getString("url");
        this.comicName          = o.getString("name");
        this.kindOfComic        = o.getString("kindOfComic");
        try{
            this.likedCount     = Integer.parseInt(o.getString("likedCount"));
        }catch (Exception e){
            this.likedCount     = 0;
        }


    }

    //    public TruyenTranh(String tenTruyen, String tenChap, String linkAnh) {
//        this.tenTruyen = tenTruyen;
//        this.tenChap = tenChap;
//        this.LinkAnh = linkAnh;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getKindOfComic() {
        return kindOfComic;
    }

    public void setKindOfComic(String kindOfComic) {
        this.kindOfComic = kindOfComic;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }



}
