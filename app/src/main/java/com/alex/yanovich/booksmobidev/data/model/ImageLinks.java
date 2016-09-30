
package com.alex.yanovich.booksmobidev.data.model;

public class ImageLinks {

    private String smallThumbnail="";

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageLinks)) return false;

        ImageLinks that = (ImageLinks) o;

        return getSmallThumbnail() != null ? getSmallThumbnail().equals(that.getSmallThumbnail()) : that.getSmallThumbnail() == null;

    }

    @Override
    public int hashCode() {
        return getSmallThumbnail() != null ? getSmallThumbnail().hashCode() : 0;
    }
}
