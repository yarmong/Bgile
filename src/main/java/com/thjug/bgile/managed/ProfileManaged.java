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
package com.thjug.bgile.managed;

import com.thjug.bgile.entity.Account;
import com.thjug.bgile.facade.AccountFacade;
import com.thjug.bgile.security.Encrypter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author @nuboat
 */
@ViewScoped
@ManagedBean(name = "profile")
public class ProfileManaged extends AccountAbstractManaged {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ProfileManaged.class);
	private Account account;
	private String viewid;
	private String password;
	private String confirmpassword;

	private boolean canSave = true;

	@Inject
	private transient AccountFacade facade;

	public ProfileManaged() {
		account = getPrincipal();
	}

	public String linkToProfile(final String viewid) {
		this.viewid = viewid;
		LOG.info("Save viewid : {}", viewid);
		return redirect("profile");
	}

	public String back() {
		return (viewid != null) ? redirect(viewid) : redirect("dashboard");
	}

	public String save() {
		try {
			account.setId(getPrincipal().getId());
			facade.editAccount(account);
			addInfoMessage("Success", "Your profile has changed.");
		} catch (final Exception e) {
			addErrorMessage("Server Error", "Cannot changed account information.");
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public String changepasswd() {
		try {
			if (password.equals(confirmpassword)) {
				account.setPasswd(Encrypter.cipher(password));
				facade.editAccount(account);
				addInfoMessage("Success", "Your password has changed.");
			} else {
				addWarnMessage("Password Mismatch", "Verify password & confirm password not equal.");
			}
		} catch (final Exception e) {
			addErrorMessage("Server Error", "Cannot changed account password.");
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public void validateUsernameIsExist(final ValueChangeEvent e) {

	}

	public void validateEmailIsExist(final ValueChangeEvent e) {

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(final String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
