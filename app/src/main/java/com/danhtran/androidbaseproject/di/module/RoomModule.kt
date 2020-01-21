package com.danhtran.androidbaseproject.di.module

import android.content.Context
import androidx.room.Room
import com.danhtran.androidbaseproject.database.dao.AddressDAO
import com.danhtran.androidbaseproject.database.dao.UserDAO
import com.danhtran.androidbaseproject.database.db.UserAddressDatabase
import com.danhtran.androidbaseproject.database.repository.UserAddrAddrRepository
import com.danhtran.androidbaseproject.di.scope.RoomScope
import com.danhtran.androidbaseproject.extras.Constant
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by danhtran on 2/25/2018.
 */
@Module
class RoomModule {

    /*@RoomScope
    @Provides
    public Executor getExecutor() {
        return Executors.newFixedThreadPool(2);
    }*/

    val compositeDisposable: CompositeDisposable
        @RoomScope
        @Provides
        get() = CompositeDisposable()

    @Provides
    @RoomScope
    //Example to use executor
    //    public UserAddrAddrRepository getUserRepository(UserDAO userDAO, Executor executor) {
    //Example to use DAO with RxJava
    fun getUserRepository(userDAO: UserDAO, addressDAO: AddressDAO): UserAddrAddrRepository {
        return UserAddrAddrRepository(userDAO, addressDAO)
    }

    @RoomScope
    @Provides
    fun getUserDAO(userDatabase: UserAddressDatabase): UserDAO {
        return userDatabase.userDAO()
    }

    @RoomScope
    @Provides
    fun getAddressDAO(userDatabase: UserAddressDatabase): AddressDAO {
        return userDatabase.addressDAO()
    }

    @RoomScope
    @Provides
    fun getUserDatabase(context: Context): UserAddressDatabase {
        return Room.databaseBuilder(
            context,
            UserAddressDatabase::class.java,
            Constant.DATABASE_NAME
        )
            .build()
    }

}
