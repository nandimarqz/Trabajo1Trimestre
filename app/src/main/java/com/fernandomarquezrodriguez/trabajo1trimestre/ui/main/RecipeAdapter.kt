package com.fernandomarquezrodriguez.trabajo1trimestre.ui.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.trabajo1trimestre.R
import com.fernandomarquezrodriguez.trabajo1trimestre.databinding.RecipeViewBinding
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe
import java.util.*
import kotlin.streams.toList

class RecipeAdapter(val listener : (Recipe) ->Unit): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    var recipes= emptyList<Recipe>()
    var originalRecipes= emptyList<Recipe>()
    lateinit var coleccion : MutableList<Recipe>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Guarda la vista inflada en una variable y la devolve pasandola como parametro a viewHolder para que controle la vista
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return ViewHolder(view)
    }

    /**
     * Se encarga de asignar un componente en el momento en el que la vista entra en pantalla
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //llama a la funcion bind creada abajo con la persona
        holder.bind(recipes[position])

        //Guarda a la persona en una variable
        val person = recipes[position]

        //Pone un escuchador de eventos para cuando se haga un click
        // en contacto llama a la funcion que se pasa por parametro a esta misma clase
        holder.itemView.setOnClickListener {
            listener(person)

        }


    }

    fun filtrado(txtSearchView : String){

        val longitud = txtSearchView.length

        if(longitud == 0) {

            recipes = emptyList<Recipe>()
            recipes = originalRecipes

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 coleccion =
                     recipes.stream().filter{ i-> i.title.lowercase(Locale.getDefault()).contains(txtSearchView.lowercase(Locale.getDefault()))}.toList() as MutableList<Recipe>

                recipes = coleccion
            } else {
                recipes.forEach {recipe ->

                    if(recipe.title.lowercase(Locale.getDefault()).contains(txtSearchView.lowercase(Locale.getDefault()))){

                        coleccion.add(recipe)

                    }

                }
                recipes = coleccion
            }

        }
        notifyDataSetChanged()
    }

    /**
     * Devuelve el tamaño de la lista pasada por parametro
     */
    override fun getItemCount(): Int = recipes.size

    /**
     * Gestiona la vista y controla lo que hay en pantalla, los datos, los reutiliza, etc
     */
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        //Obtiene los items de la vista en la variable
        private val binding = RecipeViewBinding.bind(view)

        /**
         * Esta funcion cambia todos los valores de texto de las
         * etiquetas recogidas y cambia tambien la imagen con Glide
         */
        fun bind(recipe: Recipe ){

            binding.textView.text = recipe.title


            Glide.with(binding.imageView).load(recipe.image).into(binding.imageView)

        }


    }

}