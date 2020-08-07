package com.example.cuidatucarro.ui.autos

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cuidatucarro.R
import com.example.cuidatucarro.recyclers.MainAdapter
import com.example.cuidatucarro.viewmodel.AutoViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_auto.*
import kotlinx.android.synthetic.main.tarjeta_veh.*
import kotlinx.android.synthetic.main.tarjeta_veh.view.*
import kotlinx.android.synthetic.main.tarjeta_veh.view.txtPatente


class AutosFragment : Fragment(),MainAdapter.OnAutoClickListener {

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

        adapter = MainAdapter(view.context, this )
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
            coordinatorLayout.visibility = View.VISIBLE
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
            coordinatorLayout.visibility = View.VISIBLE
        })

    }

    override fun onImageClick(autImage: String) {

    }

    override fun onItemClik(
        autPatenteC: String,
        autImage: String,
        autMarcaC: String,
        autModeloC: String
    ) {
/*        val intent = Intent(context,tarjetaVeh::class.java)

        intent.putExtra("patente",autPatenteC)

        startActivity(intent)*/

        showAlertDialogSuccess(autPatenteC,autImage,autMarcaC,autModeloC)

        Toast.makeText(context, "El auto con patenta $autPatenteC", Toast.LENGTH_SHORT).show()
    }


    private fun showAlertDialogSuccess(patente:String, imageVeh:String,marca:String, modelo:String) {


        var dialogBuilder = AlertDialog.Builder(activity)
        val layoutView = layoutInflater.inflate(R.layout.tarjeta_veh, null)

        layoutView.txtPatente.text = patente
        layoutView.txtMarcaModelo.text = "$marca $modelo"
        Glide.with(layoutView).load(imageVeh).into(layoutView.imaVeh)


        val dialogButton =
            layoutView.findViewById<Button>(R.id.btnDetele)
        dialogBuilder.setView(layoutView)
        /*dialogBuilder.setCancelable(false)*/
        var alertDialog = dialogBuilder.create()

        alertDialog.show()

        dialogButton.setOnClickListener {
            /*alertDialog.dismiss()*/
        }
    }



}


