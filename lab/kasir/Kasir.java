package lab.kasir;

/**
 * Tahap 2: Dua Kasir sebagai Thread
 * Setiap kasir adalah satu thread yang melayani 5 pelanggan.
 */
class Kasir extends Thread {

    private String nama;

    Kasir(String nama) {
        this.nama = nama;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            // mencetak nama kasir dan nomor pelanggan (i)
            System.out.println("Kasir " + nama + " melayani pelanggan ke-" + i);
            try {
                Thread.sleep(500); // jeda 0,5 detik
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
