package de.syslord.microservices.webhooksexample.security.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUserFactory {

	public static UserDetails createUserOrNull(UserAccount account) {

		if (account != null && account.getPermissions() != null) {
			String roles = account.getPermissions();
			List<GrantedAuthority> grants = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

			return createUser(account, grants);

		} else {
			return null;
		}
	}

	private static User createUser(UserAccount account, List<GrantedAuthority> grants) {
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new User(
				account.getName(),
				account.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				grants);
	}
}
