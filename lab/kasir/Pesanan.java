package lab.kasir;

/**
 * Tahap 1: Class Logika
 * Menyimpan subtotal pesanan dan menghitung total bayar.
 */
public class Pesanan {

    // atribut private (enkapsulasi)
    private double subtotal;

    // menambahkan harga x jumlah ke subtotal
    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    // membaca nilai subtotal
    public double getSubtotal() {
        return subtotal;
    }

    // jika subtotal > 50.000 maka diskon 10 persen, jika tidak subtotal apa adanya
    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal - (subtotal * 0.10);
        } else {
            return subtotal;
        }
    }

    // mengembalikan subtotal ke 0
    public void reset() {
        subtotal = 0;
    }
}
