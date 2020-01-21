package com.danhtran.androidbaseproject.database.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by danhtran on 2/25/2018.
 */
@Entity
//@Entity(tableName = "users")                          --> set name for table
//@Entity(primaryKeys = {"firstName", "lastName"})      --> using many fields are primaryKey
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    //    @ColumnInfo(name = "user_name")               --> set field name
    var username: String? = null
    var fullName: String? = null
    var phone: String? = null

    //Don't want to persist
    @Ignore
    var avatar: Bitmap? = null
    @Ignore
    var address: Address? = null
}
