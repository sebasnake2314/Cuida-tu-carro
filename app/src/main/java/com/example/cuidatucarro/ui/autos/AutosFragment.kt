package com.example.cuidatucarro.ui.autos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cuidatucarro.R
import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.recyclers.MainAdapter
import com.example.cuidatucarro.viewmodel.AutoViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_auto.*


class AutosFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var floatingActionButton: FloatingActionButton
    var esconderBar:Boolean = false

    private lateinit var adapter :MainAdapter
    private val viewModel by lazy {ViewModelProvider(this).get(AutoViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.fragment_auto, container, false)
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter(view.context)
        recyclerAutos.layoutManager = LinearLayoutManager(this.activity)
        recyclerAutos.adapter = adapter

        observeData()

        floatingActionButton = view.findViewById(R.id.btnadd)

    btnbar.setOnClickListener{
        if (esconderBar){
            floatingActionButton.show()
            esconderBar  = false
        }else{
            floatingActionButton.hide()
            esconderBar = true
        }
    }

        btnadd.setOnClickListener{
            findNavController().navigate(R.id.fragemtagregarauto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    fun observeData(){

        shimmer_view_container.startShimmer()

        val datosUsuario = FirebaseAuth.getInstance().currentUser

        viewModel.traerDatosVehiculos(datosUsuario!!.uid).observe(viewLifecycleOwner, Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })

    }

}