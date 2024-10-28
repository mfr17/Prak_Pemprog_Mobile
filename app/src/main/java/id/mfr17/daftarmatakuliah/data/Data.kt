package id.mfr17.daftarmatakuliah.data

import id.mfr17.daftarmatakuliah.model.Matakuliah

object Data {
    // Daftar matakuliah
    var matakuliahList = mutableListOf(
        Matakuliah("Pemrograman Berbasis Mobile", 3, "Dr. Budi"),
        Matakuliah("Pemrograman Web", 3, "Dr. Andi"),
        Matakuliah("Data Structures", 3, "Dr. Siti"),
        Matakuliah("Algoritma", 3, "Dr. Agus"),
        Matakuliah("Sistem Operasi", 3, "Dr. Rina")
    )

    // Fungsi untuk memperbarui matakuliah
    fun updateMatakuliah(updatedMatakuliah: Matakuliah) {
        val index = matakuliahList.indexOfFirst { it.nama == updatedMatakuliah.nama }
        if (index != -1) {
            matakuliahList[index] = updatedMatakuliah
        }
    }
}
