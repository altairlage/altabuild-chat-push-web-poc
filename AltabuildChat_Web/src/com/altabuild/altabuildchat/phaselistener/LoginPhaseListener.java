package com.altabuild.AltabuildChat.phaselistener;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.altabuild.AltabuildChat.ManagedBeans.LoginBean;

public class LoginPhaseListener implements PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		// TODO Auto-generated method stub
		
		FacesContext facesContext = phaseEvent.getFacesContext();
		String str = facesContext.getViewRoot().getViewId();
		
		if("/public/login.xhtml".equals(facesContext.getViewRoot().getViewId())){
			return;
		}
		
		LoginBean loginBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginBean}", LoginBean.class);
		
		if(!loginBean.isLogado()){
			
			NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voc� precisa estar logado para acessar esta p�gina.", null));
			navigationHandler.handleNavigation(facesContext, null, "/public/login.xhtml");
			facesContext.renderResponse();
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}
