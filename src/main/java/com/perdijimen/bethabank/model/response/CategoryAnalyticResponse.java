package com.perdijimen.bethabank.model.response;

import com.perdijimen.bethabank.model.Category;

import java.util.Map;

public class CategoryAnalyticResponse {

    private Double totalExpenses;

    private Map<Category, Double> categoryExpenseList;

    public CategoryAnalyticResponse() {
    }

    public CategoryAnalyticResponse(Double totalExpenses, Map<Category, Double> categoryExpenseList) {
        this.totalExpenses = totalExpenses;
        this.categoryExpenseList = categoryExpenseList;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Map<Category, Double> getCategoryExpenseList() {
        return categoryExpenseList;
    }

    public void setCategoryExpenseList(Map<Category, Double> categoryExpenseList) {
        this.categoryExpenseList = categoryExpenseList;
    }
}
