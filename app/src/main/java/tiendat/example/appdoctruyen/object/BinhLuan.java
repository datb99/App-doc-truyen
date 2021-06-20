package tiendat.example.appdoctruyen.object;

import org.json.JSONException;
import org.json.JSONObject;

public class BinhLuan {
    String idBinhLuan ,NoiDung , NgayDang , id , idUser;

    public BinhLuan(String noiDung, String ngayDang, String id, String idUser) {
        this.NoiDung = noiDung;
        this.NgayDang = ngayDang;
        this.id = id;
        this.idUser = idUser;
    }

    public BinhLuan(String noiDung, String ngayDang, String idUser) {
        this.NoiDung = noiDung;
        this.NgayDang = ngayDang;
        this.idUser = idUser;
    }

    public BinhLuan(JSONObject o) throws JSONException {
        this.id = o.getString("id");
        this.NoiDung = o.getString("noidung");
        this.NgayDang = o.getString("ngaydang");
        this.idUser = o.getString("iduser");
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getNgayDang() {
        return NgayDang;
    }

    public void setNgayDang(String ngayDang) {
        NgayDang = ngayDang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
