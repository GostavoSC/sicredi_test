package gstv.sicredi.view.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import gstv.sicredi.R
import gstv.sicredi.core.utils.shareEventWithOthers
import gstv.sicredi.core.utils.showErrorDialog
import gstv.sicredi.databinding.DetailsFragmentBinding
import gstv.sicredi.model.domain.Event
import gstv.sicredi.view.MainActivity
import gstv.sicredi.view_model.DetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var binding: DetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.fetchDetails(it.getString(EVENT_ID) ?: "")
        }
        setupObserver()
        setupLayout()
    }

    private fun setupLayout() {
        binding.checkinButton.setOnClickListener{
            viewModel.sendCheckIn()
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            eventDetails.observe(viewLifecycleOwner) {
                updateLayout(it)
                (requireActivity() as MainActivity).hideToolbar()
            }

            onCheckInDone.observe(viewLifecycleOwner) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.checkin_done),
                    Snackbar.LENGTH_SHORT
                )
                    .setTextColor(Color.WHITE)
                    .show()
            }

            onError.observe(viewLifecycleOwner) {
                showErrorDialog(it)
            }
        }
    }

    private fun updateLayout(event: Event) {
        with(binding) {
            progressBar.visibility = View.GONE
            eventTitle.text = event.title
            eventDescription.text = event.description
            val address = Geocoder(requireActivity()).getFromLocation(
                event.latitude.toDouble(),
                event.longitude.toDouble(),
                1
            )?.first()

            address?.let {
                "${it.adminArea} - ${it.subAdminArea} - ${it.subLocality} , ${it.thoroughfare} ${it.subThoroughfare}"
                eventLocalization.text = getString(
                    R.string.address_mask,
                    it.adminArea,
                    it.subAdminArea,
                    it.subLocality,
                    it.thoroughfare,
                    it.subThoroughfare
                )
            }

            icShare.setOnClickListener {
                requireActivity().shareEventWithOthers(event)
            }

            Glide.with(requireActivity())
                .load(event.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageEvent.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources, R.drawable.ic_without_image, resources.newTheme()
                            )
                        )
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageEvent)
        }
    }

    companion object {
        const val EVENT_ID = "id"
    }
}
