package com.core.data.local.dao

import androidx.room.*
import com.core.data.local.model.ExcludedUserDB
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Data Access Object for the excluded users table.
 */
@Dao
interface ExcludedUserDao {

    @Query("SELECT * FROM excluded_users")
    fun findAll(): Single<List<ExcludedUserDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: ExcludedUserDB): Completable
}
