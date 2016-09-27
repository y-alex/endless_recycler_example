
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

}
