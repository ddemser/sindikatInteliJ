package com.example.springbootfirst.models;

import javax.persistence.*;
import java.util.Date;

@Table //is a corresponding table that matches that entity in the database
@Entity // for specifies class is an entity and is mapped to a database table.
//@Data // for getter and seter
public class Sindikat_clanovi  {

    private String ime ;
    private String prezime ;

    public String getKartica() {
        return kartica;
    }

    public void setKartica(String kartica) {
        this.kartica = kartica;
    }

    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private java.util.Date datum;

    private String adresa ;

    private String oib ;

    private String email ;

    private String mobitel ;

    private String ustanovaRada ;

    private String sifra ;

    private String aktivacija;

    private String kartica ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    public Sindikat_clanovi() {
    }


    public Sindikat_clanovi(String kartica,String email) {
        this.kartica = kartica;
        this.email = email;
    }



    public Sindikat_clanovi(int id, String ime, String prezime, java.util.Date datum, String adresa, String oib, String email, String mobitel, String ustanovaRada, String sifra, String aktivacija, String kartica) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.datum = datum;
        this.adresa = adresa;
        this.oib = oib;
        this.email = email;
        this.mobitel = mobitel;
        this.ustanovaRada = ustanovaRada;
        this.sifra = sifra;
        this.aktivacija = aktivacija;
        this.kartica = kartica;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }


    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobitel() {
        return mobitel;
    }

    public void setMobitel(String mobitel) {
        this.mobitel = mobitel;
    }

    public String getUstanovaRada() {
        return ustanovaRada;
    }

    public void setUstanovaRada(String ustanovaRada) {
        this.ustanovaRada = ustanovaRada;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getAktivacija() {
        return aktivacija;
    }

    public void setAktivacija(String aktivacija) {
        this.aktivacija = aktivacija;
    }



}

