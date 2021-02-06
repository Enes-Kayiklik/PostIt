package com.eneskayiklik.postit.ui.main.addnote

import android.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.base.BaseFragment
import com.eneskayiklik.postit.databinding.FragmentAddNoteBinding
import com.eneskayiklik.postit.db.entity.Note
import com.eneskayiklik.postit.db.entity.Todo
import com.eneskayiklik.postit.ui.main.notes.NoteViewModel
import com.eneskayiklik.postit.util.LayoutType
import com.eneskayiklik.postit.util.extensions.changeLayoutType
import com.eneskayiklik.postit.util.extensions.convertHumanTime
import com.eneskayiklik.postit.util.extensions.hide
import com.eneskayiklik.postit.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.menu.add_note_menu),
    TodoListAdapter.OnItemCheckedChange {
    private val noteViewModel: NoteViewModel by viewModels()
    private val navArgs by navArgs<AddNoteFragmentArgs>()
    private var todoListAdapter = TodoListAdapter(this)
    private var layoutType = LayoutType.NOTE
    var todoList: MutableList<Todo> = mutableListOf()

    override val layoutId: Int
        get() = R.layout.fragment_add_note

    override fun initialize() {
        setLayoutTypeForFirstTime()
        setupButtonsOnClick()
    }

    private fun setLayoutTypeForFirstTime() {
        navArgs.currentNote?.let {
            setData(it)
            if (it.todoList.isNotEmpty()) {
                todoList = it.todoList.toMutableList()
                layoutType = LayoutType.LIST
                convertLayoutType()
            } else {
                binder.edtNote.setText(it.description)
            }
        }
    }

    private fun setupRecyclerView() {
        todoListAdapter.submitList(todoList)
        binder.recyclerViewTodoList.adapter = todoListAdapter
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
                if (viewHolder.adapterPosition < target.adapterPosition) {
                    for (i in viewHolder.adapterPosition until target.adapterPosition) {
                        Collections.swap(todoList, i, i + 1)
                    }
                } else {
                    for (i in viewHolder.adapterPosition downTo target.adapterPosition + 1) {
                        Collections.swap(todoList, i, i - 1)
                    }
                }
                todoListAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoList.removeAt(viewHolder.adapterPosition)
                todoListAdapter.notifyItemRangeChanged(viewHolder.adapterPosition, todoList.size)
                todoListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binder.recyclerViewTodoList)
    }

    private fun setData(note: Note) {
        binder.apply {
            tvDate.text = note.date.convertHumanTime()
            edtNoteTitle.setText(note.title)
            tvTitleLength.text = "${note.title.length}".plus(" / 15")
        }
    }

    private fun setupButtonsOnClick() {
        binder.edtNoteTitle.doOnTextChanged { text, _, _, _ ->
            binder.tvTitleLength.text = "${text?.length ?: 0}".plus(" / 15")
        }

        binder.btnAddListItem.setOnClickListener {
            addTodoListItem()
        }
    }

    private fun saveNote(): Boolean {
        val note = binder.edtNote.text.toString()
        val title = binder.edtNoteTitle.text.toString()

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
            Toast.makeText(this.requireContext(), R.string.input_data_error, Toast.LENGTH_SHORT)
                .show()
            false
        }
    }

    private fun addTodoListItem() {
        todoList.add(Todo())
        todoListAdapter.notifyItemInserted(todoList.size - 1)
    }

    private fun convertLayoutType() {
        when (layoutType) {
            LayoutType.LIST -> listLayout().also { setupRecyclerView(); addTodoListItem() }
            LayoutType.NOTE -> noteLayout().also { todoList.clear() }
        }
    }

    private fun noteLayout() {
        binder.apply {
            edtNote.show()
            recyclerViewTodoList.hide()
            btnAddListItem.hide()
        }
    }

    private fun listLayout() {
        binder.apply {
            edtNote.hide()
            recyclerViewTodoList.show()
            btnAddListItem.show()
        }
    }

    private fun deleteCurrentNote(note: Note?): Boolean {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.delete)
            .setMessage(
                resources.getString(R.string.delete_note_text).plus(" '${note?.title ?: ""}' ?")
            )
            .setPositiveButton(R.string.yes) { _, _ ->
                note?.let {
                    noteViewModel.deleteNote(it)
                }
                this.requireActivity().onBackPressed()
            }
            .setNegativeButton(R.string.no) { _, _ -> }.show()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.saveNote -> saveNote()
        R.id.changeNoteType -> {
            layoutType = layoutType.changeLayoutType()
            convertLayoutType()
            true
        }
        R.id.deleteNote -> deleteCurrentNote(navArgs.currentNote)
        else -> super.onOptionsItemSelected(item)
    }

    override fun onItemCheckedChange(todo: Todo, isChecked: Boolean, position: Int) {
        if (todoList.isNotEmpty())
            todoList[position].isDone = isChecked
    }

    override fun onPause() {
        super.onPause()
        binder.root.clearFocus()
    }
}