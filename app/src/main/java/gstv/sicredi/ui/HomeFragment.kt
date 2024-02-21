package gstv.sicredi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import gstv.sicredi.R
import gstv.sicredi.databinding.HomeFragmentBinding
import gstv.sicredi.presentation.view_model.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupObservers()
    }

    private fun setupLayout() {
        binding.recyclerView.adapter = EventAdapter(
            object : OnCardClickListener {
                override fun clicked(position: Int) {


                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupObservers() {
        viewModel.getAllEvents()
        viewModel.eventsList.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as EventAdapter).events = it
        }
    }
}