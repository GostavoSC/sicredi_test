package gstv.sicredi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.security.ProviderInstaller
import gstv.sicredi.databinding.MainActivityBinding
import gstv.sicredi.presentation.view_model.HomeViewModel
import gstv.sicredi.ui.EventAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProviderInstaller.installIfNeeded(this)
        setupLayout()
        setupObservers()
    }

    private fun setupLayout() {
        binding.recyclerView.adapter = EventAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.getAllEvents()
        viewModel.eventsList.observe(this) {
            (binding.recyclerView.adapter as EventAdapter).events = it
        }
    }
}