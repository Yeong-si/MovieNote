// firestore를 위한 Note 컬랙션을 클래스로 구현함

package com.example.movienote;

import java.io.File;
import java.util.Calendar;

public class Note {
    private String writer;
    private String movieTitle;
    private Calendar calendar;
    private float rating;
    private boolean visible;
    private String noteTitle;
    private String comment;
    private File note;

    public Note() {}
    public Note(String writer,String movieTitle,Calendar calendar,float rating,boolean visible,String noteTitle,String comment,File note) {
        this.writer = writer;
        this.calendar = calendar;
        this.note = note;
        this.comment = comment;
        this.noteTitle = noteTitle;
        this.visible = visible;
        this.movieTitle = movieTitle;
        this.rating = rating;
    }

    public String getWriter() {return writer;}
    public void setWriter(String writer) {this.writer = writer;}

    public Calendar getCalendar() {return calendar;}
    public void setCalendar(Calendar calendar) {this.calendar = calendar;}

    public File getNote() {return note;}
    public void setNote(File note) {this.note = note;}

    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}

    public String getNoteTitle() {return noteTitle;}
    public void setNoteTitle(String noteTitle) {this.noteTitle = noteTitle;}

    public boolean getVisible() {return visible;}
    public void setVisible(boolean visible) {this.visible = visible;}

    public String getMovieTitle() {return movieTitle;}
    public void setMovieTitle(String movieTitle) {this.movieTitle = movieTitle;}

    public float getRating() {return rating;}
    public void setRating(float rating) {this.rating = rating;}
}