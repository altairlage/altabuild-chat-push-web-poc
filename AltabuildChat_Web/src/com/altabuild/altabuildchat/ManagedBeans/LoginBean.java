package com.altabuild.AltabuildChat.ManagedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import br.com.brtoken.safeentitylib.dao.AdministradorDAO;
import br.com.brtoken.safeentitylib.model.Pessoa;


@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
	
	private Pessoa autenticado;
	private String username;	
	private String password;
	
	
	public Pessoa getAutenticado() {
		return autenticado;
	}

	
	public void setAutenticado(Pessoa autenticado) {
		this.autenticado = autenticado;
	}


	public String getUsername() {
		return username;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}


	public String efetuarLogin(){
					
		FacesContext faces = FacesContext.getCurrentInstance();
		
		String pagina = "/public/login.xhtml";
		String mensagem = null;
		
		AdministradorDAO dao = new AdministradorDAO();
		
			
		CryptorChatBO cryptorChatBO = new CryptorChatBO();
		if(this.username != null && this.password != null){
			if(this.username.length() > 2 && this.password.length() > 2){
				
//				String cryptUserLogin = new String(Hex.encode(CriptoSafeCOREServer30.cifrar_pub_completado(this.username, true, 128, CriptoSafeCOREServer30.CARACTERE_PARAGRAFO)));
				String cryptUserLogin = cryptorChatBO.cryptCompTP(this.username, 128);
				Pessoa aux = new Pessoa();
				aux.setPesLogin(cryptUserLogin);
				Pessoa p = dao.getAdministradorPorLogin(aux);
				
				if (p != null) {
//					String cryptPassword = new String(Hex.encode(CriptoSafeCOREServer30.cifrar_pub_completado(this.password, true, 16, CriptoSafeCOREServer30.CARACTERE_PARAGRAFO)));
					String cryptPassword = cryptorChatBO.cryptCompTP(this.password, 16);
					if(p.getPesSenha().equals(cryptPassword)){
						
						if(p.getPesStatus() == 1){
							this.autenticado = p;
				            FacesContext context = FacesContext.getCurrentInstance();
				            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				            request.getSession().setAttribute("pessoa", p);
				            return "/protected/usersList.xhtml";
						}
						else{
							pagina = "/public/login.xhtml";
							mensagem = "Este usu�rio n�o tem permiss�o para acessar o sistema.";
						}
					}
					else{
						pagina = "/public/login.xhtml";
						mensagem = "Usu�rio ou senha incorretos.";
					}
				}
				else{
					pagina = "/public/login.xhtml";
					mensagem = "Usu�rio ou senha incorretos.";
				}
			}
		}
						
		else{
			
			pagina = "/public/login.xhtml";
			mensagem = "Voc� deve digitar o usu�rio e a senha.";
			autenticado = null;

		}
		
		if(mensagem != null){
			
			faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
			
		}
		
		return pagina;
	}
	
	
	public String sair(){
		this.autenticado = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		session.invalidate();
		
		session = null;
		
		return "/public/login.xhtml";
	}

	
	public Pessoa getAdministrador() {
		return this.autenticado;
	}

	
	public void setAdministrador(Pessoa pessoa) {
		this.autenticado = pessoa;
	}

	
	public boolean isLogado() {
		boolean aut = false;
		if(this.autenticado != null){
			aut = true;
		}
		return aut;
	}
	
}
