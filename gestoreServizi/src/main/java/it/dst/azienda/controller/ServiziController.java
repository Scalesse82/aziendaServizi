package it.dst.azienda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.dst.azienda.configuration.JwtAuthenticationRequest;
import it.dst.azienda.configuration.JwtTokenUtil;
import it.dst.azienda.memento.CareTaker;
import it.dst.azienda.memento.Memento;
import it.dst.azienda.model.Servizio;
import it.dst.azienda.model.Utente;
import it.dst.azienda.service.JwtAuthenticationResponse;
import it.dst.azienda.service.RuoloServiceDAO;
import it.dst.azienda.service.ServizioServiceDAO;
import it.dst.azienda.service.UtenteServiceDAO;

@RestController
public class ServiziController {
	@Autowired
	private UtenteServiceDAO utenteService;
	@Autowired
	private ServizioServiceDAO servizioService;
	

    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private CareTaker careTaker;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "public/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, JsonProcessingException {

        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genero Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        response.setHeader(tokenHeader,token);
        // Ritorno il token
        return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
    }

    @RequestMapping(value = "protected/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(tokenHeader);
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            response.setHeader(tokenHeader,refreshedToken);

            return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        
    }
    @PostMapping(value = "public/registrazione")
    public boolean registrazioneUtente(@RequestBody Utente utente) {
    	utenteService.add(utente);
    	return true;
    }
    @PostMapping(value = "public/registrazioneAdmin")
    public boolean registrazioneAdmin(@RequestBody Utente utente) {
    	utenteService.addAdmin(utente);
    	return true;
    }
    @PostMapping(value= "protected/servizi")
    public boolean creaServizi(@RequestBody Servizio servizio, HttpServletRequest request, HttpServletResponse response) {
    	
    	 String token = request.getHeader(tokenHeader);
         UserDetails userDetails =jwtTokenUtil.getUserDetails(token);
         
         if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        	Servizio aggiunto = servizioService.add(servizio);
        	careTaker.getMementoList().add(new Memento(aggiunto));
        	
        	 return true;
		}
    	return false;
    	
    }

    @PostMapping(value="protected/verifica")
    public List<Servizio> verificaServizi(HttpServletRequest request, HttpServletResponse response){
    	 String token = request.getHeader(tokenHeader);
         UserDetails userDetails =jwtTokenUtil.getUserDetails(token);
         if(! userDetails.getAuthorities().isEmpty()) {
    	 return servizioService.findDisponibili();
         }
    	return null;
    }
    
    @PostMapping(value="protected/iscrizione")
    public boolean iscrizioneServizi(@RequestBody Long id, HttpServletRequest request, HttpServletResponse response) {
   	 String token = request.getHeader(tokenHeader);
     UserDetails userDetails =jwtTokenUtil.getUserDetails(token);
     
     if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("UTENTE"))) {
    	 utenteService.addServizio(utenteService.findByUsername(jwtTokenUtil.getUsernameFromToken(token)),servizioService.findById(id));
    	 return true;
	}
	return false;
	
    	
    }
    @PostMapping(value="protected/reset")
    public boolean resetServizi(HttpServletRequest request, HttpServletResponse response) {
    	 String token = request.getHeader(tokenHeader);
         UserDetails userDetails =jwtTokenUtil.getUserDetails(token);
         
         if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
        	 for (Memento memento : careTaker.getMementoList()) {
				servizioService.add(memento.getServizio());
			}
        	 utenteService.resetAll();
        	 return true;
         }
         return false;
    }
}
