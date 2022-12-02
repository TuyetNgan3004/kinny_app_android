package Models;

public class MucTieu {

    Double _cannangMT;
    String _ngayBatDau;
    String _ngayKetThuc;
    Double _tileQuaTrinh;
    Double _sokiquatrinh;


    public MucTieu() {

    }

    public MucTieu(Double _tileQuaTrinh, Double _sokiquatrinh) {
        this._tileQuaTrinh = _tileQuaTrinh;
        this._sokiquatrinh = _sokiquatrinh;
    }

    public MucTieu(Double _cannangMT, String _ngayBatDau, String _ngayKetThuc) {
        this._cannangMT = _cannangMT;
        this._ngayBatDau = _ngayBatDau;
        this._ngayKetThuc = _ngayKetThuc;
    }


    public Double get_cannangMT() {
        return _cannangMT;
    }

    public void set_cannangMT(Double _cannangMT) {
        this._cannangMT = _cannangMT;
    }

    public String get_ngayBatDau() {
        return _ngayBatDau;
    }

    public void set_ngayBatDau(String _ngayBatDau) {
        this._ngayBatDau = _ngayBatDau;
    }

    public String get_ngayKetThuc() {
        return _ngayKetThuc;
    }

    public void set_ngayKetThuc(String _ngayKetThuc) {
        this._ngayKetThuc = _ngayKetThuc;
    }

    public Double get_tileQuaTrinh() {
        return _tileQuaTrinh;
    }

    public void set_tileQuaTrinh(Double _tileQuaTrinh) {
        this._tileQuaTrinh = _tileQuaTrinh;
    }

    public Double get_sokiquatrinh() {
        return _sokiquatrinh;
    }

    public void set_sokiquatrinh(Double _sokiquatrinh) {
        this._sokiquatrinh = _sokiquatrinh;
    }
}
