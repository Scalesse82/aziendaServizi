package it.dst.azienda;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.collection.HasItemInArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;
import it.dst.azienda.service.ServizioServiceDAO;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = StarterClass.class)
@AutoConfigureMockMvc
public class TestController {

	@Autowired
	private MockMvc mock;
	@Autowired
	private ServizioServiceDAO servizioServ;

	@Test
	public void testAddServizio() throws Exception {		
		Utente u= new Utente();
		u.setUsername("admin");
		u.setPassword("admin");
		ObjectMapper obj = new ObjectMapper();
		Servizio s=new Servizio();
		s.setNome("prova");
		s.setDescrizione("prova");
        s.setQta(10);
		MvcResult p = mock.perform(post("/public/login").content(obj.writeValueAsString(u)).contentType(MediaType.APPLICATION_JSON)).andReturn();
		System.out.println(p.getResponse().getHeader("X-Auth"));
			Integer nServizi= servizioServ.findAll().size();
		    mock.perform(post("/protected/servizi").content(obj.writeValueAsString(s)).contentType(MediaType.APPLICATION_JSON).header("X-Auth", p.getResponse().getHeader("X-Auth"))).andExpect(status().isOk()).andReturn();
			Integer nSrvizioDopoAggiunta=servizioServ.findAll().size();
            assertEquals(nSrvizioDopoAggiunta , nServizi+1);		

	}
	
	
	
	@Test
	public void testRegistrazione() throws Exception {		
		Utente u= new Utente();
		u.setUsername("utente");
		u.setPassword("utente");
		ObjectMapper obj = new ObjectMapper();
		
		MvcResult p = mock.perform(post("/public/registrazione").content(obj.writeValueAsString(u)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		    
            assertEquals("true" , p.getResponse().getContentAsString());		

	}
	
	//controlliamo se la lista non contiene un oggetto servizio chiamato "vuoto" che ha qta =0
	@Test
	public void testVerifica() throws Exception {		
		Utente u= new Utente();
		u.setUsername("admin");
		u.setPassword("admin");
		Servizio s=new Servizio();
		s.setId((long) 2);
		s.setNome("vuoto");
		s.setDescrizione("vuoto");
		s.setQta(0);
		ObjectMapper obj = new ObjectMapper();
		MvcResult p = mock.perform(post("/public/login").content(obj.writeValueAsString(u)).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MvcResult result= mock.perform(post("/protected/verifica").header("X-Auth", p.getResponse().getHeader("X-Auth"))).andExpect(status().isOk()).andReturn();
		    
        List<Servizio> lista=Arrays.asList(obj.readValue(result.getResponse().getContentAsString(), Servizio[].class));
        assertTrue(!lista.contains(s));
        

	}
	
	
	
	
}
