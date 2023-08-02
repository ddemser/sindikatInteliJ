package com.example.springbootfirst.controller;

import com.example.springbootfirst.models.Sindikat_clanovi;
import com.example.springbootfirst.services.ClanoviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController

public class ClanoviController {
    @Autowired
    private ClanoviService clanoviService;

    @CrossOrigin
    @GetMapping("/clanovi")
    public List<Sindikat_clanovi> list() {
        
        return clanoviService.listAll();
    }

    @CrossOrigin
    @GetMapping("/clanovi/{email}")
    public ResponseEntity<Sindikat_clanovi> get(@PathVariable String email) {
        try {
            Sindikat_clanovi sindikatClanovi = clanoviService.get(email);
            System.out.println("test:" + sindikatClanovi.getEmail());
            System.out.println("test:" + sindikatClanovi.getAdresa());
            return new ResponseEntity<Sindikat_clanovi>(sindikatClanovi, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sindikat_clanovi>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/aktivacija/{email}")
    public String getAktivacija(@PathVariable String email) {
        try {
            Sindikat_clanovi sindikatClanovi = clanoviService.get(email);
            sindikatClanovi.setAktivacija("D");
            clanoviService.save(sindikatClanovi);




        return "Član je uspješno aktiviran";
        } catch (NoSuchElementException e) {
            return "Korisnik ne postoji u bazi";
        }


    }
    @CrossOrigin
    @GetMapping("/lozinka/{email}")
    public String getLozinka(@PathVariable String email) {
        try {
            Sindikat_clanovi sindikatClanovi = clanoviService.get(email);

            //compose the message
            // Recipient's email ID needs to be mentioned.
            String to = sindikatClanovi.getEmail();

            // Sender's email ID needs to be mentioned
            String from = "MobileSindikatZajedno@gmail.com";

            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass username and password
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("MobileSindikatZajedno@gmail.com", "wxgdqyqfnncnwgrb");

                }

            });

            // Used to debug SMTP issues
            session.setDebug(true);

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Zaboravljena lozinka");

                // Now set the actual message
                message.setText("Lozinka : "+sindikatClanovi.getSifra());

                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }



            return "Lozinka je uspješno poslana na upisani E-mail";
        } catch (NoSuchElementException e) {
            return "Korisnik ne postoji u bazi";
        } catch (Exception e){
            return ""+e.getMessage();
        }


    }
    @CrossOrigin
    @PostMapping("/registerclanovi")
    public ResponseEntity  add(@RequestBody Sindikat_clanovi clanovi) {

        try{
            Sindikat_clanovi sindikatClanovi = clanoviService.get(clanovi.getEmail());


           if(sindikatClanovi.getIme()!=null){
               System.out.println("postoji u bazi");
               return new ResponseEntity<>("Registrirani korisnik već postoji u bazi!",
                       HttpStatus.OK);
           }


        }catch (Exception e){
           try{
               clanovi.setKartica(clanoviService.getKartica());
               clanoviService.save(clanovi);
               posaljiLinkZaAktivaciju(clanovi.getEmail(),clanovi.getIme(),clanovi.getPrezime(),clanovi.getOib(),clanovi.getUstanovaRada(),clanovi.getAdresa(),clanovi.getMobitel(),clanovi.getDatum());
           }catch (Exception y){
               String sMessage=y.getMessage();
               System.out.println(sMessage);
               if(y.getMessage().contains("constraint [sindikat_clanovi.OIB]")){
                   sMessage="Upisani Oib postoji u bazi podataka";
               }else if(e.getMessage().contains("constraint [sindikat_clanovi.PRIMARY]")){
                   sMessage="Upisani E-mail postoji u bazi podataka";
               }
               return new ResponseEntity<>(sMessage,
                       HttpStatus.OK);
           }




        }

        return new ResponseEntity<>("Uspješno registriani član, korinsički račun je u statusu aktivacije",
                HttpStatus.OK);

    }

    @CrossOrigin
public void posaljiLinkZaAktivaciju(String eMailZaPotvrdu, String ime, String prezime, String oib, String sUstanovaRada, String sMjesto, String sMobitel, Date sDatRodj){
    try {
        System.out.println("Unutraaaa");
        String sSubject="";
        System.out.println();
        System.out.println("Unutraaaa"+clanoviService.getServer());
        sSubject=clanoviService.getServer()+"aktivacija"+"/"+eMailZaPotvrdu;
        //compose the message
        // Recipient's email ID needs to be mentioned.
        String to = clanoviService.getAdmn();
        System.out.println("Unutraaaa: "+to);
        // Sender's email ID needs to be mentioned
        String from = "MobileSindikatZajedno@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        String pattern = "dd/MM/yyyy";


        DateFormat df = new SimpleDateFormat(pattern);

        String datum = df.format(sDatRodj);

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("MobileSindikatZajedno@gmail.com", "wxgdqyqfnncnwgrb");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Aktivacija člana "+ime + " " + prezime +"("+oib+")");

            // Now set the actual message
            //message.setText("ime:"+ime+"<br/><br/> prezime:"+prezime+"<br><br>Link za aktivaciju : "+sSubject+"<br>");
            message.setContent(
                    "ime:"+ime+"<br/><br/> prezime:"+prezime+"<br/><br/> datum rodenja:"+datum+"<br/><br/>E-mail: "+eMailZaPotvrdu+"<br/><br/> oib:"+oib+"<br/><br/> mobitel:"+sMobitel+"<br/><br/> ustanova rada:"+sUstanovaRada+"<br/><br/> mjesto:"+sMjesto+"<br/><br/>Link za aktivaciju : "+sSubject+"<br>",
                    "text/html");


            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }




    } catch (NoSuchElementException e) {
        System.out.println(e.getMessage());
    } catch (Exception e){
        System.out.println(e.getMessage());
    }
}

}
