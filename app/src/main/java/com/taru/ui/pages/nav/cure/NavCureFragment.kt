package com.taru.ui.pages.nav.cure

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.taru.databinding.NavCureFragmentBinding
import com.taru.tools.livedata.LiveDataObserver
import com.taru.ui.base.FragmentBase
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Niraj on 08-01-2023.
 */
@AndroidEntryPoint
class NavCureFragment: FragmentBase(false) {
    private val mViewModel: NavCureViewModel by viewModels()
    private lateinit var vBinding: NavCureFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vBinding =
            NavCureFragmentBinding.inflate(inflater, container, false).apply {
                bViewModel = mViewModel
            }
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        mViewModel.mEventGuidance.observe(viewLifecycleOwner, LiveDataObserver{
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(it))
            startActivity(intent)
        })

    }
}