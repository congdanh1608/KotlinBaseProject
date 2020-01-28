package com.glide.androidbaseproject.database.dao

import androidx.room.*
import com.glide.androidbaseproject.database.entity.Address
import io.reactivex.Maybe
import io.reactivex.Single


/**
 * Created by danhtran on 2/26/2018.
 */
@Dao
interface AddressDAO {
    @get:Query("SELECT * FROM Address")
    val addresses: Single<Address>

    @Query("SELECT * FROM Address WHERE id = :id")
    fun getAddressById(id: String): Maybe<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(vararg addresses: Address)

    @Update
    fun updateAddress(vararg addresses: Address)

    @Query("DELETE FROM Address WHERE id = :id")
    fun deleteAddressById(id: String)

    @Delete
    fun deleteAddress(vararg addresses: Address)

    @Query("DELETE FROM Address")
    fun deleteAllAddress()

    @Query("SELECT * FROM Address WHERE userid = :userId")
    fun getAddressForUser(userId: Int): Maybe<List<Address>>
}
