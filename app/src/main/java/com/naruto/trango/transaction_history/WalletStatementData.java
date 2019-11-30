package com.naruto.trango.transaction_history;

public class WalletStatementData {

    private String event, date, time, amount;

    public WalletStatementData(String event, String date, String time, String amount) {
        this.amount = amount;
        this.date = date;
        this.event = event;
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
