package edc.projects.status.managing.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edc.projects.status.managing.entities.Zlecenia;
import edc.projects.status.managing.entities.ZleceniaDone;
import edc.projects.status.managing.service.ZleceniaDoneService;
import edc.projects.status.managing.service.implementation.KlienciServiceImpl;
import edc.projects.status.managing.service.implementation.PracownicyServiceImpl;
import edc.projects.status.managing.service.implementation.ZleceniaDoneServiceImpl;
import edc.projects.status.managing.service.implementation.ZleceniaServiceImpl;


@Controller
public class ProjectController {

	
	private PracownicyServiceImpl pracownicyService;
	private KlienciServiceImpl klienciService;
	private ZleceniaServiceImpl zleceniaService;
	private ZleceniaDoneServiceImpl zleceniaDoneService;

	
	public ProjectController(PracownicyServiceImpl pracownicyService, KlienciServiceImpl klienciService, ZleceniaServiceImpl zleceniaService, ZleceniaDoneServiceImpl zleceniaDoneService)
	{
		this.klienciService = klienciService;
		this.pracownicyService = pracownicyService;
		this.zleceniaService = zleceniaService;
		this.zleceniaDoneService = zleceniaDoneService;
	}
	
	@RequestMapping(value="/wykonanie", method = RequestMethod.POST)
	private String wykonanie(HttpServletRequest request)
	{
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		String tempId = request.getParameter("id");
		Integer id = Integer.parseInt(tempId);
		
		Zlecenia tempZlecenie = zleceniaService.getZlecenie(id);
		
		tempZlecenie.setData(LocalDate.now());
		ZleceniaDone zlecenieWykonane = new ZleceniaDone();
		
		LocalDate dattt = LocalDate.now();
		
		zlecenieWykonane.setDataDodania(LocalDate.now());
		zlecenieWykonane.setDzienTygodnia(zleceniaService.getDzienPolski(LocalDate.now().getDayOfWeek().toString()));
		zlecenieWykonane.setFirma(tempZlecenie.getFirma());
		zlecenieWykonane.setGodzinaDodania(LocalTime.now());
		zlecenieWykonane.setIdZlecenia(tempZlecenie.getId());
		zlecenieWykonane.setNazwaZlecenia(tempZlecenie.getNazwaZlecenia());
		zlecenieWykonane.setOpis(request.getParameter("opis"));
		zlecenieWykonane.setPath(request.getParameter("path"));
		zlecenieWykonane.setStatus(request.getParameter("status"));
		zlecenieWykonane.setWykonawca(login);
		
		
		
		zleceniaDoneService.addZlecenieDone(zlecenieWykonane);
		zleceniaService.saveOrUpdate(tempZlecenie);
		
		return "redirect:/daily-zlecenia";
	}
	
	/*
	@RequestMapping(value="/download", method = RequestMethod.GET)
	private StreamingResponseBody downloadManual(@RequestParam("id")Integer id, HttpServletResponse response) throws FileNotFoundException
	{
		
		Zlecenia tempZlecenie = zleceniaService.getZlecenie(id);
		 response.setContentType("application/pdf");
		 
		    InputStream inputStream = new FileInputStream(new File(tempZlecenie.getManualPath()));

	        return OutputStream -> {
	            int nRead;
	            byte[] data = new byte[1024];
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                OutputStream.write(data, 0, nRead);
	            }
	            inputStream.close();
	        };
	        
	 }
*/
	
