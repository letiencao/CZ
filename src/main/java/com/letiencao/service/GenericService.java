package com.letiencao.service;

public interface GenericService {
	String getMD5(String input);
	String createJWT(String issuer);
	boolean validateToken(String jwt);
	String getPhoneNumberFromToken(String jwt);
}
