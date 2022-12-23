package com.taru.ui.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController

/**
 * Created by Niraj on 20-11-2022.
 */
// https://github.com/dgewe/Movie-App-Android/blob/master/app/src/main/java/com/fredrikbogg/movie_app/ui/BaseFragment.kt

abstract class FragmentBase(private val backEnabled:  Boolean) : Fragment(){

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (backEnabled) setHasOptionsMenu(true)
    }
*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
//                menuInflater.inflate(R.menu.menu_main, menu)
//                menuInflater.inflate(R.menu.example_menu, menu)
//                super.onPrepareMenu(menu)
            }

            /*override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
            }
            */

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                      if(backEnabled) {
                          findNavController().popBackStack()
                          true
                      }else false
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupViewModelObservers()
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    open fun setupViewModelObservers() {}


}