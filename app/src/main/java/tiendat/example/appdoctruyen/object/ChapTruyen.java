package tiendat.example.appdoctruyen.object;

import org.json.JSONException;
import org.json.JSONObject;

public class ChapTruyen {
    String TenChap , NgayDang;

    public String getTenChap() {
        return TenChap;
    }

    public void setTenChap(String tenChap) {
        TenChap = tenChap;
    }

    public String getNgayDang() {
        return NgayDang;
    }

    public void setNgayDang(String ngayDang) {
        NgayDang = ngayDang;
    }

    public ChapTruyen(String tenChap, String ngayDang) {
        this.TenChap = tenChap;
        this.NgayDang = ngayDang;
    }

    public  ChapTruyen(JSONObject o) throws JSONException {
        TenChap = o.getString("tenchap");
        NgayDang = o.getString("ngaynhap");
    }
}
