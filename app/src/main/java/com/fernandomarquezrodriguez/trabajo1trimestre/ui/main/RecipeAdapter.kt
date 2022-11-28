package com.fernandomarquezrodriguez.trabajo1trimestre.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.trabajo1trimestre.R
import com.fernandomarquezrodriguez.trabajo1trimestre.databinding.RecipeViewBinding
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe

class RecipeAdapter(val listener : (Recipe) ->Unit): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    var recipes= emptyList<Recipe>()

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