package com.example.bills;

public class ModelClass {
    private int id;
    private String type,amount;
    private long started,finished;

    public ModelClass(int id, String type, String amount, long started, long finished) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.started = started;
        this.finished = finished;

    }public ModelClass(String type, String amount, long started, long finished) {
        this.type = type;
        this.amount = amount;
        this.started = started;
        this.finished = finished;
    }

    public ModelClass() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }



}





