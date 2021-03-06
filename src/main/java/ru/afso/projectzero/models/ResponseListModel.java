package ru.afso.projectzero.models;

import java.util.List;

public class ResponseListModel<T> {

    private long total;

    private List<T> items;



    public ResponseListModel(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }



    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
