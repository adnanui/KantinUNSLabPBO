package lab.kasir;

/**
 * Class utama untuk menguji Tahap 1 (class Pesanan) dan Tahap 2 (thread Kasir).
 * Tahap 3 dan 4 (GUI) dijalankan lewat class KasirFrame.
 */
public class MainKasir {

    public static void main(String[] args) {

        // ===================== TAHAP 1 : Uji class Pesanan =====================
        System.out.println("=== TAHAP 1: Uji Class Pesanan ===");
        Pesanan pesanan = new Pesanan();

        // Nasi Goreng 15.000 x 2
        pesanan.tambahItem(15000, 2);
        // Es Teh 5.000 x 3
        pesanan.tambahItem(5000, 3);
        System.out.println("Setelah Nasi Goreng dan Es Teh");
        System.out.println("Subtotal : " + pesanan.getSubtotal());
        System.out.println("Total    : " + pesanan.hitungTotal());

        // Ayam Bakar 25.000 x 1
        pesanan.tambahItem(25000, 1);
        System.out.println("Setelah Ayam Bakar");
        System.out.println("Subtotal : " + pesanan.getSubtotal());
        System.out.println("Total    : " + pesanan.hitungTotal());

        // menguji reset()
        pesanan.reset();
        System.out.println("Setelah reset, subtotal : " + pesanan.getSubtotal());

        // ===================== TAHAP 2 : Dua Kasir sebagai Thread =====================
        System.out.println();
        System.out.println("=== TAHAP 2: Dua Kasir sebagai Thread ===");

        // membuat dua objek kasir (thread)
        Kasir dina = new Kasir("Dina");
        Kasir eko = new Kasir("Eko");

        // memulai thread (akan menjalankan run())
        dina.start();
        eko.start();

        // join() : main menunggu kedua kasir selesai
        try {
            dina.join();
            eko.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Semua pelanggan selesai dilayani");
    }
}
