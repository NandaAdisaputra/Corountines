package com.nandaadisaputra.databinding.ui.activity.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nandaadisaputra.databinding.R
import com.nandaadisaputra.databinding.adapter.HobbyAdapter
import com.nandaadisaputra.databinding.databinding.ActivityMainBinding
import com.nandaadisaputra.databinding.room.Hobby
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    /*Kita deklarasikan juga binding yang akan Kita gunakan*/
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory
    }
    /*Berikutnya deklarasikan juga adapternya*/
    private val adapter = HobbyAdapter()
    /*Jangan lupa deklarasikan hobbyUsers */
    /* hobby diambil dari xml di activity_main dari android:text='@={activity.hobbyUsers}'*/
    /*Karena Kita menerapkan DataBinding*/
    var hobbyUsers = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*inisialisasi dulu activitynya*/
        /*wajib karena Kita memakai DataBinding*/
        binding.activity = this
        binding.lifecycleOwner = this
        /*Kita akan memakai fungsi  initData() dan observe()
       * maka perlu Kita deklarasikan dulu */
        initData()
        observe()

    }
    private fun initData() {
        /*Ketika salah satu item di List diklik akan memunculkan aksi*/
        adapter.setOnClickItem { name ->
            /*Disini Saya kasih aksi memunculkan Toast*/
            /*Kalian bisa menambahkan sendiri Intent ke Detail Activity dibagian ini*/
            Toast.makeText(this, "Ini namanya ${name.yourHobby}", Toast.LENGTH_SHORT).show()
        }
        /*Jangan lupa setelah deklarasi di inisialisasi adapter yak*/
        binding.adapter = adapter
    }

    private fun observe() {
        /*Karena menggunakan Coroutines kita dapat memanggil lifecycleScope.launch */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    /*panggil variabel user dari viewmodel yang isinya perintah menampilkan data*/
                    viewModel.hobby.collect { hobby ->
                        /*Pada bagian ini digunakan untuk menampilkan data */
                        adapter.submitList(hobby)
                    }
                    /*Kita panggil responseSave untuk menampilkan respon ketika data tersimpan */
                    viewModel.responseSave.collect { success ->
                        if (success) {
                            /*Katika berhasil tersimpan datanya akan muncul pesan Berhasil Menyimpan Data. */
                            Toast.makeText(this@MainActivity, "Berhasil Menyimpan Data.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    fun saveHobby() {
        /*Apabila inputan kosong / tidak ada inputan */
        if (hobbyUsers.isEmpty()) {
            /*Akan menampilan pesan Form Masih Kosong Lur. */
            Toast.makeText(this, "Form Masih Kosong Lur.", Toast.LENGTH_SHORT).show()
            return
        }
        /*Kita inisialisasi variabel newHobby isinya adalah class Hobby*/
        val newHobby = Hobby(yourHobby = hobbyUsers)
        /*Kita panggil addHobby untuk menyimpan data ke tabel Hobby*/
        viewModel.addHobby(newHobby)
    }
}
