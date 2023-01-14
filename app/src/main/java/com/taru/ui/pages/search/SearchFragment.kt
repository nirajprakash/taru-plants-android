package com.taru.ui.pages.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.taru.R
import com.taru.databinding.SearchFragmentBinding
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 14-01-2023.
 */
@AndroidEntryPoint
class SearchFragment : FragmentBase(true){

    private var mBackCallback: OnBackPressedCallback? = null
    private val mViewModel: SearchViewModel by viewModels()
    private lateinit var vBinding: SearchFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            SearchFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner

        lifecycleScope.launchWhenCreated {
            vBinding.weatherImage.load(R.drawable.pic_weather)
        }

        vBinding.searchView
            .getEditText()

            .setOnEditorActionListener { v, actionId, event ->
                vBinding.searchBar.setText(vBinding.searchView.getText())
                vBinding.searchView.hide()
                false
            }


        mBackCallback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (vBinding.searchView.isShowing && vBinding.searchBar.collapse(vBinding.searchView, vBinding.appBarLayout)) {
                // Clear selection.
                this.remove()
                return@addCallback;
            }else{
                this.remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
//                this.
//                mBackCallback?.handleOnBackPressed()
            }
            Log.d("SearchFragment: ", "callback: ")
            /*else{
                Log.d("SearchFragment: ", "callback: ")
                this.handleOnBackPressed()
            }*/
            // Handle the back button event
        }

    }
}