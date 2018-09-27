package com.example.android.exercise01;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
// - основной класс по работе с БД. Этот класс д.б.абстрактным и наследовать RoomDatabase
public abstract class AppDatabase extends RoomDatabase {

    //перечисляем способы взаимодействия с БД. М.б. несколько методов
    public abstract UserDao userDao();

}
