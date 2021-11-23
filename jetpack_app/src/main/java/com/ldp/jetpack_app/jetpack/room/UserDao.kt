package com.ldp.jetpack_app.jetpack.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by ldp.
 *
 * Date: 2021/11/23
 *
 * Summary:
 */
@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll():List<User>

    @Query("select *from user where id in(:userIds)")
    fun loadAllByIds(userIds :IntArray):List<User>

    @Query("select * from user where userName like :userName limit 1")
    fun  queryByName(userName :String) :User

    @Insert
    fun insertUsers(vararg users:User)

    @Delete
    fun delete(user :User)
}