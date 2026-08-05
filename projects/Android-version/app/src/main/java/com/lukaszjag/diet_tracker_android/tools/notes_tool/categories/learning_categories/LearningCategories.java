package com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.learning_categories;

public class LearningCategories {
    private String categoryName;
    private boolean isMainCategory;
    private boolean iSubCategory;

    public LearningCategories(String categoryName, boolean isMainCategory, boolean iSubCategory) {
        this.categoryName = categoryName;
        this.isMainCategory = isMainCategory;
        this.iSubCategory = iSubCategory;
    }

    @Override
    public String toString() {
        return categoryName != null ? categoryName : "Uncategorized";
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isMainCategory() {
        return isMainCategory;
    }

    public void setMainCategory(boolean mainCategory) {
        isMainCategory = mainCategory;
    }

    public boolean isiSubCategory() {
        return iSubCategory;
    }

    public void setiSubCategory(boolean iSubCategory) {
        this.iSubCategory = iSubCategory;
    }
}
