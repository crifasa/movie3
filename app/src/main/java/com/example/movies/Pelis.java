package com.example.movies;

import java.security.PrivateKey;

public class Pelis {

    private String title;
    private String description;
    private String email;
    private String fecha;
    private String precio;
    private int thumbnail;
    private int coverPhoto;


    public Pelis(String title,int thumbnail,int coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;


    }


    public Pelis(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;

    }

    public Pelis(String title, String description, String email, String fecha, String precio, String saldo) {
        this.title = title;
        this.description = description;
        this.email = email;
        this.fecha = fecha;
        this.precio = precio;

    }

    public Pelis() {

    }


    public int getCoverPhoto() {
        return coverPhoto;
    }

        public void setCoverPhoto(int coverPhoto) {
            this.coverPhoto = coverPhoto;
        }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getEmail(){return email;}
    public String getFecha(){return fecha;}
    public String getPrecio(){return precio;}







    public void setTitle(String title) {

        this.title = title;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setThumbnail(int thumbnail) {

        this.thumbnail = thumbnail;
    }

    public void setEmail(String correo){ this.email = email; }
    public void setFecha(String fecha){ this.fecha = fecha; }
    public void setPrecio(String precio){ this.precio = precio; }






}
