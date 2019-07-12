package com.example.mvvm_sample.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import com.example.mvvm_sample.common.MyApplication;
import com.example.mvvm_sample.modules.restuarants.data.room_db_repository.RestuarantDao;
import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;


@Database(entities = {RestuarantDb.class}, version = 2, exportSchema = false)
public abstract class RestuarantDataBase extends RoomDatabase {

    private static RestuarantDataBase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Restuarant "
                    + " ADD COLUMN restuarant_Locality TEXT");
            database.execSQL("ALTER TABLE Restuarant "
                    + " ADD COLUMN restuarant_averageRating TEXT");
        }
    };


    public static RestuarantDataBase getDatabase() {
        if (INSTANCE == null) {
            synchronized (RestuarantDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApplication.getContext(),
                            RestuarantDataBase.class, "Restuarant_Database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public abstract RestuarantDao restuarantDao();
}
