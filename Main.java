import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

// =======================
// CLASS BUKU
// =======================
class Buku {
    private String judul;
    private boolean tersedia;
    private LocalDate batasKembali;

    public Buku(String judul) {
        this.judul = judul;
        this.tersedia = true;
    }

    public String getJudul() {
        return judul;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public LocalDate getBatasKembali() {
        return batasKembali;
    }

    public void pinjam(LocalDate batasKembali) {
        tersedia = false;
        this.batasKembali = batasKembali;
    }

    public void kembali() {
        tersedia = true;
        batasKembali = null;
    }
}

// =======================
// CLASS USER
// =======================
class User {
    protected String nama;
    protected String nim;

    public User(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    public int getMaxPinjam() {
        char last = nim.charAt(nim.length() - 1);

        if (last >= '0' && last <= '3') return 2;
        else if (last >= '4' && last <= '6') return 3;
        else return 5;
    }
}

// =======================
// CLASS ANGGOTA
// =======================
class Anggota extends User {
    private ArrayList<Transaksi> pinjaman = new ArrayList<>();

    public Anggota(String nama, String nim) {
        super(nama, nim);
    }

    public void pinjamBuku(Buku buku) {
        if (pinjaman.size() >= getMaxPinjam()) {
            System.out.println(" Melebihi batas pinjam!");
            return;
        }

        if (buku.isTersedia()) {
            Transaksi t = new Transaksi(buku);
            buku.pinjam(t.getBatasKembali());
            pinjaman.add(t);
            System.out.println(" Buku berhasil dipinjam");
        } else {
            System.out.println(" Buku tidak tersedia");
        }
    }

    public void kembaliBuku(int index) {
        if (index < 0 || index >= pinjaman.size()) {
            System.out.println(" Index tidak valid!");
            return;
        }

        Transaksi t = pinjaman.get(index);
        t.getBuku().kembali();

        long denda = t.hitungDenda();
        System.out.println(" Denda: Rp" + denda);

        pinjaman.remove(index);
    }

    public void lihatPinjaman() {
        if (pinjaman.isEmpty()) {
            System.out.println(" Tidak ada buku dipinjam");
            return;
        }

        for (int i = 0; i < pinjaman.size(); i++) {
            Transaksi t = pinjaman.get(i);
            System.out.println(i + ". " + t.getBuku().getJudul() +
                    " (Kembali: " + t.getBatasKembali() + ")");
        }
    }
}

// =======================
// CLASS TRANSAKSI
// =======================
class Transaksi {
    private Buku buku;
    private LocalDate tanggalPinjam;
    private LocalDate batasKembali;

    public Transaksi(Buku buku) {
        this.buku = buku;
        this.tanggalPinjam = LocalDate.now();
        this.batasKembali = tanggalPinjam.plusDays(7);
    }

    public Buku getBuku() {
        return buku;
    }

    public LocalDate getBatasKembali() {
        return batasKembali;
    }

    public long hitungDenda() {
        LocalDate sekarang = LocalDate.now();

        if (sekarang.isAfter(batasKembali)) {
            long telat = ChronoUnit.DAYS.between(batasKembali, sekarang);
            return telat * 1000;
        }
        return 0;
    }
}

// =======================
// CLASS PERPUSTAKAAN
// =======================
class Perpustakaan {
    private ArrayList<Buku> daftarBuku = new ArrayList<>();

    public void tambahBuku(String judul) {
        daftarBuku.add(new Buku(judul));
    }

    public void hapusBuku(int index) {
        if (index >= 0 && index < daftarBuku.size()) {
            daftarBuku.remove(index);
            System.out.println(" Buku berhasil dihapus!");
        } else {
            System.out.println(" Index tidak valid!");
        }
    }

    public void tampilBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println(" Belum ada buku");
            return;
        }

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

    public Buku getBuku(int index) {
        return daftarBuku.get(index);
    }
}

// =======================
// MAIN PROGRAM
// =======================
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Perpustakaan perpus = new Perpustakaan();

        while (true) {
            System.out.println("\n=================================");
            System.out.println(" SISTEM PERPUSTAKAAN MINI ");
            System.out.println("=================================");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Keluar");
            System.out.println("=================================");
            System.out.print("Pilih menu: ");
            int pilih = input.nextInt();

            if (pilih == 1) {
                while (true) {
                    System.out.println("\n========== MENU ADMIN ==========");
                    System.out.println("1. Tambah Buku");
                    System.out.println("2. Hapus Buku");
                    System.out.println("3. Lihat Daftar Buku");
                    System.out.println("4. Kembali");
                    System.out.println("================================");
                    System.out.print("Pilih: ");

                    int a = input.nextInt();
                    input.nextLine();

                    if (a == 1) {
                        System.out.println("\n--- Tambah Buku ---");
                        System.out.print("Masukkan Judul Buku: ");
                        String j = input.nextLine();
                        perpus.tambahBuku(j);
                        System.out.println(" Buku berhasil ditambahkan!");
                    } else if (a == 2) {
                        System.out.println("\n--- Hapus Buku ---");
                        perpus.tampilBuku();
                        System.out.print("Masukkan Index Buku: ");
                        int i = input.nextInt();
                        perpus.hapusBuku(i);
                    } else if (a == 3) {
                        System.out.println("\n--- Daftar Buku ---");
                        perpus.tampilBuku();
                    } else break;
                }

            } else if (pilih == 2) {
                input.nextLine();
                System.out.println("\n========== LOGIN USER ==========");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("NIM: ");
                String nim = input.nextLine();

                Anggota user = new Anggota(nama, nim);

                while (true) {
                    System.out.println("\n========== MENU USER ==========");
                    System.out.println("Halo, " + nama + " ");
                    System.out.println("Max Pinjam: " + user.getMaxPinjam() + " buku");
                    System.out.println("--------------------------------");
                    System.out.println("1. Lihat Buku");
                    System.out.println("2. Pinjam Buku");
                    System.out.println("3. Kembalikan Buku");
                    System.out.println("4. Keluar");
                    System.out.println("================================");
                    System.out.print("Pilih: ");

                    int u = input.nextInt();

                    if (u == 1) {
                        System.out.println("\n--- Daftar Buku ---");
                        perpus.tampilBuku();
                    } else if (u == 2) {
                        System.out.println("\n--- Pinjam Buku ---");
                        perpus.tampilBuku();
                        System.out.print("Pilih Index Buku: ");
                        int i = input.nextInt();
                        user.pinjamBuku(perpus.getBuku(i));
                    } else if (u == 3) {
                        System.out.println("\n--- Buku Dipinjam ---");
                        user.lihatPinjaman();
                        System.out.print("Pilih Index Buku: ");
                        int i = input.nextInt();
                        user.kembaliBuku(i);
                    } else break;
                }

            } else {
                System.out.println("\nTerima kasih telah menggunakan sistem ");
                break;
            }
        }
    }
}