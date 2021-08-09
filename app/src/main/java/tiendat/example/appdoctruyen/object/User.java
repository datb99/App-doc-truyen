package tiendat.example.appdoctruyen.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    String id , name , passwword  , number , email , addr , currentReadingChap , avatar;
    ArrayList<String> arrayReadLater , arrayComicLiked;

    public User(String id, String passwword) {
        this.id = id;
        this.passwword = passwword;
    }

    public User(JSONObject o ) throws JSONException {
        this.name = o.getString("name");
        this.passwword = o.getString("password");
        this.number = o.getString("number");
        this.email = o.getString("email");
        this.addr = o.getString("address");
        this.currentReadingChap = o.getString("currentReadingChap");
        this.avatar = o.getString("avatar");

        String listReadLater = o.getString("readLaterList");
        arrayReadLater = new ArrayList<String>(Arrays.asList(listReadLater.split("/")));

        String listComicLiked = o.getString("comicLikedList");
        arrayComicLiked = new ArrayList<String>(Arrays.asList(listComicLiked.split("/")));

        String string = o.getString("email");
        String[] parts = string.split("@");
        this.id = parts[0];
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCurrentReadingChap() {
        return currentReadingChap;
    }

    public void setCurrentReadingChap(String currentReadingChap) {
        this.currentReadingChap = currentReadingChap;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getArrayReadLater() {
        return arrayReadLater;
    }

    public void setArrayReadLater(ArrayList<String> arrayReadLater) {
        this.arrayReadLater = arrayReadLater;
    }

    public ArrayList<String> getArrayComicLiked() {
        return arrayComicLiked;
    }

    public void setArrayComicLiked(ArrayList<String> arrayComicLiked) {
        this.arrayComicLiked = arrayComicLiked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
