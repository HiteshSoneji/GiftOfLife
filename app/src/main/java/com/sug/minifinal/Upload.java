package com.sug.minifinal;

public class Upload {
    private String name;
    private String imageUrl;

    public Upload(){

    }

    public Upload(String name, String imageUrl) {
        if(name.trim().equals("")){
            this.name = "No name";
        }
        else
            this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
