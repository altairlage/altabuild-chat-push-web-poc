package com.altabuild.AltabuildChat.ManagedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.brtoken.safeentitylib.dao.ChatMessageDAO;
import br.com.brtoken.safeentitylib.dao.UsuarioDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;
import br.com.brtoken.safeentitylib.model.ChatMessage;
import br.com.brtoken.safeentitylib.model.Usuario;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "usuariosMB")
@ViewScoped
public class UsuariosMB extends AbstractMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioDel;
	private List<Usuario> usuarioList;
	private Usuario usuarioSelecionado;
	private ChatChannel canalSelecionado;
	private List<ChatChannel> canalList;
	
	
	public void setUsuarioList(List<Usuario> userList) {
		this.usuarioList = userList;
	}
	
	
	public void onRowSelect(SelectEvent event) {
		Usuario usu = (Usuario) event.getObject();
		ELFlash.getFlash().put("usuarioSelecionado", usu);
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/protected/messagesList.xhtml"));

		try {
		     extContext.redirect(url);
		} catch (IOException ioe) {
		    throw new FacesException(ioe);
		}
    }
	
	
	public void onRowSelectCanais(SelectEvent event){
		canalSelecionado = (ChatChannel) event.getObject();
		
		
		ELFlash.getFlash().put("usuarioSelecionado", usuarioSelecionado);
		ELFlash.getFlash().put("canalSelecionado", canalSelecionado);
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();

		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/protected/messagesByChannel.xhtml"));

		try {
		     extContext.redirect(url);
		} catch (IOException ioe) {
		    throw new FacesException(ioe);
		}
	}
	
	
	public void deleteUserHistory(){
		List<ChatMessage> msgList = usuarioSelecionado.getMessages();
		if(msgList != null && msgList.size() > 0){
			ChatMessageDAO dao = new ChatMessageDAO();
			
			for (Iterator iterator = msgList.iterator(); iterator.hasNext();) {
				ChatMessage mensagem = (ChatMessage) iterator.next();
				try {
					dao.excluir(mensagem);
				} catch (Exception e) {
					displayErrorMessageToUser("Erro ao apagar hist�rico do usu�rio!");
					e.printStackTrace();
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Hist�rico de '" + usuarioSelecionado.getPessoa().getPesNome() + "' apagado com sucesso."));
		}
		else{
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("O usu�rio '" + usuarioSelecionado.getPessoa().getPesNome() + "' n�o possui mensagens a serem apagadas."));
		}
	}
	
	
	public void ressetUsuarioDel(){
		this.usuarioDel = null;
	}
	
	
	public List<ChatChannel> getCanaisList() {
		return canalList;
	}
	

	public void setCanaisList(List<ChatChannel> listaCanais) {
		this.canalList = listaCanais;
	}
	

	public ChatChannel getCanalSelecionado() {
		return canalSelecionado;
	}
	

	public void setCanalSelecionado(ChatChannel selectedCanal) {
		this.canalSelecionado = selectedCanal;
	}
	

	public Usuario getUsuarioSelecionado() {
		if(this.usuarioSelecionado!= null)
			this.canalList = usuarioSelecionado.getChatChannels();
		return usuarioSelecionado;
	}
	

	public void setUsuarioSelecionado(Usuario selectedUser) {
		this.usuarioSelecionado = selectedUser;
		if(usuarioSelecionado != null){
			this.canalList = selectedUser.getChatChannels();
		}
	}
	

	public Usuario getUsuarioDel() {
		return usuarioDel;
	}
	
	
	public void setUsuarioDel(Usuario user) {
		this.usuarioDel = user;
	}
	
	
	public List<Usuario> getUsuarioList() {
		UsuarioDAO dao = new UsuarioDAO();
		this.usuarioList = dao.getTodosUsuarios();
		
		return this.usuarioList;
	}
	
	
	public List<Usuario> getList(){
		if(this.usuarioList == null){
			this.getUsuarioList();
		}
		
		return this.usuarioList;
	}
	
}
