package gstv.sicredi.core.utils

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import gstv.sicredi.R
import gstv.sicredi.model.domain.Event
import gstv.sicredi.view.fragments.HomeFragment

fun FragmentActivity.shareEventWithOthers(event: Event) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Evento: ${event.title}")
        putExtra(Intent.EXTRA_TEXT, "Data: ${event.date}")
        putExtra(Intent.EXTRA_TEXT, "Preço: R$${event.price}")
    }
    startActivity(Intent.createChooser(intent, null))
}

fun Fragment.showErrorDialog(message: String?) {
    MaterialAlertDialogBuilder(requireActivity())
        .setTitle(getString(R.string.warning))
        .setMessage(
            if (message.isNullOrEmpty())
                getString(R.string.we_are_with_problems)
            else message
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