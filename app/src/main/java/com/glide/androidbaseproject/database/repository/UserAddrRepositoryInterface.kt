package com.glide.androidbaseproject.database.repository

import com.glide.androidbaseproject.database.entity.Address
import com.glide.androidbaseproject.database.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by danhtran on 2/25/2018.
 */

interface UserAddrRepositoryInterface {
    val users: Single<List<User>>

    val oneUser: Single<User>

    val addresses: Single<Address>

    fun getUserById(userId: String): Maybe<User>

    fun insertUser(user: User)

    fun deleteUserById(userId: String)

    fun deleteAllUsers()

    fun getAddressById(id: String): Maybe<Address>

    fun insertAddress(vararg addresses: Address)

    fun updateAddress(vararg addresses: Address)

    fun deleteAddressById(id: String)

    fun deleteAddress(vararg addresses: Address)

    fun deleteAllAddress()

    fun getAddressForUser(userId: Int): Maybe<List<Address>>
}
