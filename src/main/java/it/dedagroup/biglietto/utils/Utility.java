package it.dedagroup.biglietto.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utility {
	public static String creaSeriale (LocalDate data, long idUtente) {
		return UUID.nameUUIDFromBytes((""+data+idUtente+LocalDateTime.now()).getBytes()).toString().toUpperCase().substring(0, 8);
	}


	

}
