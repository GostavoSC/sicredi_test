package gstv.sicredi.core.utils

import android.content.Intent
import android.location.Geocoder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gstv.sicredi.R
import gstv.sicredi.model.domain.Event
import gstv.sicredi.view.fragments.HomeFragment

fun FragmentActivity.shareEventWithOthers(event: Event, address: String?) {

    val updatedAddress =  address ?: "Sem localização informada"
    val intent = Intent(Intent.ACTION_SEND)
    intent.setType("text/plain")

    intent.putExtra(
        Intent.EXTRA_TEXT,
        "Evento: ${event.title}\nData: ${event.date}\nPreço: ${event.price}\nLocal: $updatedAddress"
    )
    startActivity(Intent.createChooser(intent, null))
}

fun FragmentActivity.getAddressLocalization(lat: Float, long: Float): String? {
    try {
        val address = Geocoder(this).getFromLocation(
            lat.toDouble(),
            long.toDouble(),
            1
        )?.first()

        return getString(
            R.string.address_mask,
            address?.adminArea,
            address?.subAdminArea,
            address?.subLocality,
            address?.thoroughfare,
            address?.subThoroughfare
        )
    } catch (_: Throwable) {
        return null
    }
}

fun Fragment.showErrorDialog(error: ResultWrapper<*>) {
    MaterialAlertDialogBuilder(requireActivity())
        .setTitle(getString(R.string.warning))
        .setMessage(
            when (error) {
                is ResultWrapper.GenericError -> {
                    if (error.error.isNullOrEmpty()) getString(R.string.we_are_with_problems) else error.error
                }

                is ResultWrapper.NetworkError -> {
                    getString(R.string.connection_problemn)
                }

                else -> {
                    getString(R.string.we_are_with_problems)
                }
            }
        )
        .setPositiveButton(
            getString(R.string.understand)
        ) { dialog, _ ->
            if (this is HomeFragment) {
                requireActivity().finish()
            }
            dialog.dismiss()
            parentFragmentManager.popBackStack()
        }.show()
}