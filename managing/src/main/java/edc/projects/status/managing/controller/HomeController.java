package edc.projects.status.managing.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edc.projects.status.managing.service.implementation.DziennikServiceImpl;
import edc.projects.status.managing.service.implementation.KlienciServiceImpl;
import edc.projects.status.managing.service.implementation.PracownicyServiceImpl;
import edc.projects.status.managing.service.implementation.ZleceniaDoneServiceImpl;
import edc.projects.status.managing.service.implementation.ZleceniaServiceImpl;

@Controller
public class HomeController {
	
	private PracownicyServiceImpl pracownicyService;
	private ZleceniaServiceImpl zleceniaService;
	private ZleceniaDoneServiceImpl zleceniaDoneService;
	private DziennikServiceImpl dziennikServiceImpl;
	private KlienciServiceImpl klienciService;
	
	public HomeController(PracownicyServiceImpl pracownicyService, ZleceniaServiceImpl zleceniaService, ZleceniaDoneServiceImpl zleceniaDoneService, DziennikServiceImpl dziennikServiceImpl, KlienciServiceImpl klienciService)
	{
		this.klienciService = klienciService;
		this.pracownicyService = pracownicyService;
		this.zleceniaService = zleceniaService;
		this.zleceniaDoneService = zleceniaDoneService;
		this.dziennikServiceImpl = dziennikServiceImpl;
	}
	

	@RequestMapping("/archiwum")
	private String archiwum(Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("wykonawcy",pracownicyService.getAllPracownicy());
		return "info/archiwum";
	}

	 // zmiana co 12h 43200000
	@Scheduled(fixedDelay=10800000)
	protected void updateDb()
	{
		zleceniaService.pierwszyDzienMiesiaca();
		dziennikServiceImpl.usunPoTerminie();
		pracownicyService.urlopy();
		System.out.println("edytowano!");
	}
	
	@Scheduled(fixedDelay=43200000)
	protected void updateCalendar()
	{
		
		
		
	}
}