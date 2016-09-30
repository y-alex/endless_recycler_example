
package com.alex.yanovich.booksmobidev.data.model;

public class Item {

    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return getVolumeInfo() != null ? getVolumeInfo().equals(item.getVolumeInfo()) : item.getVolumeInfo() == null;

    }

    @Override
    public int hashCode() {
        return getVolumeInfo() != null ? getVolumeInfo().hashCode() : 0;
    }
}
