package com.example.myapplication.Gallery;

public class Item {
    private String imageUrl,tags;
    private int likes;

    public Item(String imageUrl, String tags, int likes) {
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
