package com.takehometest.acmerouter.ui.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takehometest.acmerouter.R
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.ui.common.openFragment
import com.takehometest.acmerouter.ui.destination.DestinationFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DriversFragment : Fragment() {
    private val viewModel: DriversViewModel by viewModels()

    private lateinit var adapter: DriverAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: use view binding
        val view: View = inflater.inflate(R.layout.fragment_drivers, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.feed_recycler_view)

        adapter = DriverAdapter();

        setupFeedRecyclerView(recyclerView, adapter)
        subscribeObservers()

        refreshData()

        return view
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = context?.resources?.getString(R.string.drivers_fragment_title)
    }

    private fun setupFeedRecyclerView(recyclerView: RecyclerView, adapter: DriverAdapter) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = adapter
        adapter.onItemClick = {
            activity?.openFragment(DestinationFragment.newInstance(it.id), true)
        }
    }

    private fun refreshData() {
        viewModel.resetState()
    }

    private fun resetDrivers(Drivers: List<Driver>) {
        if (Drivers.isNotEmpty()) adapter.resetData(ArrayList(Drivers))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { feed ->
            resetDrivers(feed)
        }
    }
}
