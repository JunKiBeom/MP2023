package com.techtown.mp2023

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.techtown.mp2023.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    lateinit var viewModel : TodoViewModel
    lateinit var todoList: MutableLiveData<MutableList<Todo>>
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

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

        binding.mainFabCal.setOnClickListener {
            val intent = Intent(this, CalActivity::class.java)
            startActivity(intent)
        }
        setList()


//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
//        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24); //왼쪽 상단 버튼 아이콘 지정
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navigation_view);

    }

    // 화면을 다시 돌리기 위해 viewModel 내에 있는 LiveData의 value를 변경시켜줌.
    // value가 변경됨에 따라 observer에 설정된 함수가 실행되고 UI가 변경됨.
    fun setList() {
        todoList.value = viewModel.getList(viewModel.isTimeOrder)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView  as SearchView

        //검색 기능
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //검색어 입력버튼을 누른 후 내용이 있다면
                if (query != "" && query != null) {
                    //검색어를 통해 TodoList를 얻어옴.
                    todoList.value = viewModel.getTodosByText(query)
                } else {
                    setList()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        //검색 닫기를 누른 후 기본 TodoList로 복귀
        searchView.setOnCloseListener {
            setList()
            false
        }
        return super.onCreateOptionsMenu(menu)
    }
//
//    // RecyclerView의 item을 눌릴 때 상세페이지로 들어가지게끔 하는 함수. Adapter의 인자로 넣어줌.
//    fun goToDetail(todo: Todo, position: Int) {
//        val intent = Intent(this, DetailActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("todo", todo)
//        intent.putExtra("data", bundle)
//        intent.putExtra("position", position)
////        startActivityForResult(intent, RC_GO_TO_DETAIL)
//        startActivity(intent)
//    }

    //메뉴 이벤트 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //등록일 기준 정렬
            R.id.menu_sort_register -> {
                viewModel.isTimeOrder = false

            }
            //날짜 기준 정렬
            R.id.menu_sort_date -> {
                viewModel.isTimeOrder = true
            }
            //완료 일괄 삭제
            R.id.menu_delete_done -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("완료된 할 일 목록을 전체 지우시겠습니까?")
                    .setNegativeButton("취소", null)
                    .setPositiveButton("확인") { _, _ ->
                        for (todo in adapter.itemList) {
                            if (todo.isChecked) {
                                viewModel.delete(todo)
                            }
                        }
                        setList()
                    }
                    .show()
            }
//            android.R.id.home -> {
//                drawerLayout.openDrawer(GravityCompat.START)
//                return true
//            }
        }
        setList()
        return false
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                drawerLayout.openDrawer(GravityCompat.START)
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }


}