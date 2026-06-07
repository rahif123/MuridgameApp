# MuridgameApp 🎮

MuridgameApp adalah aplikasi Android modern yang berfungsi sebagai pembaca berita dan ulasan seputar dunia gaming (periferal, konsol, audio, dll). Aplikasi ini terintegrasi secara dinamis dengan CMS WordPress melalui REST API untuk menyajikan konten secara real-time.

## 🚀 Fitur Utama

- **Sinkronisasi WordPress API**: Mengambil konten artikel, kategori, dan media secara langsung dari server.
- **Navigasi Berbasis Kategori**: Filter artikel berdasarkan kategori khusus (Mouse, Keyboard, Console, Audio) melalui Sidebar.
- **Pencarian Cepat (Live Search)**: Mencari artikel berdasarkan judul secara instan di layar utama.
- **In-App WebView**: Membaca artikel lengkap tanpa meninggalkan aplikasi, mendukung rendering HTML yang kompleks.
- **Dukungan Link Affiliate**: Penanganan cerdas untuk link belanja (Shopee, Tokopedia, dll) yang secara otomatis mendeteksi dan membuka aplikasi marketplace terkait.
- **Modern Gesture Support**: Implementasi `OnBackPressedDispatcher` untuk dukungan navigasi gestur Android terbaru.
- **UI/UX Responsif**: Antarmuka Material Design 3 dengan dukungan *Swipe-to-Refresh*.

## 🏗️ Arsitektur Proyek

Aplikasi ini menggunakan pola **Separation of Concerns** dengan pendekatan yang mengarah pada **MVVM (Model-View-ViewModel)** untuk memastikan kode mudah dipelihara dan diuji.

### Alur Data:
1. **Remote Data Source**: `RetrofitClient` melakukan request ke WordPress API.
2. **Model Layer**: Data JSON diparsing menjadi objek Java menggunakan `Gson` melalui kelas `Post`.
3. **Controller/View Layer**: `MainActivity` mengelola siklus hidup UI dan interaksi pengguna.
4. **Presentation Layer**: `PostAdapter` menghubungkan data model dengan elemen visual `RecyclerView`.

## 🛠️ Tech Stack & Library

Daftar teknologi utama yang digunakan dalam pengembangan aplikasi ini:

| Library | Fungsi |
|-----------|-----------|
| **Retrofit 2** | Network client untuk menangani request HTTP ke API WordPress. |
| **Gson** | Converter untuk mengubah JSON menjadi objek POJO Java. |
| **Glide** | Library pemrosesan dan loading gambar dari URL secara asinkron. |
| **SwipeRefreshLayout** | Implementasi fitur tarik-untuk-memperbarui data. |
| **RecyclerView** | Menampilkan daftar artikel dalam jumlah besar secara efisien. |
| **Material Components** | Library komponen UI modern dari Google (Material 3). |

## 📂 Struktur Package

```text
com.example.muridgameapp
├── MainActivity.java      # Layar utama (List artikel, Search, Drawer)
├── DetailActivity.java    # Layar detail artikel (WebView & Affiliate handling)
├── PostAdapter.java       # Jembatan antara data artikel dan UI List
├── Post.java              # Model data (Mapping JSON dari API)
├── ApiService.java        # Definisi endpoint REST API WordPress
├── RetrofitClient.java    # Konfigurasi Singleton untuk library Retrofit
```

## ⚙️ Prasyarat Instalasi

Sebelum menjalankan proyek ini, pastikan lingkungan pengembangan Anda memenuhi kriteria berikut:

- **Android Studio** (Versi Ladybug atau lebih baru direkomendasikan).
- **Android SDK** minimal level 24 (Android 7.0 Nougat).
- **Gradle** versi 8.0+.
- Koneksi Internet (untuk sinkronisasi API).

## 🚀 Cara Menjalankan Proyek

1. **Clone Repository**:
   ```bash
   git clone https://github.com/username/MuridgameApp.git
   ```
2. **Import Project**: Buka Android Studio, pilih `Open` dan arahkan ke folder proyek ini.
3. **Sync Gradle**: Biarkan Android Studio mengunduh semua dependensi yang diperlukan.
4. **Run**: Klik tombol `Run` (Segitiga Hijau) untuk menginstal aplikasi di Emulator atau perangkat fisik.

## 📝 Konfigurasi API

Jika Anda ingin mengganti sumber data ke website WordPress Anda sendiri, ubah konstanta URL di `RetrofitClient.java`:

```java
public static final String BASE_URL = "https://domain-anda.com/";
```

---
© 2024 Muridgame Dev Team. All Rights Reserved.
