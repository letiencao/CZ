package com.letiencao.service.impl;

import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.letiencao.service.GenericService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

public class BaseService implements GenericService {
	public static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	public static final Long ttlMillis = 86400000L;
	public static final String UUID = "";

//	@Override
//	public String getMD5(String input) {
//		try {
//
//			// Static getInstance method is called with hashing MD5
//			MessageDigest md = MessageDigest.getInstance("MD5");
//
//			// digest() method is called to calculate message digest
//			// of an input digest() return array of byte
//			byte[] messageDigest = md.digest(input.getBytes());
//
//			// Convert byte array into signum representation
//			BigInteger no = new BigInteger(1, messageDigest);
//
//			// Convert message digest into hex value
//			String hashtext = no.toString(16);
//			while (hashtext.length() < 32) {
//				hashtext = "0" + hashtext;
//			}
//			return hashtext;
//		}
//
//		// For specifying wrong message digest algorithms
//		catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//	}

	@SuppressWarnings("deprecation")
	@Override
	public String createJWT(String issuer) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims

		JwtBuilder builder = Jwts.builder().setIssuedAt(now).setIssuer(issuer).setAudience(UUID)
				.signWith(signatureAlgorithm, signingKey);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	@Override
	public boolean validateToken(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)

		try {
			@SuppressWarnings({ "deprecation", "unused" })
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(jwt).getBody();
			if (claims != null) {
				return true;
			}
		} catch (io.jsonwebtoken.SignatureException | io.jsonwebtoken.ExpiredJwtException | MalformedJwtException e) {
			return false;
		}
		return false;
	}

	@Override
	public String getPhoneNumberFromToken(String jwt) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(jwt).getBody();

			return claims.getIssuer();

		} catch (ExpiredJwtException | IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public long convertTimestampToSeconds(Timestamp timestamp) {
		Timestamp t = new Timestamp(0);
		long seconds = timestamp.getTime() - t.getTime();
		System.out.println("seconds = " + seconds);
		return seconds;
	}

}
