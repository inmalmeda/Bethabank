package com.perdijimen.bethabank.model.response;

import com.perdijimen.bethabank.model.Category;

import java.util.List;
import java.util.Map;

public class CategoryAnalyticResponse {

    private Double totalExpenses;

    private List<CategoryAnalytic> categoryAnalytic;


    public CategoryAnalyticResponse() {
    }

    public CategoryAnalyticResponse(Double totalExpenses, List<CategoryAnalytic> categoryAnalytic) {
        this.totalExpenses = totalExpenses;
        this.categoryAnalytic = categoryAnalytic;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public List<CategoryAnalytic> getCategoryAnalytic() {
        return categoryAnalytic;
    }

    public void setCategoryAnalytic(List<CategoryAnalytic> categoryAnalytic) {
        this.categoryAnalytic = categoryAnalytic;
    }
}
