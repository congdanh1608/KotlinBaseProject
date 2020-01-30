package com.danhtran.androidbaseproject.database.repository

import com.danhtran.androidbaseproject.database.dao.AddressDAO
import com.danhtran.androidbaseproject.database.dao.UserDAO
import com.danhtran.androidbaseproject.database.entity.Address
import com.danhtran.androidbaseproject.database.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by danhtran on 2/25/2018.
 */

class UserAddrAddrRepository(private val userDAO: UserDAO, private val addressDAO: AddressDAO) :
    UserAddrRepositoryInterface {

    val users_: List<User>
        get() = userDAO.users_


    override val users: Single<List<User>>
        get() = userDAO.users

    override val oneUser: Single<User>
        get() = userDAO.oneUser

    override val addresses: Single<Address>
        get() = addressDAO.addresses

    fun insertUser_(user: User) {
        userDAO.insertUser(user)
    }

    fun getUserById_(userId: String): Single<User> {
        return userDAO.getUserById_(userId)
    }

    override fun getUserById(userId: String): Maybe<User> {
        return userDAO.getUserById(userId)
    }

    override fun insertUser(user: User) {

        //Example for using executor
        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insertUser(user);
            }
        });*/

        userDAO.insertUser(user)
    }

    override fun deleteUserById(userId: String) {
        userDAO.deleteUserById(userId)
    }

    override fun deleteAllUsers() {
        userDAO.deleteAllUsers()
    }

    override fun getAddressById(id: String): Maybe<Address> {
        return addressDAO.getAddressById(id)
    }

    override fun insertAddress(vararg addresses: Address) {
        addressDAO.insertAddress(*addresses)
    }

    override fun updateAddress(vararg addresses: Address) {
        addressDAO.updateAddress(*addresses)
    }

    override fun deleteAddressById(id: String) {
        addressDAO.deleteAddressById(id)
    }

    override fun deleteAddress(vararg addresses: Address) {
        addressDAO.deleteAddress(*addresses)
    }

    override fun deleteAllAddress() {
        addressDAO.deleteAllAddress()
    }

    override fun getAddressForUser(userId: Int): Maybe<List<Address>> {
        return addressDAO.getAddressForUser(userId)
    }
}
