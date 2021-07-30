package tiendat.example.appdoctruyen.interfaces;

import java.util.ArrayList;

import tiendat.example.appdoctruyen.object.ChapTruyen;

public interface LayChapVe {
    void batDau();
    void ketThuc(String data);
    void ketThucOffline(ArrayList<ChapTruyen> arrayList);
    void biLoi();
}
