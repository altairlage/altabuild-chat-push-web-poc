package com.altabuild.AltabuildChat.ManagedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sun.faces.context.flash.ELFlash;

import br.com.brtoken.safeentitylib.model.ChatChannel;
import br.com.brtoken.safeentitylib.model.ChatMessage;

@ManagedBean(name = "MensagensPorCanalMB")
@ViewScoped
public class MensagensPorUsuarioMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private ChatChannel canalSelecionado;
	private List<ChatMessage> mensagemLista;
	
	
	@PostConstruct
	public void init(){
		
		if(ELFlash.getFlash().get("canalSelecionado") != null){
			this.canalSelecionado = (ChatChannel)ELFlash.getFlash().get("canalSelecionado");
		}
		
		ELFlash.getFlash().clear();
	}

	public List<ChatMessage> getMensagensPorCanal(){
		if(this.canalSelecionado != null){
			mensagemLista =  this.canalSelecionado.getMessages();
			return mensagemLista;
		}
		return null;
	}

	public ChatChannel getCanalSelecionado() {
		return canalSelecionado;
	}


	public void setCanalSelecionado(ChatChannel canalSelecionado) {
		this.canalSelecionado = canalSelecionado;
	}
	
}
