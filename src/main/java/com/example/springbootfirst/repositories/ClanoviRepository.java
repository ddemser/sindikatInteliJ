package com.example.springbootfirst.repositories;

import com.example.springbootfirst.models.Sindikat_clanovi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClanoviRepository extends JpaRepository<Sindikat_clanovi, String> {


    @Query(value = "select * from ( SELECT CAST(FLOOR(100000 + RAND() * 89999) AS UNSIGNED) AS kartica FROM sindikat_clanovi WHERE 'kartica' NOT IN (SELECT kartica FROM sindikat_clanovi WHERE kartica IS NOT NULL) LIMIT 1) as x", nativeQuery = true)
    public String findUniqueKartica();


    @Query(value = "select vrijednost from sindikat_parametri where naziv='APP_ADMIN'", nativeQuery = true)
    public String findAdmin();

    @Query(value = "select vrijednost from sindikat_parametri where naziv='APP_SERVER'", nativeQuery = true)
    public String findServer();

}
