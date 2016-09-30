
package com.alex.yanovich.booksmobidev.data.model;

import java.util.ArrayList;
import java.util.List;

public class AllVolumes {
    private String kind;
    private Integer totalItems;
    private List<Item> items = new ArrayList<Item>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllVolumes)) return false;

        AllVolumes that = (AllVolumes) o;

        if (getKind() != null ? !getKind().equals(that.getKind()) : that.getKind() != null)
            return false;
        if (getTotalItems() != null ? !getTotalItems().equals(that.getTotalItems()) : that.getTotalItems() != null)
            return false;
        return getItems() != null ? getItems().equals(that.getItems()) : that.getItems() == null;

    }

    @Override
    public int hashCode() {
        int result = getKind() != null ? getKind().hashCode() : 0;
        result = 31 * result + (getTotalItems() != null ? getTotalItems().hashCode() : 0);
        result = 31 * result + (getItems() != null ? getItems().hashCode() : 0);
        return result;
    }
}
