package com.fernandomarquezrodriguez.trabajo1trimestre.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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


            val longitudArrR = state.ingredients?.size?.minus(1)
            var strIngredientes = ""
            if(longitudArrR != null){
                for(it in 0..longitudArrR!!){

                    strIngredientes += "\t-"+state.ingredients[it].originalName + "\n"

                }
            }

            val longitudArrS : Int? = if (state.steps?.size == 0) 0 else state.steps?.get(0)?.steps?.size?.minus(1)
            var strPasos = ""
            if(longitudArrS != null && longitudArrS != 0){
                for(it in 0..longitudArrS!!){

                    strPasos += "\t-"+ state.steps?.get(0)?.steps?.get(it)?.number + "-"+ (state?.steps?.get(0)?.steps?.get(it)?.step) + "\n"

                }
            }else {

                strPasos = "No hay ningun paso a seguir"

            }
            binding.textView5.text = buildSpannedString {
                underline {  bold { append("INGREDIENTES:\n") } }
                appendLine(strIngredientes)

            }

            binding.textView4.text = buildSpannedString {
                underline {  bold { append("PASOS:\n") } }
                appendLine(strPasos)
            }
        }

    }
}