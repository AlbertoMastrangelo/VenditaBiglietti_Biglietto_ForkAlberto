package it.dedagroup.biglietto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import it.dedagroup.biglietto.dto.request.BigliettoDTORequest;
import it.dedagroup.biglietto.dto.response.BigliettoDTOResponse;
import it.dedagroup.biglietto.model.Biglietto;

@Component
public class BigliettoMapper {
	
	public BigliettoDTOResponse toDTO (Biglietto b) {
		if(b==null)return null;
		BigliettoDTOResponse biglietto = new BigliettoDTOResponse();
		biglietto.setDataAcquisto(b.getDataAcquisto());
		biglietto.setPrezzo(b.getPrezzo());
		biglietto.setSeriale(b.getSeriale());
		biglietto.setVersion(b.getVersion());
		return biglietto;
	}
	
	public Biglietto fromDTORequestToBiglietto (BigliettoDTORequest b) {
		if(b==null) return null;
		Biglietto biglietto = new Biglietto();
		biglietto.setDataAcquisto(b.getDataAcquisto());
		biglietto.setPrezzo(b.getPrezzo());
		biglietto.setSeriale(b.getSeriale());
		biglietto.setIsCancellato(false);
		biglietto.setIdUtente(b.getIdUtente());
		return biglietto;
	}
	
	public List<BigliettoDTOResponse> fromBigliettoListtToBigliettoDTOResponseList(List<Biglietto> biglietti){
		return biglietti.stream().map(this::toDTO).toList();
	}
}
