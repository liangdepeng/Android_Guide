package com.ldp.jetpack_app.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ldp.
 *
 * Date: 2021/11/23
 *
 * Summary: 以下代码段包含具有一个实体和一个 DAO 的示例数据库配置。
 */
@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userName") val userName: String?,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "sex") val sex: String?="未知"
)
