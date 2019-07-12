package com.example.mvvm_sample.modules.restuarants.data.room_db_repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;

import java.util.List;


@Dao
public interface RestuarantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRestuarants(RestuarantDb restuarantDb);

    @Update
    void updateRestuarants(RestuarantDb restuarantDb);

    @Query("SELECT * FROM Restuarant")
    List<RestuarantDb> loadAllRestuarants();

    @Query("DELETE FROM Restuarant")
    void deleteAllValuesFromTable();


}
