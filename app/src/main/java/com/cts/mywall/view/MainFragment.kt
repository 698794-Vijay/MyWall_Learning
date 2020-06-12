package com.cts.mywall.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cts.myconnectsapp.adapters.MyWallAdapter
import com.cts.mywall.R
import com.cts.mywall.entity.WallModelJson
import com.cts.mywall.viewmodel.WallViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() , CoroutineScope{
    lateinit var viewModel: WallViewModel
    private var profileList: ArrayList<WallModelJson>? = null
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit private var adapter: MyWallAdapter
    var recyclerView: RecyclerView? = null
    var viewInstance: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewInstance = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = viewInstance?.findViewById<RecyclerView>(R.id.recycler)
        return viewInstance    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider.AndroidViewModelFactory.
        getInstance(activity!!.application).create(WallViewModel::class.java)
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView?.layoutManager = linearLayoutManager

        //launch {

        profileList = viewModel.getMyWallData().items

//            val result =  callGetApi()
//            onResult(result) // onResult is called on the main thread
       // }

    }

    override fun onResume() {
        super.onResume()
        profileList = viewModel.getMyWallData().items
        adapter =  MyWallAdapter(profileList!!, activity!!)
        recyclerView!!.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
