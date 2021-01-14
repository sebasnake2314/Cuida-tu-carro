package com.example.cuidatucarro.ui.home


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cuidatucarro.R
import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.objetos.mantenientosAutos
import com.example.cuidatucarro.recyclers.MainAdapter
import com.example.cuidatucarro.recyclers.MainMantenimientos_vehi
import com.example.cuidatucarro.viewmodel.AutoViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_auto.*
import kotlinx.android.synthetic.main.fragment_home.*
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData


class HomeFragment : Fragment() {


    private var listVehículos = mutableListOf<Autos>()
    private var listMantenimientos = mutableListOf<mantenientosAutos>()
    private var listarecyclemant = mutableListOf<mantenientosAutos>()
    private lateinit var adapter :MainMantenimientos_vehi
    private val viewModel by lazy { ViewModelProvider(this).get(AutoViewModel::class.java) }
    val datosUsuario = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.fragment_home, container, false)
        return fragment

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        traervehiculos()

        calendar.setOnDateClickListener(object : OnDateClickListener() {
            override fun onDateClick(view: View, date: DateData) {

                var fechaSelect:String =    String.format("%d / %d / %d",date.day, date.month,date.year)


                adapter = MainMantenimientos_vehi(view.context)
                recyclerMantenimientos.layoutManager = LinearLayoutManager(activity)
                recyclerMantenimientos.adapter = adapter


                recyclerMantenimientos.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    )
                )


                var listrecyclemant = mutableListOf<mantenientosAutos>()

                for (i in 0 until listMantenimientos.size) {

                    if (fechaSelect == listMantenimientos[i].fecha){

                        listrecyclemant.add(listMantenimientos[i])

                    }

                }

                listarecyclemant = listrecyclemant

                observeData()

            }
        })

    }

    fun traervehiculos(){

        viewModel.traerDatosVehiculos(datosUsuario!!.uid).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                listVehículos = it

                for (i in 0 until listVehículos.size) {
                    traerFechasMant(
                        listVehículos[i].id_auto,
                        listVehículos[i].aut_marca_c + " " + listVehículos[i].aut_modelo_c,
                        listVehículos[i].aut_patente_c
                    )
                }

            })

    }

    fun traerFechasMant(idAuto: String, desVeh: String, patente: String) {
        viewModel.traerFechasServicios(idAuto, desVeh, patente).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                listMantenimientos = it

                var calendarView = view?.findViewById(R.id.calendar) as MCalendarView

                for (i in 0 until listMantenimientos.size) {

                    var datos = listMantenimientos[i].fecha.split(" / ")

                    //var dates: ArrayList<DateData> = ArrayList()
                    var dates: DateData = (DateData(
                        datos[2].toInt(),
                        datos[1].toInt(),
                        datos[0].toInt()
                    ))

                    calendarView.markDate(
                        DateData(dates.year, dates.month, dates.day).setMarkStyle(
                            MarkStyle.DOT, Color.BLUE
                        )
                    )
                }

            })
    }


    fun observeData(){

        adapter.setListData(listarecyclemant)
        adapter.notifyDataSetChanged()

    }


}


