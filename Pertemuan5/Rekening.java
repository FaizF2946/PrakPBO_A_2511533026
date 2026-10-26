package Pertemuan5;

import java.util.ArrayList;

public class Rekening {

    private String nomorRekening;
    private String namaPemilik;
    private String pin;
    
    // UBAH MENJADI PROTECTED
    protected double saldo;
    protected ArrayList<Transaksi> riwayatTransaksi;
    
    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        this.riwayatTransaksi = new ArrayList<>();
        
        if (pinAwal != null && pinAwal.length() == 6) {
            this.pin = pinAwal;
        } else {
            System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
            this.pin = "123456";
        }
        
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
    }
    
    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }
    
    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }
    
    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: nominal setor harus lebih dari 0!");
        }
    }
    
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false;
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
            return false;
        } else {
            saldo -= nominal;
            
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
            riwayatTransaksi.add(trxBaru);
            
            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Sisa saldo: Rp" + saldo);
            return true;
        }
    }
    
    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
            return;
        }
        
        boolean statusTarik = this.tarikTunai(nominal);
        
        if (statusTarik) {
            tujuan.setorTunai(nominal);
            System.out.println("Transfer sebesar Rp" + nominal + " ke rekening " + tujuan.getNomorRekening() + " BERHASIL!");
        } else {
            System.out.println("Transfer Gagal!");
        }
    }
    
    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("--------------------");
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
        } else {
            for (Transaksi trx : riwayatTransaksi) {
                trx.cetakDetail();
            }
        }
    }
}