	@GetMapping("/download")
	public ResponseEntity downloadFileFromLocal(@RequestParam("id")Integer id) {
		
		
		Zlecenia tempZlecenie = zleceniaService.getZlecenie(id);
		
		Path path = Paths.get(tempZlecenie.getManualPath());
		UrlResource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	
	
	@RequestMapping("/change-status")
	public String change(@RequestParam("id")Integer theId, HttpServletRequest request)
	{
		
		ZleceniaDone tempZlecenie = zleceniaDoneService.getZlecenieDoneById(theId);
		if(request.getParameter("path3")!=null)
		{
			tempZlecenie.setPath(request.getParameter("path3"));
		}
		
		tempZlecenie.setStatus("ZAAKCEPTOWANO");
		zleceniaDoneService.saveOrUpdate(tempZlecenie);
		
		return "redirect:/daily-zlecenia";
	}
	
	@RequestMapping("/add-project")
	private String addProject(Model theModel)
	{ 
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		Zlecenia tempZlecenie = new Zlecenia();
		
		theModel.addAttribute("true",true);
		theModel.addAttribute("project",tempZlecenie);
		theModel.addAttribute("glowny",pracownicyService.getAllPracownicy());
		theModel.addAttribute("firmy",klienciService.getAllKlienci());
		theModel.addAttribute("button","Dodaj");
		return "form/project";
	}
	
	
	
	
	@RequestMapping("/add-project2")
	private String addProject2(Model theModel)
	{ 
		
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		Zlecenia tempZlecenie = new Zlecenia();
		theModel.addAttribute("project",tempZlecenie);
		theModel.addAttribute("glowny",pracownicyService.getAllPracownicy());
		theModel.addAttribute("firmy",klienciService.getAllKlienci());
		theModel.addAttribute("button","Dodaj");
		return "form/project2";
	}
	
	
	@RequestMapping(value="/save-project", method = RequestMethod.POST)
	private String saveProject(HttpServletRequest request, @RequestParam(value="manual",required=false)MultipartFile file, @ModelAttribute("project")Zlecenia zlecenie) throws IOException
	{

		String[] param;
		
		Zlecenia temp;
		try {
			String relativeWebPath = "/resources";
			String absoluteFilePath = request.getServletContext().getRealPath(relativeWebPath);
			Random rand = new Random();

			byte[] bytes = file.getBytes();
			File dir = new File(absoluteFilePath + "/" + file.getName() + "_" + rand.nextInt(10000000)) ; 
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			
			File uploadFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			outputStream.write(bytes);
			outputStream.close();
			zlecenie.setManualPath(uploadFile.getAbsolutePath());
		}
		
		
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		zlecenie.setData(LocalDate.ofYearDay(LocalDate.now().getDayOfYear(),2));
		
		
		if(zlecenie.getCykl()==3)
		{
			param = zlecenie.getZakresy().split(" ");
			for(String z : param)
			{
				temp = new Zlecenia(zlecenie);
				temp.setZakresy(z);
				zleceniaService.saveOrUpdate(temp);
			}
			
		}
		if(zlecenie.getCykl()==3)
		{
			zlecenie.setCykl(4);
		}
		
		zleceniaService.addZlecenie(zlecenie);
		return "redirect:/daily-zlecenia";
	}
	
	
	
	
	
	@RequestMapping(value="/update")
	public String updateProject(@ModelAttribute("project")ZleceniaDone zlecenie)
	{
		
		zleceniaDoneService.saveOrUpdate(zlecenie);
		
		return "redirect:/daily-zlecenia";
	}
	

	@RequestMapping(value="/save-project4", method = RequestMethod.POST)
	private String saveProject4(HttpServletRequest request, @RequestParam(value="manual",required=false)MultipartFile file, @ModelAttribute("project")Zlecenia zlecenie) throws IOException
	{

		String[] param;
		
		Zlecenia temp;
		
		try {
			String relativeWebPath = "/resources";
			String absoluteFilePath = request.getServletContext().getRealPath(relativeWebPath);
			Random rand = new Random();

			byte[] bytes = file.getBytes();
			File dir = new File(absoluteFilePath + "/" + file.getName() + "_" + rand.nextInt(10000000)) ; 
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			
			File uploadFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			outputStream.write(bytes);
			outputStream.close();
			zlecenie.setManualPath(uploadFile.getAbsolutePath());
		}
		
		
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		zlecenie.setData(LocalDate.ofYearDay(2020,1));
		
		
		if(zlecenie.getCykl()==3)
		{
			param = zlecenie.getZakresy().split(" ");
			for(String z : param)
			{
				temp = new Zlecenia(zlecenie);
				temp.setZakresy(z);
				zleceniaService.saveOrUpdate(temp);
			}
			
		}

		if(zlecenie.getCykl()==3)
		{
			zlecenie.setCykl(4);
		}
		zleceniaService.addZlecenie(zlecenie);
		
			return "redirect:/moje-zlecenia";
	}
	
	
	
	
	@RequestMapping(value="/edit-zlecenie", method = RequestMethod.GET)
	private String editZlecenie(@RequestParam("id")Integer theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("project",zleceniaService.getZlecenie(theId));
		theModel.addAttribute("glowny",pracownicyService.getAllPracownicy());
		theModel.addAttribute("firmy",klienciService.getAllKlienci());
		theModel.addAttribute("button","Edytuj");
		return "edit/edit-project";
		
	}
	
	
	@RequestMapping(value="/edit-done", method = RequestMethod.GET)
	private String editWykonane(@RequestParam("id")Integer theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("project",zleceniaDoneService.getZlecenieDoneById(theId));
		theModel.addAttribute("glowny",pracownicyService.getAllPracownicy());
		theModel.addAttribute("firmy",klienciService.getAllKlienci());
		
		return "edit/edit-done";
	}
	
	
	@RequestMapping("/daily-zlecenia")
	private String dailyZlecenia(Model theModel)
	{
		
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Zlecenia> projekty = zleceniaService.getZakresoweZlecenia(login);
		
		projekty.addAll(zleceniaService.getDzienneZlecenia(login));
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("zawieszone",zleceniaDoneService.getZawieszone(login));
		theModel.addAttribute("projekty",projekty); 
		theModel.addAttribute("anulowane",zleceniaDoneService.getAnulowane(login));
		theModel.addAttribute("zastepstwa",zleceniaService.getZleceniaZZastepstewm(login));
		theModel.addAttribute("wykonane",zleceniaDoneService.getZleceniaDoneByLogin(login));
		
		return "view/zlecenia";
		
	}
	
	
	@RequestMapping(value = "/accept" , method = RequestMethod.GET)
	public String selected1(@RequestParam("id") int theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("zlecenie",zleceniaService.getZlecenie(theId));
		return "status/accept";
	}
	
	@RequestMapping(value = "/yellow" , method = RequestMethod.GET)
	public String selected2(@RequestParam("id") int theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("zlecenie",zleceniaService.getZlecenie(theId));
		return "status/yellow";
	}
	
	@RequestMapping(value = "/error-zlecenia" , method = RequestMethod.GET)
	public String selected3(@RequestParam("id") int theId, Model theModel)
	{
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("zlecenie",zleceniaService.getZlecenie(theId));
		return "status/error";
	}
	
	@RequestMapping(value="/success-done")
	public String successDone(@RequestParam("id")Integer id, HttpServletRequest request)
	{
		
			String login = SecurityContextHolder.getContext().getAuthentication().getName();
			
			System.out.println("zmieniam zlecenie o id: " + id);
			System.out.println("zmieniam zlecenie o id: " + id);
			System.out.println("zmieniam zlecenie o id: " + id);
 			Zlecenia tempZlecenie = zleceniaService.getZlecenie(id);
			
			tempZlecenie.setData(LocalDate.now());
			ZleceniaDone zlecenieWykonane = new ZleceniaDone();
			zlecenieWykonane.setDataDodania(LocalDate.now());
			zlecenieWykonane.setFirma(tempZlecenie.getFirma());
			zlecenieWykonane.setGodzinaDodania(LocalTime.now());
			zlecenieWykonane.setIdZlecenia(tempZlecenie.getId());
			zlecenieWykonane.setNazwaZlecenia(tempZlecenie.getNazwaZlecenia());
			zlecenieWykonane.setOpis(tempZlecenie.getOpis());
			zlecenieWykonane.setStatus("ZAAKCEPTOWANO");
			zlecenieWykonane.setWykonawca(login);
			zleceniaDoneService.addZlecenieDone(zlecenieWykonane);
			zleceniaService.saveOrUpdate(tempZlecenie);

			if(tempZlecenie.getCykl()==3)
			{
				tempZlecenie.setWykonano(true);
			}
			
			
			
				if(request.getParameter("path")==null)
				{
					zlecenieWykonane.setPath(request.getParameter("path2"));
				}
				else if(request.getParameter("path2")==null)
					zlecenieWykonane.setPath(request.getParameter("path"));
			
			
			zleceniaService.saveOrUpdate(tempZlecenie);
		
			return "redirect:/daily-zlecenia";
	}

	
	@RequestMapping(value="/search")
	private String search(@RequestParam("login")String login, HttpServletRequest request, Model theModel)
	{
		theModel.addAttribute("login",login);
		theModel.addAttribute("firma",klienciService.getAllKlienci());
		theModel.addAttribute("uzytkownik" , pracownicyService.getPracownikByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getImieNazwisko());
		theModel.addAttribute("zlecenia",zleceniaDoneService.getZleceniaDoneByLogin2(login));
		return "info/listaZlecen";
	}
		
	
	@RequestMapping(value="/moje-zlecenia")
	private String mojeZlecenia(Model theModel)
	{
		theModel.addAttribute("uzytkownik",SecurityContextHolder.getContext().getAuthentication().getName());
		theModel.addAttribute("login",zleceniaService.getFinalAllZlecenia(SecurityContextHolder.getContext().getAuthentication().getName()));
		theModel.addAttribute("zlecenia",zleceniaService.getFinalAllZlecenia(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		return "info/moje";
	}
	
	@RequestMapping(value="/delete-zlecenie")
	private String delete(@RequestParam("id")Integer id)
	{
		
		zleceniaService.deleteZlecenie(id);
		
		return "redirect:/moje-zlecenia";
	}
	
	@RequestMapping(value="/undo")
	private String cofnij(@RequestParam("id")Integer id)
	{
		
		ZleceniaDone tempZlecDone = zleceniaDoneService.getZlecenieById(id);
		
		Zlecenia tempZlec = zleceniaService.getZlecenie(tempZlecDone.getIdZlecenia());
		tempZlec.setData(LocalDate.ofYearDay(LocalDate.now().getYear(), LocalDate.now().minusDays(1).getDayOfYear()));
		
		zleceniaService.saveOrUpdate(tempZlec);
		
		zleceniaDoneService.deleteZlecenieDone(tempZlecDone);
		
		
		return "redirect:/daily-zlecenia";
	}
}
