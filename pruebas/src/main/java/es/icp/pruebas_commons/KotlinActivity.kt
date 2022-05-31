package es.icp.pruebas_commons

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import es.icp.icp_commons.simplesearchview.SimpleSearchView
import es.icp.icp_commons.simplesearchview.utils.DimensUtils.convertDpToPx
import es.icp.pruebas_commons.databinding.ActivityKotlinBinding

class KotlinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        setSupportActionBar(binding.toolbar)
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupSearchView(menu: Menu) = with(binding) {
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        searchView.setHint("Buscar...")
        binding.toolbar.title = "Nuevo Titulo"
//        searchView.setTabLayout(tabLayout)

        binding.toolbar.setOnMenuItemClickListener {it->
            when(it.itemId){

            }
            true
        }

        searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextChange -> $newText")
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextSubmit -> $query")
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextCleared")
                return false
            }
        })

        // Adding padding to the animation because of the hidden menu item
        val revealCenter = searchView.revealAnimationCenter
        revealCenter!!.x -= convertDpToPx(EXTRA_REVEAL_CENTER_PADDING, context)
    }

    override fun onBackPressed() = with(binding) {
        if (searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }


    companion object {
        const val EXTRA_REVEAL_CENTER_PADDING = 0 // 0 Si es el 1º 40 si es el 2º
    }

}