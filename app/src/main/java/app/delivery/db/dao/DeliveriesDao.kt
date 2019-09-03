package app.delivery.db.dao

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.delivery.model.DeliveriesData

/**
 * Dao class to perform database operations
 */
@Dao
@WorkerThread
interface DeliveriesDao {

    @Query("SELECT * from delivery_table ORDER BY id ASC")
    fun getAllDelieveries(): DataSource.Factory<Int, DeliveriesData>

    @Query("SELECT * from delivery_table ORDER BY id ASC")
    fun getDelieveries(): List<DeliveriesData>

    @Query("SELECT * from delivery_table Where id=:id")
    fun getDelieveryData(id: Int): DeliveriesData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DeliveriesData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<DeliveriesData>): LongArray

    @Query("DELETE FROM delivery_table")
    fun deleteAll(): Int

    @Query("DELETE FROM delivery_table where id>:id")
    fun deleteByIdCondition(id: Int): Int

    @Query("SELECT count(*) from delivery_table")
    fun getCount(): Int
}