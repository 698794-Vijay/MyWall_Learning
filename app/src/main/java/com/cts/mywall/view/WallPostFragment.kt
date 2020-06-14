package com.cts.mywall.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cts.mywall.adapters.WallPostAdapter
import com.cts.mywall.R
import com.cts.mywall.databinding.FragmentWallPostBinding
import com.cts.mywall.viewmodel.PostItemViewModel
import kotlinx.android.synthetic.main.fragment_wall_post.*

class WallPostFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var postItemViewModel: PostItemViewModel
    private lateinit var wallPostBinding: FragmentWallPostBinding
    private var categoryTypesList: Array<String>? = null

    companion object {
        @JvmStatic
        fun newInstance() = WallPostFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wallPostBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_wall_post, container, false)
        wallPostBinding.lifecycleOwner = viewLifecycleOwner

        wallPostBinding.recycler.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = WallPostAdapter(ArrayList())
        }
        return wallPostBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postItemViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
                .create(PostItemViewModel::class.java)

        showProgress()
        postItemViewModel.getMyWallData().observe(this, Observer { data ->
            if (data == null)
                return@Observer

            (recycler.adapter as WallPostAdapter).wallItemsList = data
            (recycler.adapter as WallPostAdapter).notifyDataSetChanged()

            categoryTypesList = postItemViewModel.getCategoryTypes()
            categoryTypesList?.let {
                val aa =
                    ArrayAdapter(
                        requireContext(),
                        R.layout.colored_spinner_layout,
                        it
                    )
                aa.setDropDownViewResource(R.layout.spinner_item)
                with(wallPostBinding.actionBarSpinner) {
                    adapter = aa
                    setSelection(0, false)
                    onItemSelectedListener = this@WallPostFragment
                    gravity = Gravity.END
                }
            }

            hideProgress()
        })
    }

    private fun showProgress() {
        wallPostBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        wallPostBinding.progressBar.visibility = View.GONE
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        categoryTypesList?.let {
            val item = it[p2]
            val wallList = postItemViewModel.getCategoryTypesByType(item)
            wallList?.let {
                (recycler.adapter as WallPostAdapter).wallItemsList = it
                (recycler.adapter as WallPostAdapter).notifyDataSetChanged()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}