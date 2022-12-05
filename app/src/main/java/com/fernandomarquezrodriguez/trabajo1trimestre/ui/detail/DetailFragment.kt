package com.fernandomarquezrodriguez.trabajo1trimestre.ui.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.trabajo1trimestre.R
import com.fernandomarquezrodriguez.trabajo1trimestre.databinding.FragmentDetailBinding
import com.fernandomarquezrodriguez.trabajo1trimestre.databinding.FragmentMainBinding
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels {
        val recipe  =  arguments?.getParcelable<Recipe>(SELECTED_RECIPE)!!
        DetailViewModelFactory( recipe, recipe.id.toString(), "99f7bab35f4645379cfa6ac6c7168ab2" )

    }

    //Crea la constante para recoger del intent a la persona
    companion object{

        const val SELECTED_RECIPE = "RecetaSeleccionada"

    }

    override fun onViewCreated(view: View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //Recuperamos el binding con bind por que la vista ya esta inflada
        val binding = FragmentDetailBinding.bind(view)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel.state.observe(viewLifecycleOwner){state ->

            //Se cambia el titulo de la action bar por el nombre de la persona
            (requireActivity() as AppCompatActivity).supportActionBar?.title = state.recipe?.title


            //Se cambia la url de la imagen
            Glide.with(binding.imageView3).load(state.recipe?.image).into(binding.imageView3)

            //Guarda en una varibale la longitud de la lista
            val longitudArrR = state.ingredients?.size?.minus(1)
            var strIngredientes = ""

            //Si la longitud es distinta a null entra en la condicion
            if(longitudArrR != null){
                //Recorre el array de ingredientes y los añade a la variable
                for(it in 0..longitudArrR!!){

                    strIngredientes += "\t-"+state.ingredients[it].originalName + "\n"

                }
            }

            //Si la longitud del array de pasos es iguaal a 0 se guarda 0 si es distinta se guarda el valor
            val longitudArrS : Int? = if (state.steps?.size == 0) 0 else state.steps?.get(0)?.steps?.size?.minus(1)
            var strPasos = ""

            //Si la longitud es distinto a null y distinto a 0 entra en la condicion si es alguno de los dos entra en el else
            if(longitudArrS != null && longitudArrS != 0){
                //Recoore la lista de pasos y añade los pasos a una variable
                for(it in 0..longitudArrS!!){

                    strPasos += "\t-"+ state.steps?.get(0)?.steps?.get(it)?.number + "-"+ (state?.steps?.get(0)?.steps?.get(it)?.step) + "\n"

                }
            }else {
                //Se iguala la variable la siguiente string
                strPasos = "No hay ningun paso a seguir"

            }
            //Se añade al texview lo ingredientes
            binding.textView5.text = buildSpannedString {
                underline {  bold { append("INGREDIENTES:\n").setSpan(ForegroundColorSpan(Color.BLACK), 0,12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) } }
                appendLine(strIngredientes)

            }

            //Se añade al textview los pasos
            binding.textView4.text = buildSpannedString {
                underline {  bold { append("PASOS:\n") }.setSpan(ForegroundColorSpan(Color.BLACK), 0,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
                appendLine(strPasos)
            }
        }

    }
}