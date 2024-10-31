package com.altabuild.AltabuildChat.ManagedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.sun.faces.context.flash.ELFlash;

import br.com.brtoken.safeentitylib.dao.ChatChannelDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;
import br.com.brtoken.safeentitylib.model.Usuario;

@ManagedBean(name = "channelMB")
@ViewScoped
public class CanalMB extends AbstractMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarioList;
	private Usuario usuarioSelecionado;
	private ChatChannel canalSelecionado;
	private List<ChatChannel> canaisList;
	

	public void setCanaisList(List<ChatChannel> listaCanais) {
		this.canaisList = listaCanais;
	}
	

	public ChatChannel getCanalSelecionado() {
		return canalSelecionado;
	}
	

	public void setCanalSelecionado(ChatChannel selectedCanal) {
		this.canalSelecionado = selectedCanal;
		this.usuarioList = this.canalSelecionado.getUsuarios();
	}
	

	public Usuario getUsuarioSelecionado() {
		if(this.usuarioSelecionado!= null)
			this.canaisList = usuarioSelecionado.getChatChannels();
		return usuarioSelecionado;
	}
	

	public void setUsuarioSelecionado(Usuario selectedUser) {
		if(selectedUser != null){
			this.usuarioSelecionado = selectedUser;
			this.canaisList = selectedUser.getChatChannels();
		}
	}
	
	
	public List<ChatChannel> getCanaisList() {
		ChatChannelDAO dao = new ChatChannelDAO();
		
		try {
			this.canaisList = dao.getTodos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.canaisList;
	}
	
	
	public List<ChatChannel> getList(){
		if(this.canaisList == null){
			this.getCanaisList();
		}
		
		return this.canaisList;
	}
	
	
	public void setUsuarioList(List<Usuario> userList) {
		this.usuarioList = userList;
	}
	
	
	public List<Usuario> getUsuarioList() {
		return this.usuarioList;
	}
	
	
	public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Canal Selecionado", String.valueOf(((ChatChannel) event.getObject()).getChaId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
	public void onRowSelectUsuario(SelectEvent event){
		usuarioSelecionado = (Usuario) event.getObject();
		
		ELFlash.getFlash().put("usuarioSelecionado", usuarioSelecionado);
		ELFlash.getFlash().put("canalSelecionado", canalSelecionado);
		ELFlash.getFlash().put("viewParent", "/protected/channelList.xhtml");
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extContext = ctx.getExternalContext();
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/protected/messagesByChannel.xhtml"));

		try {
		     extContext.redirect(url);
		} catch (IOException ioe) {
		    throw new FacesException(ioe);
		}
	}
	
}
