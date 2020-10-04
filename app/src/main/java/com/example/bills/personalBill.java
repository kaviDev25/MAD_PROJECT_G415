package com.example.bills;

public class personalBill {

    private int id;
    private double amount,PaidAmount;
    private String dueDate;


    public personalBill() {
    }

    public personalBill(String category,double amount,String dueDate) {
        this.category = category;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public personalBill(int id,String category,double amount,String dueDate) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public personalBill(int id,String category,double amount,String dueDate,double paidAmount) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.dueDate = dueDate;
        this.PaidAmount = paidAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        PaidAmount = paidAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
