package it.dedagroup.biglietto.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utility {
	// Data Acquisto - ID Biglietto - ID Utente
	// 
	
	public static String creaSeriale (LocalDate data, long idUtente, long idBiglietto) {
		//return UUID.nameUUIDFromBytes((nomeLuogo + nomeEvento + data + idBiglietto + idUtente).getBytes()).toString().toUpperCase().substring(0, 8);
		return UUID.nameUUIDFromBytes((""+data+idUtente+idBiglietto+LocalDateTime.now()).getBytes()).toString().toUpperCase().substring(0, 8);
		
	}
	
//	public static void main(String[] args) {
//		
//
//		String s = creaSeriale(LocalDate.now(), 5, 102);
//		String c = creaSeriale(LocalDate.now(), 4, 103);
//		System.out.println(s);
//		System.out.println(c);
//	}


	

}
