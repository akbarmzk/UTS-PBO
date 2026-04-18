import java.util.*;

public class Admin {
    private String nama;
    private String nim;

    // Constructor
    public Admin(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    // Getter (Encapsulation)
    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    // Tambah Buku
    public void tambahBuku(ArrayList<Buku> daftarBuku, Scanner input) {
        System.out.print("Masukkan judul buku: ");
        String judul = input.nextLine();

        daftarBuku.add(new Buku(judul));
        System.out.println("Buku berhasil ditambahkan!");
    }

    // Hapus Buku
    public void hapusBuku(ArrayList<Buku> daftarBuku, Scanner input) {
        if (daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku!");
            return;
        }

        tampilBuku(daftarBuku);

        System.out.print("Pilih index buku yang ingin dihapus: ");
        int index = input.nextInt();
        input.nextLine();

        if (index < 0 || index >= daftarBuku.size()) {
            System.out.println("Index tidak valid!");
            return;
        }

        System.out.println("Buku \"" + daftarBuku.get(index).getJudul() + "\" dihapus.");
        daftarBuku.remove(index);
    }

    // Tampil Buku
    public void tampilBuku(ArrayList<Buku> daftarBuku) {
        if (daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku di perpustakaan.");
            return;
        }

        System.out.println("\n=== DAFTAR BUKU ===");
        for (int i = 0; i < daftarBuku.size(); i++) {
            Buku b = daftarBuku.get(i);

            if (b.isTersedia()) {
                System.out.println(i + ". " + b.getJudul() + " (Tersedia)");
            } else {
                System.out.println(i + ". " + b.getJudul() +
                        " (Dipinjam, kembali: " + b.getBatasKembali() + ")");
            }
        }
    }
}