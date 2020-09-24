package edc.projects.status.managing.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edc.projects.status.managing.service.implementation.DziennikServiceImpl;
import edc.projects.status.managing.service.implementation.PracownicyServiceImpl;


@Controller
public class UrlopController {

	
	
	private PracownicyServiceImpl pracownicyService;
	private DziennikServiceImpl dziennikServiceImpl;
	
	

	public UrlopController(PracownicyServiceImpl pracownicyService, DziennikServiceImpl dziennikServiceImpl)
	{
		this.dziennikServiceImpl = dziennikServiceImpl;
		this.pracownicyService = pracownicyService;
	}
	
	
	@RequestMapping(value="/urlop")
	private String urlop(Model theModel)
	{
		String imieNazwisko = pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko();
		theModel.addAttribute("urlopUzytkownika",imieNazwisko);
		theModel.addAttribute("uzytkownik" , imieNazwisko);
		return "view/urlop";
	}
	
	@RequestMapping(value="/save-urlop")
	public String saveUrlop(HttpServletRequest request)
	{
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String od = request.getParameter("od");
		String doo = request.getParameter("do");
		String odP[] = od.split("-");
		String doP[] = doo.split("-");
		
		LocalDate ld1 = LocalDate.of(Integer.parseInt(odP[0]), Integer.parseInt(odP[1]), Integer.parseInt(odP[2]));
		LocalDate ld2 = LocalDate.of(Integer.parseInt(doP[0]), Integer.parseInt(doP[1]), Integer.parseInt(doP[2]));
		
		pracownicyService.setUrlop(login, ld1, ld2);
		updateDb2();
		return "redirect:/daily-zlecenia";
	}

	protected void updateDb2()
	{
		dziennikServiceImpl.usunPoTerminie();
		pracownicyService.urlopy();
		System.out.println("edytowano!");
	}
	
}
