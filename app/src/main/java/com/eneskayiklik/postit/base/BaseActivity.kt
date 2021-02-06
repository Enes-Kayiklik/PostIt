package com.eneskayiklik.postit.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    val binder by lazy<B> {
        DataBindingUtil.setContentView(this, layoutId)
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun initLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }
}