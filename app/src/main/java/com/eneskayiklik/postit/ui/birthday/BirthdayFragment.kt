package com.eneskayiklik.postit.ui.birthday

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.util.makeInvisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_birthday.*

@AndroidEntryPoint
class BirthdayFragment : Fragment(R.layout.fragment_birthday) {
    private val birthdayViewModel by viewModels<BirthdayViewModel>()
    private val adapter = BirthdayAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupButtonsOnClick()
        recyclerViewBirthdays.adapter = adapter
    }

    private fun setupButtonsOnClick() {
        btnAddBirthday.setOnClickListener {
            findNavController().navigate(R.id.action_birthdayFragment_to_addBirthdayFragment)
        }
    }

    private fun setupObserver() {
        birthdayViewModel.birthdays.observe(this.viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if (it.isNotEmpty())
                emptyBirthday.makeInvisible()
        })
    }
}