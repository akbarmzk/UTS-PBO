import java.time.LocalDate;

public class Buku {
    private String judul;
    private boolean tersedia;
    private LocalDate batasKembali;

    public Buku(String judul) {
        this.judul = judul;
        this.tersedia = true;
    }

    public String getJudul() { return judul; }
    public boolean isTersedia() { return tersedia; }

    public void setTersedia(boolean status) {
        tersedia = status;
    }

    public void setBatasKembali(LocalDate t) {
        batasKembali = t;
    }

    public LocalDate getBatasKembali() {
        return batasKembali;
    }
}