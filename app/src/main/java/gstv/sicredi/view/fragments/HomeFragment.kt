package gstv.sicredi.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gstv.sicredi.R
import gstv.sicredi.core.utils.showErrorDialog
import gstv.sicredi.databinding.HomeFragmentBinding
import gstv.sicredi.view.adapters.EventAdapter
import gstv.sicredi.view.MainActivity
import gstv.sicredi.view.adapters.OnCardClickListener
import gstv.sicredi.view_model.HomeViewModel
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
                override fun clicked(id: String) {
                    val args = Bundle()
                    args.putString(DetailsFragment.EVENT_ID, id)
                    findNavController()
                        .navigate(R.id.action_home_navigation_to_details_fragment, args)
                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        (requireActivity() as MainActivity).showToolbar()
    }

    private fun setupObservers() {
        with(viewModel){
            getAllEvents()
            eventsList.observe(viewLifecycleOwner) {
                binding.progressBar.visibility = View.GONE
                (binding.recyclerView.adapter as EventAdapter).events = it
            }

            onError.observe(viewLifecycleOwner){
                showErrorDialog(it)
            }
        }

    }
}