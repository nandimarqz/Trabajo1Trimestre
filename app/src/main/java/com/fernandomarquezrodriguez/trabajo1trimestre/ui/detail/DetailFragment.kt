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

            binding.textView5.text = "INGREDIENTES"

            val longitudArr = state.ingredients?.size?.minus(1)
            var strIngredientes = ""
            if(longitudArr != null){
                for(it in 0..longitudArr!!){

                    strIngredientes += state.ingredients[it].originalName + "\n"

                }
            }


            binding.textView4.text = strIngredientes
        }

    }
}