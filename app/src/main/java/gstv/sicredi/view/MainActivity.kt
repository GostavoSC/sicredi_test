package gstv.sicredi.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.security.ProviderInstaller
import gstv.sicredi.databinding.MainActivityBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProviderInstaller.installIfNeeded(this)
    }

    fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
    }

    fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
    }
}