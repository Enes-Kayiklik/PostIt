package com.eneskayiklik.postit.ui.birthday.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Birthday
import com.eneskayiklik.postit.ui.birthday.BirthdayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_birthday.*

@AndroidEntryPoint
class AddBirthdayFragment : Fragment(R.layout.fragment_add_birthday) {
    private val birthdayViewModel by viewModels<BirthdayViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        button.setOnClickListener {
            birthdayViewModel.addBirthday(
                Birthday(
                    0,
                    System.currentTimeMillis(),
                    editTextTextPersonName.text.toString()
                )
            )
        }
    }

    override fun onPause() {
        super.onPause()
        rootLayout.clearFocus()
    }
}