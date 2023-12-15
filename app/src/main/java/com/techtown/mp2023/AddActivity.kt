package com.techtown.mp2023

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.techtown.mp2023.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var viewModel : TodoViewModel
    lateinit var todoList: MutableLiveData<MutableList<Todo>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProviderFactory(this.application))
            .get(TodoViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_add_save -> {
                val input = findViewById<EditText>(R.id.input_todo) // input_todo의 ID를 사용하여 뷰를 찾음
                if (input.text.toString() != "") {
                    val todo = Todo(input.text.toString())
                    viewModel.insert(todo)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
//                    setList()
                input.setText("")
                }
                finish()
                true
            }
            else -> true
    }
//    fun setList() {
//        todoList.value = viewModel.getList(viewModel.isTimeOrder)
//    }
}