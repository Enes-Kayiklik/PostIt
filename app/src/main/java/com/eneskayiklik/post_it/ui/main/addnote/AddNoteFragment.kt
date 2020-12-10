package com.eneskayiklik.post_it.ui.main.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import com.eneskayiklik.post_it.db.entity.Todo
import com.eneskayiklik.post_it.ui.main.notes.NoteViewModel
import com.eneskayiklik.post_it.util.convertHumanTime
import com.eneskayiklik.post_it.util.makeInvisible
import com.eneskayiklik.post_it.util.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val noteViewModel: NoteViewModel by viewModels()
    private val navArgs by navArgs<AddNoteFragmentArgs>()
    private var todoListAdapter = TodoListAdapter()
    var todoList: MutableList<Todo> = mutableListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setupRecyclerView()
        setupButtonsOnClick()
        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        todoListAdapter.submitList(todoList)
        recyclerViewTodoList.adapter = todoListAdapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipe = ItemTouchHelper.START or ItemTouchHelper.END
                return ItemTouchHelper.Callback.makeMovementFlags(drag, swipe)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Collections.swap(todoList, viewHolder.adapterPosition, target.adapterPosition)
                todoListAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoList.removeAt(viewHolder.adapterPosition)
                todoListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                todoListAdapter.notifyItemRangeChanged(viewHolder.adapterPosition, todoList.size)
            }
        }).attachToRecyclerView(recyclerViewTodoList)
    }

    private fun setData() {
        tvDate.text = System.currentTimeMillis().convertHumanTime()
        navArgs.currentNote?.let { currentNote ->
            todoList = currentNote.todoList.toMutableList()
            tvDate.text = currentNote.date.convertHumanTime()
            edtNoteTitle.setText(currentNote.title)
            edtNote.setText(currentNote.description)
            tvTitleLength.text = "${currentNote.title.length}".plus(" / 15")
            if (todoListAdapter.currentList.isNotEmpty()) {
                recyclerViewTodoList.makeVisible()
                edtNote.makeInvisible()
            }
        }
        if (todoList.isNotEmpty())
            convertLayoutType()
    }

    private fun setupButtonsOnClick() {
        edtNoteTitle.doOnTextChanged { text, _, _, _ ->
            tvTitleLength.text = "${text?.length ?: 0}".plus(" / 15")
        }

        btnAddListItem.setOnClickListener {
            addTodoListItem()
        }
    }

    private fun saveNote(): Boolean {
        val note = edtNote.text.toString()
        val title = edtNoteTitle.text.toString()

        return if (title.isNotEmpty() && (note.isNotEmpty() || todoListAdapter.currentList.isNotEmpty())) {
            noteViewModel.addNote(
                Note(
                    id = navArgs.currentNote?.id ?: 0,
                    title = title,
                    description = note,
                    date = System.currentTimeMillis(),
                    todoList = todoListAdapter.currentList.filter { it.title.isNotEmpty() }
                )
            )
            this.requireActivity().onBackPressed()
            true
        } else {
            Toast.makeText(this.requireContext(), "Input all require data", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }

    private fun addTodoListItem(): Boolean {
        todoList.add(Todo())
        todoListAdapter.notifyItemInserted(todoList.size - 1)
        recyclerViewTodoList.makeVisible()
        edtNote.makeInvisible()
        return true
    }

    private fun deleteCurrentNote(note: Note?): Boolean {
        note?.let {
            noteViewModel.deleteNote(it)
        }
        this.requireActivity().onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.saveNote -> saveNote()
        R.id.changeNoteType -> convertLayoutType()
        R.id.deleteNote -> deleteCurrentNote(navArgs.currentNote)
        else -> super.onOptionsItemSelected(item)
    }

    private fun convertLayoutType(): Boolean {
        if (btnAddListItem.isVisible)
            todoList.clear()
                .also { btnAddListItem.makeInvisible(); edtNote.makeVisible(); recyclerViewTodoList.makeInvisible() }
        else
            edtNote.setText("").also {
                btnAddListItem.makeVisible(); edtNote.makeInvisible(); recyclerViewTodoList.makeVisible(); todoList.add(
                Todo()
            )
            }
        return true
    }
}