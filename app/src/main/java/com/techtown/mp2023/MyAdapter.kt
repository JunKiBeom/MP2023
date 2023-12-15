package com.techtown.mp2023

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.techtown.mp2023.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)
class MyAdapter(val context: Context,
                var itemList: MutableList<Todo>,
                val viewModel: TodoViewModel,
//                val goToDetailListener : (Todo, Int) -> Unit,
                val setList: () -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
//        binding.itemData.text = itemList!![position]

        // position에 해당하는 Todo객체를 얻음
        val todo = itemList[position]
        val todoInfo = binding.itemRoot
        val todoText = binding.itemData
        val todoTime = binding.itemTime
        val todoIsDone: CheckBox = binding.itemChecked

        todoText.text = todo.text


        // todo객체의 isDone을 CheckBox의 isChecked에 set해줌
        todoIsDone.isChecked = todo.isChecked

        // todo가 완료(done)된 상태라면 todo_text의 글자색 변경 후 취소선을 추가
        if (todo.isChecked) {
            todoText.apply {
                setTextColor(Color.GRAY)
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTypeface(null, Typeface.ITALIC)
            }
        } else {
            // 완료상태가 아니라면 글자색 복구, 취소선 없앰
            todoText.apply {
                setTextColor(Color.BLACK)
                paintFlags = 0
                setTypeface(null, Typeface.NORMAL)
            }
        }

        // CheckBox인 todoIsDone이 클릭되었을 때
        todoIsDone.apply {
            setOnClickListener {
                todo.isChecked = this.isChecked
                viewModel.update(todo)
                //변경이 완료되었으므로 다시 todoList를 받아온 후
                // liveData.value에 넣어주는 setList메서드 실행
                setList()
            }
        }

        //할일이 적히는 todo_text를 감싸고있는 todoInfo를 클릭하면 DetailActivity 실행
//        todoInfo.setOnClickListener {
//            goToDetailListener(todo, position)
//        }

        //시간을 설정한적이 있는 경우 todo의 날짜와 시간이 표시됨.
        if (todo.time != null && todo.date != null) {
            todoTime.apply {
                text = "${todo.date} ${todo.time}"
                visibility = View.VISIBLE
            }
        } else {
            todoTime.apply {
                text = ""
                visibility = View.GONE
            }
        }

//        //아이템 내의 x버튼을 누를 경우 삭제여부 확인.
//        todoDelete.setOnClickListener {
//            val alertDialog = AlertDialog.Builder(context)
//                .setMessage("정말 삭제하시겠습니까?")
//                .setPositiveButton("삭제") {str, dialogInterface ->
//                    val todo = itemList[position]
//                    viewModel.delete(todo)
//                    setList()
//                }
//                .setNegativeButton("취소",null)
//            alertDialog.show()
//        }
    }
}
