package Pertemuan2;

public class Transaksi {
	String idTransaksi;
	String jenis;
	double nominal;
	
	// COnstructor
	public Transaksi(String id, String jenis, double nominal){
		this.idTransaksi = id;
		this.jenis = jenis;
		this.nominal = nominal;
	}

public void cetakDetail() {
	System.out.println("ID: "  + idTransaksi + " | jenis: " + jenis + " | nominal: Rp" + nominal);
}
}
