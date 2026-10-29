# Catatan Tugas Kuliah

Aplikasi Android sederhana untuk membantu mahasiswa mencatat dan memantau tugas kuliah. Aplikasi ini dibuat menggunakan **Kotlin**, **Jetpack Compose**, dan pola arsitektur **MVVM**, dengan pengelolaan UI State menggunakan **StateFlow** serta alur **Unidirectional Data Flow (UDF)**.

## Anggota Kelompok

| No. | Nama | NIM |
|---|---|---|
| 1 | Niko Aditya Alansyah | 24523006 |
| 2 | Dzakki Naufal Hanif | 24523199 |

## Latar Belakang Masalah

Mahasiswa menerima tugas dari berbagai mata kuliah. Tanpa pencatatan yang teratur, tugas dapat tersebar di berbagai tempat dan sulit dipantau status penyelesaiannya.

## Pengguna Sasaran

Mahasiswa yang membutuhkan cara sederhana untuk mencatat tugas kuliah dan mengetahui tugas yang sudah atau belum selesai.

## Solusi

**Catatan Tugas Kuliah** menyediakan satu layar untuk menambahkan tugas beserta nama mata kuliah, menandai tugas yang telah selesai, dan menghapus tugas yang tidak lagi diperlukan.

## Fitur Utama

1. **Tambah tugas** — pengguna memasukkan judul tugas dan mata kuliah. Kedua kolom divalidasi agar tidak kosong.
2. **Tandai selesai/belum selesai** — pengguna dapat mengubah status tugas melalui checkbox. Judul tugas yang selesai ditampilkan dengan garis coret.
3. **Hapus tugas** — pengguna menekan ikon hapus pada kartu tugas untuk menghapus tugas tersebut dari daftar.

Data pada versi ini dirancang disimpan di memori. Login, API, database, dan notifikasi tidak termasuk cakupan minimum tugas.

## Teknologi dan Konsep

- **Kotlin** sebagai bahasa pemrograman.
- **Jetpack Compose** dan **Material 3** untuk antarmuka.
- **MVVM** untuk memisahkan model data, tampilan, dan logika/state layar.
- **StateFlow** untuk memublikasikan UI State dari `TaskViewModel` ke UI.
- **`collectAsStateWithLifecycle()`** untuk mengumpulkan state pada layar Compose mengikuti lifecycle.
- **UDF (Unidirectional Data Flow)**: event pengguna dikirim ke ViewModel, kemudian UI merender state terbaru.
- **Adaptive layout**: form dan daftar tugas ditumpuk pada layar sempit serta ditampilkan berdampingan pada layar lebar.

## Struktur Kode

| File | Tanggung jawab |
|---|---|
| `Task.kt` | Model data tugas: ID, judul, mata kuliah, dan status selesai. |
| `TaskUiState.kt` | Menyimpan state layar, input, pesan validasi, dan kondisi daftar kosong. |
| `TaskViewModel.kt` | Mengelola state layar dan logika tambah, ubah status, serta hapus tugas melalui `MutableStateFlow` privat dan `StateFlow` read-only. |
| `TaskScreen.kt` | Menampilkan UI Compose, form input, daftar tugas, tombol hapus, dan meneruskan event ke ViewModel. |
| `TaskRepository.kt` | Kelas pengelola daftar tugas berbasis memori. Pada source saat ini, repository belum terhubung ke ViewModel. |
| `MainActivity.kt` | Titik masuk aplikasi dan pemanggil `TaskScreen()`. |

## Contoh Alur UDF

Contoh: pengguna menambahkan tugas baru.

1. Pengguna mengetik judul tugas dan mata kuliah pada form.
2. `TaskForm` meneruskan perubahan input melalui callback `onTitleChange` dan `onCourseChange`.
3. `TaskViewModel` memperbarui `TaskUiState` melalui `_uiState`.
4. Saat tombol **Tambah Tugas** ditekan, `addTask()` memvalidasi input.
5. Jika input tidak valid, ViewModel memperbarui pesan error pada `TaskUiState` dan UI menampilkannya.
6. Jika input valid, tugas baru ditambahkan ke state dan input dikosongkan.
7. `StateFlow` mengirim state terbaru; `collectAsStateWithLifecycle()` membuat Compose menampilkan daftar tugas yang diperbarui.

```text
Aksi pengguna
    ↓
Composable / callback
    ↓
TaskViewModel memproses event
    ↓
TaskUiState diperbarui melalui StateFlow
    ↓
Compose merender UI terbaru
```

Untuk menghapus tugas, pengguna menekan ikon hapus pada kartu tugas. `TaskScreen` meneruskan ID tugas melalui callback ke `TaskViewModel.deleteTask()`, yang mengeluarkan tugas tersebut dari `TaskUiState`. Compose kemudian memperbarui daftar; jika tugas terakhir dihapus, pesan bahwa daftar masih kosong akan ditampilkan.

## Cara Menjalankan

1. Pastikan Android Studio dan Android SDK yang sesuai telah terpasang.
2. Buka folder project ini di Android Studio.
3. Tunggu proses Gradle Sync selesai.
4. Jalankan aplikasi pada emulator Android atau perangkat yang terhubung.
5. Jika diperlukan, sesuaikan konfigurasi SDK berdasarkan lingkungan pengembangan masing-masing.

## Screenshot Aplikasi

Tambahkan screenshot aktual ke repository dan perbarui path gambar berikut.

### Tampilan layar sempit (ponsel)

![Tampilan aplikasi pada layar ponsel](<Screenshot 2026-10-29 111810.png>)

### Tampilan layar lebar (tablet atau emulator lebar)

![Tampilan aplikasi pada layar lebar](<Screenshot 2026-10-29 111826.png>)

Buat folder `docs/screenshots/`, lalu simpan screenshot dengan nama `phone.png` dan `wide.png`, atau ubah path gambar di atas sesuai nama file yang digunakan.

## Kontribusi Anggota

Lengkapi tabel berikut berdasarkan kontribusi yang benar-benar dilakukan.

| Anggota | Kontribusi |
|---|---|
| Niko Aditya Alansyah | Mengembangkan implementasi awal aplikasi Catatan Tugas Kuliah, meliputi struktur project, antarmuka pengguna menggunakan Jetpack Compose, serta implementasi awal MVVM, UI State, dan StateFlow. |
| Dzakki Naufal Hanif | Melakukan pengembangan lanjutan pada fitur penghapusan tugas (delete), termasuk penyesuaian fungsi penghapusan dan integrasi callback antara UI dan ViewModel agar sesuai dengan alur Unidirectional Data Flow (UDF). Melakukan pembaruan dokumentasi README untuk menjelaskan fitur dan alur UDF. |

## Batasan

- Data disimpan di memori dan tidak dijamin tetap tersedia setelah proses aplikasi dihentikan.
- Belum ada login, sinkronisasi cloud, database persisten, atau notifikasi.
- Aplikasi berfokus pada pencatatan tugas dan status penyelesaian, bukan pengelolaan deadline atau kalender.

## Pengembangan Selanjutnya

Jika cakupan tugas dan waktu memungkinkan, aplikasi dapat dikembangkan dengan tanggal tenggat, pengurutan tugas, atau penyimpanan lokal. Fitur tersebut berada di luar cakupan minimum tugas saat ini.

## Referensi

- Materi perkuliahan **UI State, StateFlow, dan Unidirectional Data Flow**.
- [Android Developers — UI layer](https://developer.android.com/topic/architecture/ui-layer)
- [Kotlin — StateFlow API](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
