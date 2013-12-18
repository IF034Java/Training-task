package springapp.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ClientsController {	
	

	public String printWelcome(ModelMap model) {				
		return "/Training_1/Clients.jsp";
	}
}