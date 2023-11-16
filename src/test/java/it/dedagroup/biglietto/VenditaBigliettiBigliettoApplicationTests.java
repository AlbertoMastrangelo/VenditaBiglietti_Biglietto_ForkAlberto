package it.dedagroup.biglietto;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dedagroup.biglietto.dto.request.BigliettoDTORequest;
import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.service.def.GeneralCallService;
import it.dedagroup.biglietto.utils.Utility;

import static it.dedagroup.biglietto.path.UtilPath.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.Clock;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



@SpringBootTest
@ContextConfiguration(classes = VenditaBigliettiBigliettoApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
class VenditaBigliettiBigliettoApplicationTests implements GeneralCallService{
	
	@Autowired 
	MockMvc mvc;
	
	@Test
	@Order(1)
	public void testSaveBigliettoSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post(INSERT_BIGLIETTO_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andDo(print());
	}
	
	@Test
	@Order(2)
	public void testSaveBigliettoConDati() throws Exception{

		String json = convertToJson(new Biglietto(2, LocalDate.of(2023, 02, 10),19.99,"YYYYYYYY",false, 4, 1));
		mvc.perform(MockMvcRequestBuilders.post(INSERT_BIGLIETTO_PATH)
				//la richiesta all'interno del body è un JSON
				.contentType(MediaType.APPLICATION_JSON)
				//è il JSON(il mio request)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
		 .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		 .andReturn();
	}
	
	@Test
	@Order(3)
	public void testFindByBigliettoSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_ID_PATH))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(4)
	public void testFindByBigliettoConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_ID_PATH+"/1"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	@Order(5)
	public void testDeleteBigliettoSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post(DELETE_BIGLIETTO_PATH))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(6)
	public void testDeleteBigliettoConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post(DELETE_BIGLIETTO_PATH+"/1"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	@Order(7)
	public void testTrovaTuttiBiglietti() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_PATH))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
		
	}
	
	@Test
	@Order(8)
	public void testTrovaBigliettoByIdAndIdUtenteSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_ID_AND_UTENTE_ID_PATH))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andReturn();
	}
	
	@Test
	@Order(9)
	public void testTrovaBigliettoByIdAndIdUtenteConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_ID_AND_UTENTE_ID_PATH+"/1/1"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andReturn();
	}
	
	@Test
	@Order(10)
	public void testTrovaBigliettoBySerialeSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_SERIALE_PATH))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(print());
	}
	
	@Test
	@Order(11)
	public void testTrovaBigliettoBySerialeConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_SERIALE_PATH+"/XXXXXXX"))
			.andExpectAll(MockMvcResultMatchers.status().is2xxSuccessful())
			.andDo(print());
	}

	@Test
	@Order(11)
	public void testTrovaListaDiBigliettiByPrezzoSuperioreASenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(12)
	public void testTrovaListaDiBigliettiByPrezzoSuperioreAConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_PREZZO_IS_GREATER_THAN_EQUAL_PATH+"/10"))
			.andExpectAll(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	@Order(13)
	public void testTrovaListaDiBigliettiByPrezzoInferioreASenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(14)
	public void testTrovaListaDiBigliettiByPrezzoInferioreAConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_PREZZO_IS_LESS_THAN_EQUAL_PATH+"/1000"))
			.andExpectAll(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	@Order(15)
	public void testTrovaListaDiBigliettiByUtenteSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_ID_UTENTE_PATH))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(16)
	public void testTrovaListaDiBigliettiByUtenteConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_ID_UTENTE_PATH+"/1"))
			.andExpectAll(MockMvcResultMatchers.status().is2xxSuccessful())
			.andReturn();
	}
	
	@Test
	@Order(17)
	public void testTrovaListaDiBigliettiByDataDiAcquistoSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_DATA_ACQUISTO))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	@Order(18)
	public void testTrovaListaDiBigliettiByDataDiAcquistoConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_ALL_BY_ID_UTENTE_PATH+LocalDate.now()))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}
	
	
	
}
