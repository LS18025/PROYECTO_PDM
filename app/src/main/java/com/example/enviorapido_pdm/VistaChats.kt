package com.example.enviorapido_pdm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.enviorapido_pdm.ui.chat.FragmentoChatChats
import com.example.enviorapido_pdm.ui.chat.FragmentoUsuariosChats
import com.google.android.material.tabs.TabLayout

class VistaChats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_chats)
        InicializarElementos()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    fun InicializarElementos(){
        val tabLayout : TabLayout = findViewById(R.id.TabLayoutMain)
        val  viewPager : ViewPager = findViewById(R.id.ViewPagerMain)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addItem(FragmentoUsuariosChats(),"Usuarios")
        viewPagerAdapter.addItem(FragmentoChatChats(),"Chats")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }



    class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

        private val listaFragmentos:MutableList<Fragment> = ArrayList()
        private val listaTitulos:MutableList<String> = ArrayList()
        override fun getCount(): Int {
            return listaFragmentos.size
        }

        override fun getItem(position: Int): Fragment {
            return listaFragmentos[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listaTitulos[position]
        }

        fun addItem(fragment: Fragment, titulo: String){
            listaFragmentos.add(fragment)
            listaTitulos.add((titulo))
        }

    }
}