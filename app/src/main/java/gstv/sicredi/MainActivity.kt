package gstv.sicredi

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.security.ProviderInstaller
import gstv.sicredi.databinding.MainActivityBinding
import gstv.sicredi.ui.HomeFragment

class MainActivity : FragmentActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProviderInstaller.installIfNeeded(this)
        setupLayout()
    }

    private fun setupLayout() {

    }
}