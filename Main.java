import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Buku> daftarBuku = new ArrayList<>();
        ArrayList<User> daftarUser = new ArrayList<>();
        Admin admin = new Admin("Admin", "000");

        while (true) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            int pilih = input.nextInt();
            input.nextLine();

            if (pilih == 1) {
                while (true) {
                    System.out.println("\n=== MENU ADMIN ===");
                    System.out.println("1. Tambah Buku");
                    System.out.println("2. Hapus Buku");
                    System.out.println("3. Lihat Buku");
                    System.out.println("4. Kembali");
                    System.out.print("Pilih: ");
                    int a = input.nextInt();
                    input.nextLine();

                    if (a == 1) admin.tambahBuku(daftarBuku, input);
                    else if (a == 2) admin.hapusBuku(daftarBuku, input);
                    else if (a == 3) admin.tampilBuku(daftarBuku);
                    else if (a == 4) break;
                }

            } else if (pilih == 2) {

                User userAktif = null;

                if (!daftarUser.isEmpty()) {
                    System.out.println("\n=== DATA USER ===");
                    for (int i = 0; i < daftarUser.size(); i++) {
                        User u = daftarUser.get(i);
                        System.out.println(i + ". NIM: " + u.getNim() + " | Nama: " + u.getNama());
                    }

                    System.out.print("Pilih user lama atau -1 untuk user baru: ");
                    int pilihUser = input.nextInt();
                    input.nextLine();

                    if (pilihUser >= 0 && pilihUser < daftarUser.size()) {
                        userAktif = daftarUser.get(pilihUser);
                    }
                }

                if (userAktif == null) {
                    System.out.print("Nama: ");
                    String nama = input.nextLine();
                    System.out.print("NIM: ");
                    String nim = input.nextLine();

                    boolean ada = false;
                    for (User u : daftarUser) {
                        if (u.getNim().equals(nim)) {
                            ada = true;
                            break;
                        }
                    }

                    if (ada) {
                        System.out.println("NIM sudah terdaftar!");
                        continue;
                    }

                    userAktif = new User(nama, nim);
                    daftarUser.add(userAktif);
                }

                while (true) {
                    System.out.println("\n=== MENU USER ===");
                    System.out.println("1. Lihat Buku");
                    System.out.println("2. Pinjam Buku");
                    System.out.println("3. Kembalikan Buku");
                    System.out.println("4. Kembali");
                    System.out.print("Pilih: ");
                    int u = input.nextInt();
                    input.nextLine();

                    if (u == 1) userAktif.tampilBuku(daftarBuku);
                    else if (u == 2) userAktif.pinjamBuku(daftarBuku, input);
                    else if (u == 3) userAktif.kembaliBuku(input);
                    else if (u == 4) break;
                }

            } else if (pilih == 3) {
                System.out.println("Terima kasih!");
                break;
            }
        }
    }
}
