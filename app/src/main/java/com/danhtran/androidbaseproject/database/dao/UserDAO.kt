package com.danhtran.androidbaseproject.database.dao

import androidx.room.*
import com.danhtran.androidbaseproject.database.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by danhtran on 2/25/2018.
 */
@Dao
interface UserDAO {
    //example for thread pool or AsyncTask
    @get:Query("SELECT * FROM User")
    val users_: List<User>

    @get:Query("SELECT * FROM User")
    val users: Single<List<User>>

    @get:Query("SELECT * FROM User LIMIT 1")
    val oneUser: Single<User>

    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUserById_(userId: String): Single<User>

    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUserById(userId: String): Maybe<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    // Insert multiple items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg users: User): LongArray

    @Query("DELETE FROM User WHERE id = :userId")
    fun deleteUserById(userId: String)

    @Query("DELETE FROM User")
    fun deleteAllUsers()

    @Delete
    fun deleteUser(vararg users: User)

    @Update
    fun updateUser(vararg users: User)
}
