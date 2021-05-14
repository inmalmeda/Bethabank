package com.perdijimen.bethabank.model.response;

import com.perdijimen.bethabank.model.Category;

public class CategoryAnalytic {

    private Long idCategory;

    private String nameCategory;

    private Double expenses;

    public CategoryAnalytic() {
    }

    public CategoryAnalytic(Long idCategory, String nameCategory, Double expenses) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.expenses = expenses;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
