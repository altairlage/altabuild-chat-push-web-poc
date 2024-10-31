package tests;

import br.com.brtoken.safeentitylib.dao.ChatChannelDAO;
import br.com.brtoken.safeentitylib.model.ChatChannel;

public class ConverterTest {

	public static void main(String[] args) {
		String string = "7";
		ChatChannel retorno = null;
		try {
			if(string != null){
				ChatChannelDAO dao = new ChatChannelDAO();
				retorno  = dao.getPorId(Integer.parseInt(string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("retorno - id = " + retorno.getChaId() + " - descricao = " + retorno.getChaDescription());

	}

}
