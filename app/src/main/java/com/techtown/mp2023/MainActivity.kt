package com.techtown.mp2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.techtown.mp2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter
    lateinit var viewModel : TodoViewModel
    lateinit var todoList: MutableLiveData<MutableList<Todo>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뷰모델 받아오기
        viewModel = ViewModelProvider(this, ViewModelProviderFactory(this.application))
            .get(TodoViewModel::class.java)

        //recycler view에 보여질 아이템 Room에서 받아오기
        todoList = viewModel.mutableLiveData
        todoList.observe(this, Observer {
            adapter.itemList = it
            adapter.notifyDataSetChanged()
        })

        adapter = MyAdapter(this, mutableListOf<Todo>(), viewModel, ::setList) //::goToDetail, ::setList)

        //recycler view에 adapter와 layout manager 넣기
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)


        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        setList()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // RecyclerView의 item을 눌릴 때 상세페이지로 들어가지게끔 하는 함수. Adapter의 인자로 넣어줌.
    fun goToDetail(todo: Todo, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("todo", todo)
        intent.putExtra("data", bundle)
        intent.putExtra("position", position)
//        startActivityForResult(intent, RC_GO_TO_DETAIL)
        startActivity(intent)
    }

    // 화면을 다시 돌리기 위해 viewModel 내에 있는 LiveData의 value를 변경시켜줌.
    // value가 변경됨에 따라 observer에 설정된 함수가 실행되고 UI가 변경됨.
    fun setList() {
        todoList.value = viewModel.getList(viewModel.isTimeOrder)
    }
}