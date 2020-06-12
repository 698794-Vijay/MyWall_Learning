package com.cts.mywall.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cts.myconnectsapp.adapters.MyWallAdapter
import com.cts.mywall.R
import com.cts.mywall.databinding.FragmentMainBinding
import com.cts.mywall.viewmodel.WallViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import okhttp3.internal.notify

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), AdapterView.OnItemSelectedListener{
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
     lateinit var categoryTypesList : Array<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider.AndroidViewModelFactory.
        getInstance(activity!!.application).create(WallViewModel::class.java)

        showProgress()
        viewModel.getMyWallData().observe(this, Observer{ data ->
            if(data == null)
                return@Observer

            recycler.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = MyWallAdapter(data, requireActivity())
            }


            categoryTypesList = viewModel.getCategoryTypes()
            Log.d("APP", "Observer Live data called")

            val aa = ArrayAdapter(requireContext(), R.layout.colored_spinner_layout, categoryTypesList)
            aa.setDropDownViewResource(R.layout.spinner_item)
            with(mainBinding.actionBarSpinner)
            {
                adapter = aa
                setSelection(0, false)
                onItemSelectedListener = this@MainFragment
                gravity = Gravity.END
            }

            hideProgress()
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(context, "onNothingSelected", Toast.LENGTH_SHORT).show()

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item  = categoryTypesList[p2]
        val wallList = viewModel.getCategoryTypesByType(item)
        (recycler.adapter as MyWallAdapter).wallItemsList = wallList
        (recycler.adapter as MyWallAdapter).notifyDataSetChanged()
    }
}
