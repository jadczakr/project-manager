package edc.projects.status.managing.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edc.projects.status.managing.entities.Dziennik;
import edc.projects.status.managing.service.implementation.DziennikServiceImpl;
import edc.projects.status.managing.service.implementation.PracownicyServiceImpl;



@Controller
public class DziennikController {

	
	private PracownicyServiceImpl pracownicyService;
	private DziennikServiceImpl dziennikServiceImpl;
	
	public DziennikController(PracownicyServiceImpl pracownicyService, DziennikServiceImpl dziennikServiceImpl)
	{
		
		this.pracownicyService = pracownicyService;
		this.dziennikServiceImpl = dziennikServiceImpl;
	}
	
	
	@GetMapping(value="/notatka")
	public String notatka(Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		Dziennik tempDziennik = new Dziennik();
		theModel.addAttribute("dziennik",tempDziennik);
		return "form/notatka";
	}
	

	
	
	@RequestMapping(value="/save-notatka", method = RequestMethod.POST)
	private String saveNotatka(@ModelAttribute("dziennik")Dziennik dziennik)
	{
		
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		
		int tmp = Integer.parseInt(dziennik.getTyp());

		dziennik.setUzytkownik(login);

		
		dziennikServiceImpl.addNotatke(dziennik);
		
		return "redirect:/dziennik";
	}
	
	
	
	@RequestMapping(value="/dziennik")
	private String dziennik(Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		String login = SecurityContextHolder.getContext().getAuthentication().getName().trim();
		theModel.addAttribute("dziennik2",dziennikServiceImpl.getAllOgolne());
		theModel.addAttribute("dziennik",dziennikServiceImpl.getAll2(login));
		theModel.addAttribute("login",login);
		return "view/dziennik";
	}
	
	
	
	@RequestMapping(value="/edit-notatka", method = RequestMethod.GET)
	private String editNotatka(Model theModel, @RequestParam("id")Integer theId)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("dziennik",dziennikServiceImpl.getNotatkaById(theId));
		
		return "form/notatka";
	}
	
	@RequestMapping(value="/delete-notatka")
	private String deleteNotatka(@RequestParam("id")Integer theId)
	{
		dziennikServiceImpl.deleteNotatkaById(theId);
		
		return "redirect:/dziennik";
	}
	
	@RequestMapping(value="/edit")
	private String edycja(@RequestParam("id") int theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		return "status/project";
	}
	
}
