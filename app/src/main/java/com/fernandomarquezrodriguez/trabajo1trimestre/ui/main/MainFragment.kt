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

class MainFragment : Fragment(R.layout.fragment_main), SearchView.OnQueryTextListener {

    private val viewModel: MainViewModel by viewModels{MainViewModel.MainViewModelFactory("99f7bab35f4645379cfa6ac6c7168ab2")}
    private val adapter = RecipeAdapter(){ recipe-> viewModel.navigateTo(recipe)}

    override fun onViewCreated(view: View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val binding =FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter

            (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        }
        viewModel.state.observe(viewLifecycleOwner){state->

            binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE

            state.recipes?.let {recipes ->

                adapter.recipes = recipes
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

            binding.searchView.setOnQueryTextListener(this)

        }

    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("Not yet implemented")
    }


}