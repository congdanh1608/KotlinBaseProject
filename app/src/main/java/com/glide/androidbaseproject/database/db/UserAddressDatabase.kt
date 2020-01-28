package com.glide.androidbaseproject.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glide.androidbaseproject.database.dao.AddressDAO
import com.glide.androidbaseproject.database.dao.UserDAO
import com.glide.androidbaseproject.database.entity.Address
import com.glide.androidbaseproject.database.entity.User

/**
 * Created by danhtran on 2/25/2018.
 */
@Database(entities = [User::class, Address::class], version = 1, exportSchema = false)
abstract class UserAddressDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    abstract fun addressDAO(): AddressDAO
}