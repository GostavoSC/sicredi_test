package gstv.sicredi

import android.os.Bundle
import androidx.activity.ComponentActivity
import gstv.sicredi.presentation.view_model.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel

    }
}