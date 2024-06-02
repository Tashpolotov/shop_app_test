package com.example.shop_app_test.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop_app_test.domain.model.Converter
import com.example.shop_app_test.domain.model.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDB : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE selected_products ADD COLUMN isSaved INTEGER NOT NULL DEFAULT 0")
            }
        }
        fun getDatabase(context: Context): AppDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2) // Добавляем миграцию
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

