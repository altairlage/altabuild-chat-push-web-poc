package com.altabuild.AltabuildChat.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.brtoken.safeentitylib.dao.ChatChannelDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;


@FacesConverter(forClass = ChatChannel.class)
public class ChannelConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		ChatChannel retorno = null;
		try {
			if(string != null){
				ChatChannelDAO dao = new ChatChannelDAO();
				retorno  = dao.getPorId(Integer.parseInt(string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {

		if(o != null){
			String retorno = String.valueOf(((ChatChannel) o).getChaId());
			return retorno;
		}
		return null;
	}

}
