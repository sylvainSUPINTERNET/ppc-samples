package com.ppcsamples.apisampler.DTO;

import org.springframework.stereotype.Component;

@Component
public class UserDetailsDTO {
    int sub;
    String name;
    String given_name;
    String family_name;
    String picture;
    String email;
    boolean email_verified;
    String locale;
    int iat;

    UserDetailsDTO(){}


    public String getName(){
        return this.name;
    }
    public int getSub() {
        return this.sub;
    }
    public String getGivenName(){
        return this.given_name;
    }
    public String getFamilyName(){
        return this.family_name;
    }
    public String getPicture(){
        return this.picture;
    }
    public String getEmail(){
        return this.email;
    }
    public boolean getEmailVerified(){
        return this.email_verified;
    }
    public String getLocale(){
        return this.locale;
    }
    public int getIat(){
        return this.iat;
    }

}   

// {sub=114706333881848131803, name=Sylvain Joly, given_name=Sylvain, family_name=Joly, picture=https://lh5.googleusercontent.com/-a4ImIYU-7wU/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucm-zwF2Nrbv6ke4xLMu-opErqbF7w/s96-c/photo.jpg, email=sylvain.joly00@gmail.com, email_verified=true, locale=fr, iat=1609509965}

