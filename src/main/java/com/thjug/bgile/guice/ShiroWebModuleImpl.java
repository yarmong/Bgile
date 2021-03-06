/*
 * Attribution
 * CC BY
 * This license lets others distribute, remix, tweak,
 * and build upon your work, even commercially,
 * as long as they credit you for the original creation.
 * This is the most accommodating of licenses offered.
 * Recommended for maximum dissemination and use of licensed materials.
 *
 * http://creativecommons.org/licenses/by/3.0/
 * http://creativecommons.org/licenses/by/3.0/legalcode
 */
package com.thjug.bgile.guice;

import com.google.inject.Exposed;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.google.inject.util.Types;
import com.thjug.bgile.define.Accounttype;
import com.thjug.bgile.entity.Account;
import com.thjug.bgile.security.JpaRealm;
import com.thjug.bgile.security.ShiroWebSecurityManager;
import com.thjug.bgile.servlet.AuthenticationListenerImpl;
import java.util.Collection;
import java.util.Set;
import javax.servlet.ServletContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.guice.web.ShiroWebModule;
import static org.apache.shiro.guice.web.ShiroWebModule.ANON;
import static org.apache.shiro.guice.web.ShiroWebModule.AUTHC;
import static org.apache.shiro.guice.web.ShiroWebModule.ROLES;
import org.apache.shiro.web.mgt.WebSecurityManager;

/**
 *
 * @author @nuboat
 */
public class ShiroWebModuleImpl extends ShiroWebModule {

	public ShiroWebModuleImpl(final ServletContext servletContext) {
		super(servletContext);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void configureShiroWeb() {
		bindRealm().to(JpaRealm.class);
		bindAuthenticationListener().to(AuthenticationListenerImpl.class);
		bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to("/home");
		bindConstant().annotatedWith(Names.named("shiro.unauthorizedUrl")).to("/dashboard");
		addFilterChain("/signin", ANON);
		addFilterChain("/signin.xhtml", ANON);
		addFilterChain("/home", ANON);
		addFilterChain("/home.xhtml", ANON);
		addFilterChain("/register", ANON);
		addFilterChain("/register.xhtml", ANON);
		addFilterChain("/assets/**", ANON);
		addFilterChain("/javax.faces.resource/**", ANON);
		addFilterChain("/board/**", ANON);
		addFilterChain("/**", AUTHC);
		addFilterChain("/corporate/**", AUTHC, config(ROLES, Accounttype.C.getText()));
		addFilterChain("/admin/**", AUTHC, config(ROLES, Accounttype.A.getText()));
		bind(authenticationListenerCollectionKey()).to(authenticationListenerSetKey());
	}

	@Override
	protected void bindWebSecurityManager(final AnnotatedBindingBuilder<? super WebSecurityManager> bind) {
		try {
			bind.toConstructor(ShiroWebSecurityManager.class.getConstructor(Collection.class, Collection.class))
					.asEagerSingleton();
		} catch (final NoSuchMethodException e) {
			throw new ConfigurationException(
					"This really shouldn't happen. Either something has changed in Shiro, or there's a bug in ShiroModule.",
					e);
		}
	}

	LinkedBindingBuilder<AuthenticationListener> bindAuthenticationListener() {
		final Multibinder<AuthenticationListener> multibinder = Multibinder.newSetBinder(binder(),
				AuthenticationListener.class);
		return multibinder.addBinding();
	}

	private Key<Set<AuthenticationListener>> authenticationListenerSetKey() {
		return (Key<Set<AuthenticationListener>>) Key.get(TypeLiteral.get(Types.setOf(AuthenticationListener.class)));
	}

	private Key<Collection<AuthenticationListener>> authenticationListenerCollectionKey() {
		return (Key<Collection<AuthenticationListener>>) Key.get(Types.newParameterizedType(Collection.class,
				AuthenticationListener.class));
	}

	@Provides
	@Exposed
	public Account provideAccount() {
		return (Account) SecurityUtils.getSubject().getPrincipal();
	}
}
