// firestore를 위한 Note 컬랙션을 클래스로 구현함

package com.example.movienote;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public class Note {

    private String poster; //포스터
    private String uid; // userId
    private String writer; // 작성자
    private String movieTitle; // 영화 제목
    private String calendar; //캘린더
    private float rating; // 평점
    private boolean visible; // 공개 여부
    private String noteTitle; // 감상문 제목
    private String comment; // 기억하고 싶은 나의 한 줄
    private String note; // 본문
    private String genre; //장르

    private @NonNull LocalDateTime timestamp;

    public Note() {}

    public Note(String poster,String uid,String writer, String movieTitle, String calendar, float rating, boolean visible, String noteTitle, String comment, String note,@NonNull LocalDateTime timestamp,String genre) {
        this.poster = poster;
        this.writer = writer;
        this.uid = uid;
        this.movieTitle = movieTitle;
        this.calendar = calendar;
        this.rating = rating;
        this.visible = visible;
        this.noteTitle = noteTitle;
        this.comment = comment;
        this.note = note;
        this.timestamp = timestamp;
        this.genre = genre;
    }

    @NonNull
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNoteTitle() {
        return noteTitle;
    }
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getGenre(){
        return genre;
    }
}