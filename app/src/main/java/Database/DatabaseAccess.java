package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import Entities.Expense;
import Entities.Income;

@Dao
public interface DatabaseAccess {

    @Insert
    void insertIncome(Income... income);

    @Insert
    void insertExpense(Expense... expense);

    @Query("SELECT * FROM income_table WHERE income_id = :incomeId")
    Income getIncome(int incomeId);

    @Query("SELECT * FROM expense_table WHERE expense_id = :expenseId")
    Expense getExpense(int expenseId);

    @Query("SELECT * FROM income_table ORDER BY income_date")
    List<Income> getAllIncomes();

    @Query("SELECT * FROM expense_table ORDER BY expense_date")
    List<Expense> getAllExpenses();

    @Query("SELECT * FROM income_table WHERE income_date BETWEEN :dateFrom and :dateTo ORDER BY income_date")
    List<Income> filterIncome(long dateFrom, long dateTo);

    @Query("SELECT * FROM expense_table WHERE expense_date BETWEEN :dateFrom and :dateTo ORDER BY expense_date")
    List<Expense> filterExpense(long dateFrom, long dateTo);

    @Query("DELETE FROM EXPENSE_TABLE WHERE expense_id = :expenseId")
    void deleteExpense(int expenseId);

    @Query("DELETE FROM income_table WHERE income_id = :incomeId")
    void deleteIncome(int incomeId);
}