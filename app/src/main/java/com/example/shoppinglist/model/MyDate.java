package com.example.shoppinglist.model;

import androidx.annotation.Nullable;

public class MyDate {

    // region 0. Constants

    private static final int DEFAULT_YEAR = 1;
    private static final int DEFAULT_MONTH = 1;
    private static final int DEFAULT_DAY = 1;

    private static final int[] MONTHS_LENGTH = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    private static final String[] MONTHS = {
            "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"
    };

    // endregion

    // region 1. Decl and Init

    private int year;
    private int month;
    private int day;

    // endregion

    // region 2. Constructor

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MyDate(String strDate){
        StringBuilder strYear = new StringBuilder();
        StringBuilder strMonth = new StringBuilder();
        StringBuilder strDay = new StringBuilder();
        int ind = 0;

        while (ind < strDate.length() && strDate.charAt(ind) != '.') {
            strYear.append(strDate.charAt(ind));
            ind ++;
        }
        this.year = Integer.parseInt(strYear.toString());

        ind += 2;
        while (ind < strDate.length() && strDate.charAt(ind) != '.') {
            strMonth.append(strDate.charAt(ind));
            ind ++;
        }
        for (int i = 0; i < MONTHS.length; i++) {
            if (strMonth.toString().equals(MONTHS[i])){
                this.month = i+1;
                break;
            }
        }

        ind += 2;
        while (ind < strDate.length() && strDate.charAt(ind) != '.') {
            strDay.append(strDate.charAt(ind));
            ind ++;
        }
        this.day = Integer.parseInt(strDay.toString());
    }

    public MyDate(){
        this.year = DEFAULT_YEAR;
        this.month = DEFAULT_MONTH;
        this.day = DEFAULT_DAY;
    }

    // endregion

    // region 3. Getters and Setters

    public boolean isDefault(){
        return (this.year == DEFAULT_YEAR
                && this.month == DEFAULT_MONTH
                && this.day == DEFAULT_DAY);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public MyDate getOneDayEarlier(){
        int year = this.year;
        int month = this.month;
        int day = this.day;

        day--;
        if (day == 0){
            month--;
            day = MONTHS_LENGTH[(month - 1 + 12) % 12];
            if (day == 28 && year % 4 == 0){
                day = 29;
            }

            if (month == 0){
                month = 12;
                year--;
            }
        }

        return new MyDate(year, month, day);
    }

    // endregion

    // region 4. Other methods

    @Override
    public String toString() {
        String strDate = "";
        strDate = this.year + ". " + MONTHS[this.month - 1] + ". " + this.day + ".";
        return strDate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        MyDate date = (MyDate) obj;
        if (date == null)
            return false;

        return (
                date.getYear() == this.year
                && date.getMonth() == this.month
                && date.getDay() == this.day
                );
    }

    // endregion

}