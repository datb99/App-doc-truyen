package tiendat.example.appdoctruyen.object;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    String id , passwword;

    public User(String id, String passwword) {
        this.id = id;
        this.passwword = passwword;
    }

    public  User(JSONObject o) throws JSONException {
        id = o.getString("id");
        passwword = o.getString("password");
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
}
