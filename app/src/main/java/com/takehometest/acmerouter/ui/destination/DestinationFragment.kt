package com.takehometest.acmerouter.ui.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.takehometest.acmerouter.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DestinationFragment : Fragment() {

    companion object {
        const val FIELD_DRIVER_ID = "field_driver_id"

        fun newInstance(driverFullName: Int): DestinationFragment {
            return DestinationFragment().apply {
                arguments = bundleOf(FIELD_DRIVER_ID to driverFullName)
            }
        }
    }

    private val viewModel: DestinationViewModel by viewModels()

    private lateinit var destinationFullName: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: use view binding
        val view: View = inflater.inflate(R.layout.fragment_destination, container, false)
        destinationFullName = view.findViewById(R.id.destination_full_address)

        subscribeObservers();

        setData(arguments?.getInt(FIELD_DRIVER_ID)!!);

        return view
    }

    private fun setData(driverId: Int) {
        viewModel.resetState(driverId)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            requireActivity().title = state.driver?.fullName

            val fullAddress: String = state.destination?.streetNumber + state.destination?.street
            destinationFullName.text = fullAddress
        }
    }
}
