package Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    private int id;

    @ColumnInfo(name = "expense_title")
    private String title;

    @ColumnInfo(name = "expense_category")
    private String category;

    @ColumnInfo(name = "expense_date")
    private Long date;

    @ColumnInfo(name = "expense_price")
    private Double price;

    public Expense(String title, String category, Long date, Double price) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Title = " + title + ", Date = " + date + ", Category = " + category + ", Price = " + price;
    }
}
