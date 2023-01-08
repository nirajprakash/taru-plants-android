package com.taru.ui.labs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.taru.databinding.LabsMainFragmentBinding
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 20-11-2022.
 */
@AndroidEntryPoint
class LabsFragmentMain : FragmentBase(true) {


    private val mViewModel: LabsMainViewModel by viewModels()
    private lateinit var vBinding: LabsMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBinding =
            LabsMainFragmentBinding.inflate(inflater, container, false).apply {
                viewModel = mViewModel
                lifecycleOwner = this@LabsFragmentMain.viewLifecycleOwner
            }
        return vBinding.root

//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupViewModelObservers() {
//        super.setupViewModelObservers()

        mViewModel.mEventNavigate.observe(viewLifecycleOwner, LiveDataObserver {
            navigateTo(it)
        })
    }

    private fun navigateTo(it: Int) {
/*        //val action = LabsFragmentMain.actionNavigationHomeToShowAllFragment(movieListType)
        fi//ndNavController().navigate(action)*/

        Log.i("LabsFragmentMain", "navigateTo $it")

        when (it) {
            LabsConstants.Navigation.NAV_APP -> {
                val action = LabsFragmentMainDirections.navigateGlobalToNavApp()
                findNavController().navigate(action)
            }
            LabsConstants.Navigation.DEFAULT -> {
                val action = LabsFragmentMainDirections.navigateGlobalToNavDefault()
                findNavController().navigate(action)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


//    onCreateV


}