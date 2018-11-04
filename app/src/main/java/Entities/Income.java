package Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "income_table")
public class Income {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "income_id")
    private int id;

    @ColumnInfo(name = "income_title")
    private String title;

    @ColumnInfo(name = "income_category")
    private String category;

    @ColumnInfo(name = "income_date")
    private Long date;

    @ColumnInfo(name = "income_price")
    private Double price;

    public Income(String title, String category, Long date, Double price){
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

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return "Title = " + title + ", Date = " + date + ", Category = " + category + ", Price = " + price;
    }

}
