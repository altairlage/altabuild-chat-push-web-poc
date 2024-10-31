package com.altabuild.AltabuildChat.ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.brtoken.safeentitylib.dao.ChatMessageDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;
import br.com.brtoken.safeentitylib.model.ChatMessage;
import br.com.brtoken.safeentitylib.model.Usuario;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "mensagensListMB")
@ViewScoped
public class MensagensListMB extends AbstractMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuarioSelecionado;
	private ChatChannel canalSelecionado;
	private Map<String, ChatChannel> canais;
	private List<ChatChannel> canaisList;
	private List<ChatMessage> mensagemLista;
	
	@PostConstruct
	public void init(){
		
		if(ELFlash.getFlash().get("usuarioSelecionado") != null){
			this.usuarioSelecionado = (Usuario)ELFlash.getFlash().get("usuarioSelecionado");
		}
		
		if(usuarioSelecionado != null){
			if(usuarioSelecionado.getChatChannels() != null && usuarioSelecionado.getChatChannels().size() > 0){
				this.canalSelecionado = usuarioSelecionado.getChatChannels().get(0);
				this.mensagemLista = usuarioSelecionado.getChatChannels().get(0).getMessages();
			}
		}else{
			this.canalSelecionado = null;
			this.mensagemLista = null;
		}
		
		ELFlash.getFlash().clear();
	}
	
	
	public List<ChatChannel> getCanaisList() {
		this.canaisList = new ArrayList<ChatChannel>();
		
		if(this.usuarioSelecionado != null){
			if(this.usuarioSelecionado.getChatChannels() != null && this.usuarioSelecionado.getChatChannels().size() > 0)
			{
				this.canaisList = usuarioSelecionado.getChatChannels();
			}
		}
		
		return canaisList;
	}
	

	public void setCanaisList(List<ChatChannel> canaisList) {
		this.canaisList = canaisList;
	}

	
	public List<ChatMessage> getMensagensPorCanal(){
		if(this.canalSelecionado != null){
			ChatMessageDAO dao = new ChatMessageDAO();
			mensagemLista =  dao.getAllByUsuarioAndChannel(canalSelecionado.getChaDescription(), usuarioSelecionado.getPessoa().getPesLogin());
			
			return mensagemLista;
		}
		return null;
	}
	
	
	public void onCanalChange(){
	}
	

	public ChatChannel getCanalSelecionado() {
		return canalSelecionado;
	}


	public void setCanalSelecionado(ChatChannel canal) {
		if(canal != null){			
			this.canalSelecionado = canal;
			this.mensagemLista = this.canalSelecionado.getMessages();
		}
	}
	
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	

	public Map<String, ChatChannel> getCanais() {
		this.canais = new HashMap<String, ChatChannel>();
		if(this.usuarioSelecionado != null)
		{
			if(this.usuarioSelecionado.getChatChannels() != null && this.usuarioSelecionado.getChatChannels().size() > 0)
			{
				this.canais = this.transformaCanais(usuarioSelecionado.getChatChannels());
			}
		}
		return canais;
	}
	
	
	private Map<String, ChatChannel> transformaCanais(List<ChatChannel> lista){
		Map<String, ChatChannel> mapa = new HashMap<String, ChatChannel>();
		
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ChatChannel canal = (ChatChannel) iterator.next();
			mapa.put(canal.getChaDescription(), canal);
		}
		
		return mapa;
	}
	

	public void setCanais(Map<String, ChatChannel> canais) {
		this.canais = canais;
	}
	
	
	public List<ChatMessage> getMensagemLista() {
		return mensagemLista;
	}

	
	public void setMensagemLista(List<ChatMessage> mensagemLista) {
		this.mensagemLista = mensagemLista;
	}
	
	public Boolean renderStatus(String value){
		int test = Integer.parseInt(value);
		if(test == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void deleteUserHistory(){
		try {
			ChatMessageDAO dao = new ChatMessageDAO();
			dao.deletarPorUsuario(usuarioSelecionado.getUsuId());
			mensagemLista = null;
			canalSelecionado.setMessages(null);
			
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Hist�rico de '" + usuarioSelecionado.getPessoa().getPesNome() + "' apagado com sucesso."));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("O usu�rio '" + usuarioSelecionado.getPessoa().getPesNome() + "' n�o possui mensagens a serem apagadas."));
	        e.printStackTrace();
		}		
	}
	
	
	public void deleteChanelHistory(){		
		try {
			
			ChatMessageDAO dao = new ChatMessageDAO();
			dao.deleteByChannelAndUser(canalSelecionado.getChaId(), usuarioSelecionado.getUsuId());
			this.mensagemLista = null;
			canalSelecionado.setMessages(null);
			
			FacesContext context = FacesContext.getCurrentInstance();
		    context.addMessage(null, new FacesMessage("Hist�rico do canal '" + canalSelecionado.getChaDescription() + "' apagado com sucesso."));
			
			
		} catch (Exception e) {
			displayErrorMessageToUser("Erro ao apagar hist�rico do canal!");
			e.printStackTrace();
		}
	}
	
}
