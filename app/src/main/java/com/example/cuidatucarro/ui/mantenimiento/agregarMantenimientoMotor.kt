package com.example.cuidatucarro.ui.mantenimiento


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.cuidatucarro.R
import com.example.cuidatucarro.objetos.tipoMatenimientoMotor
import com.example.cuidatucarro.viewmodel.mantenimientoViewModel
import kotlinx.android.synthetic.main.fragment_agregar_mantenimiento.*
import java.util.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import androidx.lifecycle.Observer
import com.example.cuidatucarro.recyclers.MainAdapter
import kotlinx.android.synthetic.main.fragment_agregarauto.*

class agregarMantenimientoMotor : Fragment() {
    private var ListaMantenimientoMotor = mutableListOf<tipoMatenimientoMotor>()
    private val viewModel by lazy { ViewModelProvider(this).get(mantenimientoViewModel::class.java) }
    private lateinit var adapter : MainAdapter
    private lateinit var mantenimientosMotor : Array<String>

    var savedia = 0
    var savemes = 0
    var saveanio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_mantenimiento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tiposMantenimientoMotor()

        txtTipoMantenimiento.setOnClickListener {
            popTipoMantenimientoMotor()
        }


        txtFecha.setOnClickListener {
            val now = Calendar.getInstance()
            val currentYear: Int = now.get(Calendar.YEAR)
            val currentMonth: Int = now.get(Calendar.MONTH)
            val currentDay: Int = now.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                savedia = dayOfMonth
                savemes = monthOfYear
                saveanio = year

                var selectedDate:String = "$dayOfMonth / ${monthOfYear + 1} / $year"

                txtFecha.setText(selectedDate)

            }, currentYear, currentMonth, currentDay)

            datePickerDialog.setTitle("Seleccionar fecha")
            datePickerDialog.setAccentColor(resources.getColor(R.color.colorPrimary))
            datePickerDialog.setOkText("Agregar")
            datePickerDialog.setCancelText("Salir")
            datePickerDialog.show(activity?.fragmentManager,"")
        }

    }

   private fun popTipoMantenimientoMotor() {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setItems(mantenimientosMotor){dialog, which ->
            txtTipoMantenimiento.setError(null)
            txtTipoMantenimiento.setText(mantenimientosMotor[which])
        }
        val alert = dialogBuilder.create()
        alert.setTitle("Selecione el tipo de Mantenimiento")
        alert.show()
    }

    private fun tiposMantenimientoMotor(){
        viewModel.traerTipoMantenimientoMotor().observe(viewLifecycleOwner, Observer {
            var count =0
            for (i in it) {

                if (count == 0){
                    mantenimientosMotor = arrayOf(it.get(0).tipoMantenimiento)
                }else{
                    //mantenimientosMotor += it.get(i).tipoMantenimiento
                    mantenimientosMotor += arrayOf(i.tipoMantenimiento)
                }

                count  +=  1
            }
            //popTipoMantenimientoMotor()
        })
    }


}
