package tiendat.example.appdoctruyen.object;

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
}