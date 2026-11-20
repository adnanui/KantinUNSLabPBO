# Kasir Kantin UNS

Praktikum PBO Kelas 3B, Pertemuan 11 (Multithreading) dan Pertemuan 12 (GUI Programming)

## Gambaran Umum

Kasir Kantin UNS adalah aplikasi kasir sederhana berbasis jendela yang dibuat dengan Java Swing. Kasir memasukkan nama menu, harga, dan jumlah, lalu aplikasi menghitung total bayar dan memberi diskon bila belanja cukup besar. Selain itu, aplikasi ini memuat simulasi dua kasir yang melayani pelanggan secara bersamaan dan fitur cetak struk yang tidak membuat jendela macet. Seluruh program berada dalam satu package bernama lab.kasir dan terdiri dari empat class, yaitu Pesanan, Kasir, MainKasir, dan KasirFrame. Pengerjaannya dibagi menjadi empat tahap yang saling berurutan, dari logika perhitungan, thread, tampilan GUI, sampai penggabungan GUI dengan thread.

## Tahap 1: Class Pesanan (Logika)

Class Pesanan menyimpan seluruh logika perhitungan belanja dan sengaja dipisahkan dari tampilan. Class ini hanya memiliki satu atribut, yaitu subtotal bertipe double yang dideklarasikan private. Dengan begitu nilai subtotal tidak bisa diubah sembarangan dari luar class, dan hanya bisa diakses lewat method yang disediakan. Inilah penerapan konsep enkapsulasi.

Method tambahItem menerima harga dan jumlah, lalu menambahkan hasil perkaliannya ke subtotal. Method getSubtotal mengembalikan nilai subtotal saat ini. Method hitungTotal memeriksa subtotal. Bila subtotal lebih dari 50.000, method ini mengembalikan subtotal dikurangi diskon 10 persen, dan bila tidak, subtotal dikembalikan apa adanya. Method reset mengembalikan subtotal ke nol sehingga pesanan baru bisa dimulai.

Pengujian dilakukan di class MainKasir dengan tiga menu, yaitu Nasi Goreng seharga 15.000 sebanyak 2, Es Teh seharga 5.000 sebanyak 3, dan Ayam Bakar seharga 25.000 sebanyak 1. Setelah dua menu pertama, subtotal bernilai 45.000 dan total juga 45.000 karena belum mencapai batas diskon. Setelah menu ketiga ditambahkan, subtotal menjadi 70.000 sehingga melewati batas 50.000 dan total menjadi 63.000 setelah dipotong diskon 7.000. Hasil ini sesuai dengan hasil yang diharapkan pada soal. Method reset juga diuji dan membuat subtotal kembali ke nol.

## Tahap 2: Dua Kasir sebagai Thread

Pada tahap ini class Kasir dibuat dengan mewarisi class Thread, sehingga setiap objek Kasir adalah satu thread yang berjalan sendiri. Class ini memiliki atribut nama yang bersifat private dan diisi lewat constructor. Pekerjaan thread ditulis di dalam method run. Di dalamnya terdapat perulangan dari pelanggan pertama sampai kelima. Pada setiap putaran, kasir mencetak kalimat dengan format nama kasir melayani pelanggan ke sekian, lalu thread tidur selama 500 milidetik dengan sleep untuk mensimulasikan waktu pelayanan. Pemanggilan sleep wajib dibungkus try dan catch karena dapat melempar InterruptedException.

Di MainKasir dibuat dua objek Kasir bernama Dina dan Eko. Keduanya dijalankan dengan start, bukan dengan memanggil run secara langsung. Pemanggilan start membuat thread baru yang kemudian mengeksekusi run, sedangkan memanggil run langsung hanya menjalankannya seperti method biasa di thread utama sehingga tidak ada kerja paralel. Karena kedua thread berjalan bersamaan, keluaran Dina dan Eko muncul bergantian, dan urutan siapa yang tampil lebih dulu bisa berbeda di setiap kali program dijalankan. Hal ini terjadi karena urutan eksekusi thread ditentukan oleh penjadwal sistem operasi dan Java Virtual Machine, bukan oleh urutan penulisan kode. Dalam pengujian yang dilakukan dua kali, urutan keluaran memang tidak selalu sama.

Agar program utama menunggu kedua kasir selesai, dipakai method join pada kedua thread. Method join membuat thread main berhenti sampai thread yang dipanggil menyelesaikan run. Setelah keduanya selesai, barulah main mencetak kalimat bahwa semua pelanggan selesai dilayani. Tanpa join, kalimat tersebut bisa muncul lebih dulu, bahkan sebelum kasir selesai bekerja, karena main tidak menunggu. Dengan join, kalimat itu selalu muncul paling akhir, sesuai hasil yang diharapkan.

## Tahap 3: Tampilan GUI Kasir

Class KasirFrame mewarisi JFrame sehingga menjadi jendela utama aplikasi. Jendela berukuran 400 kali 450 piksel dan memakai layout null, artinya posisi dan ukuran setiap komponen diatur secara manual dengan setBounds. Semua komponen berasal dari package javax.swing. Komponen yang dibuat meliputi tiga label dan tiga kolom input JTextField untuk nama menu, harga, dan jumlah, tombol Tambah dan Hitung Total, area struk berupa JTextArea yang dibungkus JScrollPane agar dapat digulir, serta sebuah label untuk menampilkan total bayar. Area struk dibuat tidak bisa diedit karena hanya berfungsi menampilkan catatan belanja. Semua komponen ditambahkan ke jendela dengan add, dan jendela baru ditampilkan dengan setVisible(true) setelah semua komponen terpasang. Pengaturan setDefaultCloseOperation dengan EXIT_ON_CLOSE membuat program berhenti sepenuhnya ketika jendela ditutup.

