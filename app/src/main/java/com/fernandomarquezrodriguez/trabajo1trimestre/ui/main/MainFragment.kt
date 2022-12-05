package com.fernandomarquezrodriguez.trabajo1trimestre.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.trabajo1trimestre.R
import com.fernandomarquezrodriguez.trabajo1trimestre.databinding.FragmentMainBinding
import com.fernandomarquezrodriguez.trabajo1trimestre.ui.detail.DetailFragment

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels{MainViewModel.MainViewModelFactory("99f7bab35f4645379cfa6ac6c7168ab2")}
    private val adapter = RecipeAdapter(){ recipe-> viewModel.navigateTo(recipe)}

    override fun onViewCreated(view: View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val binding =FragmentMainBinding.bind(view).apply {

            //Se iguala el adapter al creado arriba
            recycler.adapter = adapter

        }

        //observa el viewModel y mira el estado
        viewModel.state.observe(viewLifecycleOwner){state->

            //Si el la variable loading en el viewModel tiene valor true se pone en visible si esta en false se oculta
            binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE

            //Mira el valor de las recetas y igualamos la lista de recetas del adapter a las del mainViewModel
            state.recipes?.let {recipes ->

                adapter.recipes = recipes
                adapter.originalRecipes = recipes
                adapter.notifyDataSetChanged()

            }

            state.navigateTo?.let { recipe ->
                //Realiza la navegacion con la accion realizada en nav_graph
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragment ,
                bundleOf(DetailFragment.SELECTED_RECIPE to  recipe)
                ) 

                viewModel.onNavigateDone()
            }

            //Pone un escuchador de eventos para cuando se haga click en el buscador y busque
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

                //Cuando se envia el texto
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.filtrado(p0)
                    }
                    return false
                }

                //Cuando el texto esta cambiando
                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.filtrado(p0)
                    }
                    return false
                }

            })

        }

    }

}