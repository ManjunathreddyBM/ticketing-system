package com.tms.ticketing_system.jwt;

import java.security.Key;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class KeyGenerator {
	public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // or HS384, HS512
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("MANJU"+base64Key+"MANJU"); // Use this as your key
    }
}
