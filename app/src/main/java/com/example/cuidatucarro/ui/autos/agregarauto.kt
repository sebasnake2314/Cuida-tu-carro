package com.example.cuidatucarro.ui.autos

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cuidatucarro.R
import com.example.cuidatucarro.viewmodel.AutoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_agregarauto.*


class agregarauto : Fragment() {

    private val Auth = FirebaseAuth.getInstance().currentUser
    private val viewModel by lazy {ViewModelProvider(this).get(AutoViewModel::class.java) }
    private val transmision = arrayOf("Automático", "Sincrónica")
    private val selectpickture = arrayOf("Galería", "Cámara")
    private lateinit var mStorage:StorageReference


    private lateinit var txtPatente: EditText
    private lateinit var txtMarca: EditText
    private lateinit var txtModelo: EditText
    private lateinit var txtKm: EditText
    private lateinit var txtTrasmision: TextView
    private var foto: Uri? = null
    private val REQUEST_GALERIA = 1001
    private val REQUEST_CAMERA = 1002

    var fotoCamarara: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragment = inflater.inflate(R.layout.fragment_agregarauto, container, false)
        return fragment

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddVeh.setOnClickListener{
            txtPatente = view.findViewById(R.id.txtPatente)
            txtPatente = view.findViewById(R.id.txtPatente)
            txtMarca = view.findViewById(R.id.txtMarca)
            txtModelo = view.findViewById(R.id.txtModelo)
            txtKm  = view.findViewById(R.id.txtKilo)
/*            txtTrasmision = txtTrasmision*/

            val patente = txtPatente.text.toString()
            val marca = txtMarca.text.toString()
            val model = txtModelo.text.toString()
            val km = txtKm.text.toString()
            val transmision = txtTransmision.text.toString()
            val image = foto


            if (!TextUtils.isEmpty(patente) && !TextUtils.isEmpty(marca) && !TextUtils.isEmpty(model) && !TextUtils.isEmpty(km) && !TextUtils.isEmpty(transmision) && image != null){
                viewModel.agregarNuevoVehocilo(Auth?.uid.toString(),patente,marca,model,km.toString().toLong(),transmision, image)
                showAlertDialogSuccess()
                findNavController().navigate(R.id.navigation_home)
            }else{
                Toast.makeText(activity,"Completa todos los campos solicitados", Toast.LENGTH_SHORT ).show()
            }
        }
        txtTransmision.setOnClickListener{
            popTransmision()
       }
        btnSelectPickture.setOnClickListener{
            popSelectPickture()
        }
    }


    //Se comprueba si el usuario autorizó permisos a la aplicación
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALERIA ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    muestraGaleria()
                else{
                    Toast.makeText(activity,"No se puede acceder a las imagenes", Toast.LENGTH_LONG).show()
                }
            }
            REQUEST_CAMERA ->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    muestraCamara()
                }else{
                    Toast.makeText(activity,"No se puede acceder a la cámara", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    private fun seleccionCamara(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ||ActivityCompat.checkSelfPermission(requireContext() ,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //Permiso denegado
                val permisoCamara = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permisoCamara,REQUEST_CAMERA)
             }else{
                muestraCamara()
            }
        }else{
            muestraCamara()
        }
    }

    private fun selecionGaleria(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //Permiso denegado
                val permisoArchivos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permisoArchivos,REQUEST_GALERIA)
            }else{
                muestraGaleria()
            }
        }else{
            muestraGaleria()
        }
    }

    //Se abre la Galería de fotos
    private fun muestraGaleria(){
        /*Toast.makeText(activity,"Aqui van las imagenes", Toast.LENGTH_LONG).show()*/
        val intentGaleria = Intent(Intent.ACTION_PICK)
        intentGaleria.type = "image/*"
        startActivityForResult(intentGaleria, REQUEST_GALERIA)
    }


    private fun muestraCamara(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Nueva Imagen")
        fotoCamarara = context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,fotoCamarara)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALERIA){
            btnSelectPickture.setImageURI(data?.data)
            foto = data?.data!!
        }

        if(resultCode == Activity.RESULT_OK && requestCode ==REQUEST_CAMERA){
            btnSelectPickture.setImageURI(fotoCamarara)
            foto = fotoCamarara.toString().toUri()
        }

    }


    private fun showAlertDialogSuccess() {
        var dialogBuilder = AlertDialog.Builder(activity)
        val layoutView = layoutInflater.inflate(R.layout.activity_vehiculo_agregado, null)
        val dialogButton =
            layoutView.findViewById<Button>(R.id.btnOk)
        dialogBuilder.setView(layoutView)
        dialogBuilder.setCancelable(false)
        var alertDialog = dialogBuilder.create()
        alertDialog.show()
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    fun popTransmision(){
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setItems(transmision){dialog, which ->
            txtTransmision.setText(transmision[which])
        }
        val alert = dialogBuilder.create()
        alert.setTitle("Selecione la Transmisión")
        alert.show()
    }

    fun popSelectPickture(){
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setItems(selectpickture){dialog, which ->
            var option = selectpickture[which]

         if (option == "Galería"){
             selecionGaleria()
         }else{
             seleccionCamara()
         }

        }
        val alert = dialogBuilder.create()
        alert.setTitle("Selecciones una opción")
        alert.show()
    }




}



