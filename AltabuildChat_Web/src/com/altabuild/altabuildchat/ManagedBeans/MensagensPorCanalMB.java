package com.altabuild.AltabuildChat.ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.brtoken.safeentitylib.dao.ChatMessageDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;
import br.com.brtoken.safeentitylib.model.ChatMessage;
import br.com.brtoken.safeentitylib.model.Usuario;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "mensagensPorCanalMB")
@ViewScoped
public class MensagensPorCanalMB extends AbstractMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private ChatChannel canalSelecionado;
	private Usuario usuarioSelecionado;
	private List<ChatMessage> mensagemLista;
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<ChatMessage> getMensagemLista() {
		return mensagemLista;
	}

	public void setMensagemLista(List<ChatMessage> mensagemLista) {
		this.mensagemLista = mensagemLista;
	}
	
	
	@PostConstruct
	public void init(){
		
		if(ELFlash.getFlash().get("canalSelecionado") != null){
			this.canalSelecionado = (ChatChannel)ELFlash.getFlash().get("canalSelecionado");
		}
		
		if(ELFlash.getFlash().get("usuarioSelecionado") != null){
			this.usuarioSelecionado = (Usuario)ELFlash.getFlash().get("usuarioSelecionado");
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
	
	public List<ChatMessage> getMensagensDoUsuarioPorCanal()
	{
		List<ChatMessage> lista = new ArrayList<ChatMessage>();
		if(usuarioSelecionado != null && canalSelecionado != null){
			ChatMessageDAO dao = new ChatMessageDAO();
			lista = dao.getAllByUsuarioAndChannel(canalSelecionado.getChaDescription(), usuarioSelecionado.getPessoa().getPesLogin());
			
			this.mensagemLista = lista;
		}
		return lista;
	}
	
	public void deleteMessages(){
		if(this.mensagemLista != null && mensagemLista.size() > 0){
			ChatMessageDAO dao = new ChatMessageDAO();
			
			dao.deleteByChannelAndUser(canalSelecionado.getChaId(), usuarioSelecionado.getUsuId());
			
//			for (Iterator iterator = mensagemLista.iterator(); iterator.hasNext();) {
//				ChatMessage mensagem = (ChatMessage) iterator.next();
//				try {
//					dao.excluir(mensagem);
//				} catch (Exception e) {
//					displayErrorMessageToUser("Erro ao apagar hist�rico do canal.");
//					e.printStackTrace();
//				}
//			}
			this.mensagemLista = null;
			
			displayInfoMessageToUser("Hist�rico do canal '" + canalSelecionado.getChaDescription() + "' apagado com sucesso.");
			this.canalSelecionado = null;
			this.usuarioSelecionado = null;
		
		}else{
			displayErrorMessageToUser("O canal '" + canalSelecionado.getChaDescription() + "' n�o possui mensagens a serem apagadas.");
		}
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
	
}
