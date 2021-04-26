package com.letiencao.service;

import java.sql.Timestamp;

public interface GenericService {
//	String getMD5(String input);

	String createJWT(String issuer);

	boolean validateToken(String jwt);

	String getPhoneNumberFromToken(String jwt);
	long convertTimestampToSeconds(Timestamp timestamp);//Now - 1/1/1970
}
