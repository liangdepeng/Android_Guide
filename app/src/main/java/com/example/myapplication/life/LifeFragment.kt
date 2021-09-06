package com.example.myapplication.life

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentLifeBinding

/**
 * Created by ldp.
 *
 * Date: 2021/9/2
 *
 * Summary:
 */
class LifeFragment : BaseFragment() {


    /**
     *
     * -- onAttach -- LifeFragment
     * -- onCreate -- LifeFragment
     * -- onCreateView -- LifeFragment
     * -- onViewCreated -- LifeFragment
     * -- onActivityCreated -- LifeFragment
     * -- onStart -- LifeFragment
     * -- onResume -- LifeFragment
     * -- onPause -- LifeFragment
     * -- onStop -- LifeFragment
     * -- onDestroyView -- LifeFragment
     * -- onDestroy -- LifeFragment
     * -- onDetach -- LifeFragment
     *
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflate = FragmentLifeBinding.inflate(inflater, container, false)
        return inflate.root
    }
}