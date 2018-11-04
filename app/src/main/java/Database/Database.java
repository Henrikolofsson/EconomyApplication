package Database;

import android.arch.persistence.room.RoomDatabase;

import Entities.Expense;
import Entities.Income;
import Entities.User;

@android.arch.persistence.room.Database(entities = {Expense.class, Income.class, User.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DatabaseAccess databaseAccess();
}
