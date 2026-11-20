package lab.kasir;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;

/**
 * Tahap 3: Tampilan GUI Kasir (Java Swing)
 * Tahap 4: Tombol Cetak Struk dengan Thread
 */
public class KasirFrame extends JFrame {

    private JTextField txtMenu, txtHarga, txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private Pesanan pesanan = new Pesanan();

    // tambahan untuk Tahap 4
    private JLabel lblStatus;

    // format angka ribuan, contoh: 63000 menjadi 63.000
    private DecimalFormat formatRupiah;

    public KasirFrame() {
        setTitle("Kasir Kantin UNS");
        setSize(400, 450);
        setLayout(null);

        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        formatRupiah = new DecimalFormat("#,##0", simbol);

        // ===================== TAHAP 3: Komponen =====================
        JLabel lblMenu = new JLabel("Nama Menu :");
        lblMenu.setBounds(20, 20, 90, 25);
        txtMenu = new JTextField();
        txtMenu.setBounds(110, 20, 250, 25);

        JLabel lblHarga = new JLabel("Harga :");
        lblHarga.setBounds(20, 55, 90, 25);
        txtHarga = new JTextField();
        txtHarga.setBounds(110, 55, 250, 25);

        JLabel lblJumlah = new JLabel("Jumlah :");
        lblJumlah.setBounds(20, 90, 90, 25);
        txtJumlah = new JTextField();
        txtJumlah.setBounds(110, 90, 250, 25);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(60, 130, 110, 30);
        JButton btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(200, 130, 130, 30);

        // area struk (JTextArea dibungkus JScrollPane agar bisa di-scroll)
        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        scrollStruk.setBounds(20, 175, 345, 120);

        lblTotal = new JLabel("Total Bayar : Rp 0");
        lblTotal.setBounds(20, 305, 345, 25);

        // ===================== TAHAP 4: Tombol Cetak Struk dan label status =====================
        JButton btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 340, 120, 30);
        lblStatus = new JLabel("Siap");
        lblStatus.setBounds(20, 380, 345, 25);

        // menambahkan semua komponen ke jendela
        add(lblMenu);
        add(txtMenu);
        add(lblHarga);
        add(txtHarga);
        add(lblJumlah);
        add(txtJumlah);
        add(btnTambah);
        add(btnHitung);
        add(scrollStruk);
        add(lblTotal);
        add(btnCetak);
        add(lblStatus);

        // ===================== Tombol Tambah =====================
        btnTambah.addActionListener(e -> {
            String menu = txtMenu.getText().trim();
            String hargaTeks = txtHarga.getText().trim();
            String jumlahTeks = txtJumlah.getText().trim();

            // validasi: field tidak boleh kosong
            if (menu.isEmpty() || hargaTeks.isEmpty() || jumlahTeks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            // validasi: harga dan jumlah harus angka
            try {
                double harga = Double.parseDouble(hargaTeks);
                int jumlah = Integer.parseInt(jumlahTeks);

                if (harga <= 0 || jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, "Harga dan jumlah harus lebih dari 0!");
                    return;
                }

                pesanan.tambahItem(harga, jumlah);

                // tulis satu baris ke area struk
                areaStruk.append(menu + " | " + formatRupiah.format(harga) + " x " + jumlah
                        + " = Rp " + formatRupiah.format(harga * jumlah) + "\n");

                // kosongkan field
                txtMenu.setText("");
                txtHarga.setText("");
                txtJumlah.setText("");
                txtMenu.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan jumlah harus berupa angka!");
            }
        });

        // ===================== Tombol Hitung Total =====================
        btnHitung.addActionListener(e -> {
            lblTotal.setText("Total Bayar : Rp " + formatRupiah.format(pesanan.hitungTotal()));
        });

        // ===================== Tombol Cetak Struk (pakai thread) =====================
        btnCetak.addActionListener(e -> {
            new Thread(() -> {
                lblStatus.setText("Mencetak struk...");
                try {
                    Thread.sleep(2000); // menunggu 2 detik di thread baru, jendela tidak macet
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                lblStatus.setText("Struk selesai");
            }).start();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // menjalankan GUI (klik kanan file ini > Run File di NetBeans)
    public static void main(String[] args) {
        new KasirFrame();
    }
}
