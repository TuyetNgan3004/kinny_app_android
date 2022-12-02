package Models;

public class NguoiDung {

     private int _sdt;
     private String _matkhau;
     private String _ten;
     private int _tuoi;
     private String _gioitinh;
     private double _chieucao;
     private double _cannangbandau;
     private double _luyentap;



     public NguoiDung() {
     }

     public NguoiDung(int _sdt, String _matkhau) {
          this._sdt = _sdt;
          this._matkhau = _matkhau;
     }

     public NguoiDung(String _ten, int _tuoi, String _gioitinh, double _chieucao, double _cannangbandau) {
          this._ten = _ten;
          this._tuoi = _tuoi;
          this._gioitinh = _gioitinh;
          this._chieucao = _chieucao;
          this._cannangbandau = _cannangbandau;
     }
     public double get_luyentap() {
          return _luyentap;
     }

     public void set_luyentap(double _luyentap) {
          this._luyentap = _luyentap;
     }

     public int get_sdt() {
          return _sdt;
     }

     public void set_sdt(int _sdt) {
          this._sdt = _sdt;
     }

     public String get_matkhau() {
          return _matkhau;
     }

     public void set_matkhau(String _matkhau) {
          this._matkhau = _matkhau;
     }

     public String get_ten() {
          return _ten;
     }

     public void set_ten(String _ten) {
          this._ten = _ten;
     }

     public int get_tuoi() {
          return _tuoi;
     }

     public void set_tuoi(int _tuoi) {
          this._tuoi = _tuoi;
     }

     public String get_gioitinh() {
          return _gioitinh;
     }

     public void set_gioitinh(String _gioitinh) {
          this._gioitinh = _gioitinh;
     }

     public double get_chieucao() {
          return _chieucao;
     }

     public void set_chieucao(double _chieucao) {
          this._chieucao = _chieucao;
     }

     public double get_cannangbandau() {
          return _cannangbandau;
     }

     public void set_cannangbandau(double _cannangbandau) {
          this._cannangbandau = _cannangbandau;
     }
}
