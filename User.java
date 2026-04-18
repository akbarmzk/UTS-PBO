import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class User {
    private String nama;
    private String nim;
    private int maxPinjam;
    private ArrayList<Buku> bukuDipinjam = new ArrayList<>();

    public User(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;

        int digit = Character.getNumericValue(nim.charAt(nim.length() - 1));
        if (digit <= 3) maxPinjam = 2;
        else if (digit <= 6) maxPinjam = 3;
        else maxPinjam = 5;
    }

    public String getNama() { return nama; }
    public String getNim() { return nim; }

    public void tampilJatah() {
        System.out.println("\n=== INFO PEMINJAMAN ===");
        System.out.println("Maks Pinjam       : " + maxPinjam);
        System.out.println("Sedang Dipinjam   : " + bukuDipinjam.size());
        System.out.println("Sisa Jatah        : " + (maxPinjam - bukuDipinjam.size()));
    }

    public void tampilBuku(ArrayList<Buku> daftarBuku) {
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

    public void pinjamBuku(ArrayList<Buku> daftarBuku, Scanner input) {
        tampilJatah();

        if (bukuDipinjam.size() >= maxPinjam) {
            System.out.println("Maaf, jatah peminjaman buku telah habis");
            return;
        }

        tampilBuku(daftarBuku);
        System.out.print("Masukkan index (contoh: 1 3): ");
        String[] pilihan = input.nextLine().split(" ");

        if (bukuDipinjam.size() + pilihan.length > maxPinjam) {
            System.out.println("Melebihi batas maksimal!");
            return;
        }

        for (String p : pilihan) {
            try {
                int index = Integer.parseInt(p);

                if (index < 0 || index >= daftarBuku.size()) {
                    System.out.println("Index tidak valid!");
                    continue;
                }

                Buku b = daftarBuku.get(index);

                if (!b.isTersedia()) {
                    System.out.println("Buku sedang dipinjam!");
                    continue;
                }

                b.setTersedia(false);
                b.setBatasKembali(LocalDate.now().plusDays(7));
                bukuDipinjam.add(b);

                System.out.println("Berhasil pinjam: " + b.getJudul());

            } catch (Exception e) {
                System.out.println("Input salah!");
            }
        }
    }

    public void kembaliBuku(Scanner input) {
        if (bukuDipinjam.isEmpty()) {
            System.out.println("Tidak ada buku yang dipinjam");
            return;
        }

        System.out.println("\n=== BUKU DIPINJAM ===");
        for (int i = 0; i < bukuDipinjam.size(); i++) {
            System.out.println(i + ". " + bukuDipinjam.get(i).getJudul());
        }

        System.out.print("Berapa buku yang ingin dikembalikan: ");
        int jumlah = input.nextInt();
        input.nextLine();

        if (jumlah <= 0 || jumlah > bukuDipinjam.size()) {
            System.out.println("Jumlah tidak valid!");
            return;
        }

        for (int i = 0; i < jumlah; i++) {
            System.out.print("Pilih index buku: ");
            int index = input.nextInt();
            input.nextLine();

            if (index < 0 || index >= bukuDipinjam.size()) {
                System.out.println("Index tidak valid!");
                i--;
                continue;
            }

            Buku b = bukuDipinjam.get(index);

            long telat = ChronoUnit.DAYS.between(b.getBatasKembali(), LocalDate.now());
            if (telat > 0) {
                System.out.println("Denda: Rp " + (telat * 1000));
            }

            b.setTersedia(true);
            bukuDipinjam.remove(index);

            System.out.println("Buku dikembalikan");
        }
    }
}