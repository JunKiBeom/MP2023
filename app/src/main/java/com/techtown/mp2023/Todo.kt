package com.techtown.mp2023

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Todo")
data class Todo(var text: String?, var isChecked: Boolean = false) : Serializable {
    // 등록하는 순간 System의 시간을 받음, 등록일 순 정렬에 사용
    @PrimaryKey
    var registerTime : Long = System.currentTimeMillis()

    // 날짜 기준 검색
    @ColumnInfo
    var time: String? = null
    @ColumnInfo
    var date: String? = null
    @ColumnInfo
    var dateLong: Long? = null

    @ColumnInfo
    var year: Int? = null
    @ColumnInfo
    var month: Int? = null
    @ColumnInfo
    var day: Int? = null
    @ColumnInfo
    var hour: Int? = null
    @ColumnInfo
    var minute: Int? = null
}
