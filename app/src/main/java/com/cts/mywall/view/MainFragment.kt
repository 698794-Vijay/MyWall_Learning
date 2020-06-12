package com.cts.mywall.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cts.myconnectsapp.adapters.MyWallAdapter
import com.cts.mywall.R
import com.cts.mywall.databinding.FragmentMainBinding
import com.cts.mywall.viewmodel.WallViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(){
    lateinit var viewModel: WallViewModel
    private lateinit var adapter: MyWallAdapter
    private lateinit var mainBinding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        mainBinding.lifecycleOwner = viewLifecycleOwner
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider.AndroidViewModelFactory.
        getInstance(activity!!.application).create(WallViewModel::class.java)

        showProgress()
        viewModel.getMyWallData().observe(this, Observer{ data ->

            recycler.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = MyWallAdapter(data, requireActivity())
            }

            hideProgress()
            Log.d("APP", "Observer Live data called")
        })
    }
    fun showProgress(){
        mainBinding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        mainBinding.progressBar.visibility = View.INVISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
