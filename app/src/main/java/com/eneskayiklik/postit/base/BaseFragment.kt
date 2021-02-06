package com.eneskayiklik.postit.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @MenuRes private val menuId: Int?
) : Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int
    lateinit var binder: B

    abstract fun initialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binder = DataBindingUtil.inflate(inflater, layoutId, container, false)
        menuId?.let { setHasOptionsMenu(true) }
        initialize()
        return binder.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuId?.let { id -> inflater.inflate(id, menu) }
    }
}