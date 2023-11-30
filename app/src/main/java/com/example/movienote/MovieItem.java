package com.example.movienote;

public class MovieItem {
    private String title;
    private String image;
//        private String genre;
//        private String director;


    //Movie movie = new Movie(title, link, image, pubDate, director, actor, rating);

    public MovieItem(String title, String image){
        this.title = title;
        this.image = image;
        //this.director = director;
        // , String director
    }
    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for image
    public String getImage() {
        return image;
    }

    // Setter for image
    public void setImage(String image) {
        this.image = image;
    }
}