Atribut seperti kolom input, area struk, label total, dan objek Pesanan dideklarasikan private sesuai prinsip enkapsulasi. KasirFrame hanya berperan sebagai tampilan, sedangkan perhitungan diserahkan kepada objek Pesanan dari Tahap 1. Pemisahan ini sesuai tujuan GUI yang dijelaskan di materi, yaitu memisahkan logika bisnis dari antarmuka.

Interaksi pengguna ditangani lewat event dan listener. Setiap tombol diberi ActionListener dengan addActionListener, dan isi listener ditulis sebagai lambda. Ketika tombol diklik, kode di dalam listener dijalankan. Pada tombol Tambah, program mengambil isi ketiga kolom, memastikan tidak ada yang kosong, lalu mengubah harga menjadi angka desimal dan jumlah menjadi bilangan bulat. Bila berhasil, program memanggil tambahItem pada objek Pesanan, menulis satu baris ke area struk berisi nama menu, harga, jumlah, dan hasil kali dalam format rupiah, lalu mengosongkan kolom input supaya siap untuk menu berikutnya. Pada tombol Hitung Total, program memanggil hitungTotal dan menampilkan hasilnya pada label total dengan pemisah ribuan, misalnya Rp 63.000.

Validasi dilakukan dalam dua lapis. Lapis pertama memeriksa apakah ada kolom yang kosong, dan bila ada, ditampilkan pesan lewat JOptionPane.showMessageDialog. Lapis kedua memakai try dan catch untuk menangkap NumberFormatException, yaitu galat yang muncul ketika harga atau jumlah bukan angka, misalnya berisi huruf, dan program menampilkan pesan bahwa isian harus berupa angka. Program juga menolak harga atau jumlah yang bernilai nol atau negatif. Pada kedua kasus gagal ini, data tidak dimasukkan ke pesanan sehingga total tetap benar. Dengan data uji Tahap 1, label total menampilkan 63.000 setelah ketiga menu ditambahkan.

## Tahap 4: Tombol Cetak Struk dengan Thread

Pada tahap ini ditambahkan tombol Cetak Struk dan sebuah label status di bagian bawah jendela. Saat tombol ditekan, label menampilkan tulisan Mencetak struk, lalu setelah dua detik berubah menjadi Struk selesai. Penantian dua detik ini mensimulasikan proses mencetak yang memakan waktu.

Bagian terpenting dari tahap ini adalah penantian tersebut dikerjakan di thread baru. Aplikasi Swing menjalankan seluruh penanganan event dan penggambaran tampilan di satu thread khusus. Bila sleep dua detik dipanggil langsung di dalam listener, thread khusus itu ikut tertidur sehingga jendela tidak merespons dan tampak macet. Dengan membuat thread baru di dalam listener lalu memulainya dengan start, proses menunggu berjalan terpisah, dan thread tampilan tetap bebas melayani klik pengguna. Manfaat ini sama dengan yang dijelaskan di materi multithreading, yaitu antarmuka tetap responsif saat ada proses yang memakan waktu.

Hasilnya dapat diamati langsung. Selama dua detik penantian, tombol Tambah dan Hitung Total masih bisa ditekan dan memberi respons seperti biasa. Label status berubah dari Mencetak struk menjadi Struk selesai tepat setelah waktu tunggu berakhir. Perlu dicatat bahwa pengubahan teks label dari thread baru mengikuti kerangka yang diberikan pada soal. Pendekatan yang lebih ketat untuk aplikasi besar adalah memakai SwingUtilities.invokeLater agar perubahan tampilan dilakukan di thread tampilan, tetapi teknik itu tidak dibahas di materi sehingga tidak dipakai di sini.

## Kaitan dengan Konsep OOP

Kelas dan objek tampak pada Pesanan, Kasir, dan KasirFrame, dan pada komponen Swing seperti JFrame, JButton, dan JTextField, di mana setiap tombol, label, dan kolom adalah objek tersendiri. Enkapsulasi diterapkan pada semua atribut yang bersifat private dan hanya diakses lewat method. Pewarisan dipakai ketika Kasir mewarisi Thread dan KasirFrame mewarisi JFrame, sehingga keduanya otomatis memiliki kemampuan class induknya. Polimorfisme terlihat pada method run yang di-override di Kasir dan pada addActionListener, di mana setiap tombol memberikan perilaku yang berbeda terhadap peristiwa klik yang sama.

## Cara Menjalankan

Seluruh file berada di folder lab/kasir dan memakai package lab.kasir. Untuk menguji Tahap 1 dan Tahap 2, jalankan class MainKasir, dan hasilnya tampil sebagai teks di terminal. Untuk Tahap 3 dan Tahap 4, jalankan class KasirFrame, dan jendela aplikasi kasir akan muncul. Kedua class memiliki method main sendiri sehingga dapat dijalankan secara terpisah. Berkas MainKasir menjadi tempat bukti untuk tangkapan layar Tahap 1 dan 2, sedangkan KasirFrame menjadi tempat bukti untuk tangkapan layar Tahap 3 dan 4.
