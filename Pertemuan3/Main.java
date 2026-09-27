package Pertemuan3;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
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
						input.nextLine(); // Membersihkan buffer scanner setelah nextDouble()
						
						if (saldo >= 50000) {
							break;
						} else {
							System.out.println("Nominal saldo awal harus minimal 50.000!");
						}
					}
					
					// [TUGAS POIN 1] Meminta input PIN sebelum menginstansiasi objek
					System.out.print("Masukkan PIN (6 digit): ");
					String pin = input.nextLine();
					
					// Menginstansiasi objek Rekening baru dengan PIN
					akunAktif = new Rekening(no, nama, saldo, pin);
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
				
				case 3: // [TUGAS POIN 2] Otentikasi PIN pada Tarik Tunai
					if (akunAktif == null) {
						System.out.println("Error: Anda belum membuka rekening!");
					} else {
						System.out.print("Masukkan PIN Anda: ");
						String pinInput = input.nextLine();
						
						// Memanggil akunAktif.otentikasi(pinYangDiinput)
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

				case 6: // [TUGAS POIN 2] Otentikasi PIN pada Cetak Mutasi
					if (akunAktif == null) {
						System.out.println("Error: Anda belum membuka rekening!");
					} else {
						System.out.print("Masukkan PIN Anda: ");
						String pinInput = input.nextLine();
						
						// Memanggil akunAktif.otentikasi(pinYangDiinput)
						if (akunAktif.otentikasi(pinInput)) {
							akunAktif.cetakMutasi();
						} else {
							System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
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