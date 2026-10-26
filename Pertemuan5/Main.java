package Pertemuan5;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Daftar seluruh rekening terdaftar untuk mendukung fitur transfer
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;
        boolean isRunning = true;
        
        System.out.println("=== SISTEM PERBANKAN MINI ===");
        
        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("6. Cetak Mutasi (Riwayat)");
            System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
            System.out.println("8. Transfer ke Rekening Lain"); // TUGAS: Integrasi Menu 8
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = input.nextInt();
            input.nextLine();
            
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();
                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();
                    
                    double saldo = 0;
                    while (true) {
                        System.out.print("Masukkan Saldo Awal (min. 50.000): ");
                        saldo = input.nextDouble();
                        input.nextLine();
                        
                        if (saldo >= 50000) {
                            break;
                        } else {
                            System.out.println("Nominal saldo awal harus minimal 50.000!");
                        }
                    }
                    
                    System.out.print("Masukkan PIN (6 digit): ");
                    String pin = input.nextLine();
                    
                    System.out.println("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis");
                    System.out.print("Pilihan Anda: ");
                    int jenisProduk = input.nextInt();
                    input.nextLine();
                    
                    if (jenisProduk == 1) {
                        System.out.print("Masukkan Suku Bunga (%): ");
                        double sukuBunga = input.nextDouble();
                        input.nextLine();
                        
                        akunAktif = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
                    } else if (jenisProduk == 2) {
                        System.out.print("Masukkan Batas Overdraft: ");
                        double overdraft = input.nextDouble();
                        input.nextLine();
                        
                        akunAktif = new RekeningGiro(no, nama, saldo, pin, overdraft);
                    } else {
                        System.out.println("Pilihan produk tidak valid!");
                        break;
                    }
                    
                    // Simpan objek ke daftarRekening
                    daftarRekening.add(akunAktif);
                    break;
                
                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        akunAktif.setorTunai(setor);
                    }
                    break;
                
                case 3:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        System.out.print("Masukkan PIN Anda: ");
                        String pinInput = input.nextLine();
                        
                        if (akunAktif.otentikasi(pinInput)) {
                            System.out.print("Masukkan nominal tarik: ");
                            double tarik = input.nextDouble();
                            akunAktif.tarikTunai(tarik);
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;
                    
                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 6:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        System.out.print("Masukkan PIN Anda: ");
                        String pinInput = input.nextLine();
                        
                        if (akunAktif.otentikasi(pinInput)) {
                            akunAktif.cetakMutasi();
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;
                    
                case 7:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        if (akunAktif instanceof RekeningTabungan) {
                            RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
                            tabungan.tambahBungaAkhirBulan();
                        } else {
                            System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
                        }
                    }
                    break;

                case 8: // TUGAS: Transfer Ke Rekening Lain
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        System.out.print("Masukkan Nomor Rekening Tujuan: ");
                        String noTujuan = input.nextLine();
                        
                        // Cari nomor rekening tujuan di daftarRekening
                        Rekening akunTujuan = null;
                        for (Rekening r : daftarRekening) {
                            if (r.getNomorRekening().equals(noTujuan)) {
                                akunTujuan = r;
                                break;
                            }
                        }
                        
                        if (akunTujuan == null) {
                            System.out.println("Gagal: Nomor rekening tujuan tidak ditemukan!");
                        } else if (akunTujuan == akunAktif) {
                            System.out.println("Gagal: Tidak bisa mentransfer ke rekening sendiri!");
                        } else {
                            System.out.print("Masukkan nominal transfer: ");
                            double nominalTrf = input.nextDouble();
                            input.nextLine();
                            
                            System.out.print("Masukkan PIN Anda: ");
                            String pinTrf = input.nextLine();
                            
                            // Panggil method transfer polimorfik
                            akunAktif.transfer(akunTujuan, nominalTrf, pinTrf);
                        }
                    }
                    break;
                    
                case 0:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima kasih!");
                    break;
                
                default:
                    System.out.println("Pilihan tidak valid!");				
            }
		}
	
		input.close();
	}
}