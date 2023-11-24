package it.dedagroup.biglietto;

import it.dedagroup.biglietto.repository.BigliettoCriteriaQuery;
import it.dedagroup.biglietto.repository.BigliettoRepository;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import it.dedagroup.biglietto.model.Biglietto;
import it.dedagroup.biglietto.service.def.GeneralCallService;
import static it.dedagroup.biglietto.path.UtilPath.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = VenditaBigliettiBigliettoApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
class VenditaBigliettiBigliettoApplicationTests implements GeneralCallService{

	@Autowired
	MockMvc mvc;
	@Autowired
	BigliettoRepository repo;
	@Autowired
	BigliettoCriteriaQuery criteriaQuery;
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
		String json = convertToJson(new Biglietto(LocalDate.now(),19.99,"YYYYYYYY",0, 4, 1));
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
	public void testFindByIdBigliettoSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_ID_PATH))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andReturn();
	}

	@Test
	@Order(4)
	public void testFindByIdBigliettoConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post(FIND_BY_ID_PATH+"/1"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andDo(print());
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
	@Order(10)
	public void testTrovaBigliettoBySerialeSenzaDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get(FIND_BY_SERIALE_PATH))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(print());
	}

	@Test
	@Order(11)
	public void testTrovaBigliettoBySerialeConDati() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post(FIND_BY_SERIALE_PATH+"/267960E3"))
			.andExpectAll(MockMvcResultMatchers.status().is4xxClientError())
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
		mvc.perform(MockMvcRequestBuilders.post(FIND_ALL_BY_ID_UTENTE_PATH+"/1"))
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
	public void testTrovaBigliettiFiltrati() throws Exception {
		Map<String,String> map = new HashMap<>();
		map.put("idUtente","4");
		List<Biglietto> biglietti = criteriaQuery.getBigliettiFiltrati(map);
		assertEquals(1, biglietti.size());
	}

	@Test
	@Order(18)
	public void testCountBigliettiComprati() {
		int count = repo.countByIdPrezzoSettoreEventoAndDataAcquistoIsNotNull(1);
		assertEquals(1,count);
	}
	@Test
	@Order(19)
	public void testRecuperoDistintoPrezzoBiglietto(){
		List<Double> prezziDistinti = repo.findDistinctPrezzoBigliettoByIdPrezzoSettoreEvento(1);
		assertEquals(1, prezziDistinti.size());
	}
	@Test
	@Order(20)
	public void testFindAllByIdPrezzoSettoreEventoIn(){
		List<Biglietto> biglietti = repo.findAllByIdPrezzoSettoreEventoIn(List.of(1L,2L));
		assertEquals(1,biglietti.size());
	}
}
