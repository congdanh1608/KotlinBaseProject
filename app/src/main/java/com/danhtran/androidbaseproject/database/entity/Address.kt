package com.danhtran.androidbaseproject.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

/**
 * Created by danhtran on 2/26/2018.
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
class Address {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var userId: Int = 0
    var houseNumber: String? = null
    var street: String? = null
    var city: String? = null
    var country: String? = null
}