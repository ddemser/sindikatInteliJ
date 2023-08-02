package com.example.springbootfirst.services;

import com.example.springbootfirst.models.Sindikat_clanovi;
import com.example.springbootfirst.repositories.ClanoviRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClanoviService {

    @Autowired
    private ClanoviRepository repo;

    public List<Sindikat_clanovi> listAll() {
        return repo.findAll();
    }

    public void save(Sindikat_clanovi clanovi) {
        repo.save(clanovi);
    }

   public Sindikat_clanovi get(String id) {
        return repo.findById(id).get();
    }


    public Sindikat_clanovi getAktivacija(String id) {
        return repo.findById(id).get();
    }

    public Sindikat_clanovi getLozinka(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }


    public  String getKartica(){

        return repo.findUniqueKartica();
    }


    public  String getAdmn(){

        return repo.findAdmin();
    }

    public  String getServer(){

        return repo.findServer();
    }





}