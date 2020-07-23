package it.dst.azienda.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import it.dst.azienda.model.Ruolo;
import it.dst.azienda.model.Utente;

public class JwtUserFactory {
	
	private JwtUserFactory() {
    }

    public static JwtUser create(Utente user) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRuolo()),
                user.getActive()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Ruolo> authorities) {
    	Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Ruolo role : authorities) {
			roles.add(new SimpleGrantedAuthority(role.getRuolo()));
		}
		return new ArrayList<>(roles);
    }

}
