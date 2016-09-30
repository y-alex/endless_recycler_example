package com.alex.yanovich.booksmobidev.data.model;

public class VolumeInfo {

    private String title="";
    private ImageLinks imageLinks = new ImageLinks();
    private String infoLink="";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VolumeInfo)) return false;

        VolumeInfo that = (VolumeInfo) o;

        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null)
            return false;
        if (getImageLinks() != null ? !getImageLinks().equals(that.getImageLinks()) : that.getImageLinks() != null)
            return false;
        return getInfoLink() != null ? getInfoLink().equals(that.getInfoLink()) : that.getInfoLink() == null;

    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getImageLinks() != null ? getImageLinks().hashCode() : 0);
        result = 31 * result + (getInfoLink() != null ? getInfoLink().hashCode() : 0);
        return result;
    }
}
