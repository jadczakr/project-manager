package edc.projects.status.managing.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edc.projects.status.managing.entities.Pracownicy;
import edc.projects.status.managing.entities.Swieta;
import edc.projects.status.managing.entities.Zlecenia;
import edc.projects.status.managing.repository.ZleceniaRepository;
import edc.projects.status.managing.service.ZleceniaService;

@Service
public class ZleceniaServiceImpl implements ZleceniaService {

	
	private ZleceniaRepository zleceniaRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	private PracownicyServiceImpl pracownicyServiceImpl ;
	
	
	@Autowired
	public ZleceniaServiceImpl(ZleceniaRepository zleceniaRepository, EntityManager entityManager, PracownicyServiceImpl pracownicyServiceImpl, SwietaServiceimpl swietaServiceImpl)
	{
		this.zleceniaRepository = zleceniaRepository;
		this.entityManager = entityManager;
		this.pracownicyServiceImpl = pracownicyServiceImpl ;
	}

	@Override
	public void addZlecenie(Zlecenia tempZlecenie) {
		
		zleceniaRepository.save(tempZlecenie);
		
	}

	@Override
	public void deleteZlecenie(Integer id)
	{
		zleceniaRepository.deleteById(id);
	}
	
	@Override
	public List<Zlecenia> getFinalAllZlecenia(String login)
	{
		@SuppressWarnings("unchecked")
		List<Zlecenia> tempZlecenia = entityManager.createQuery("from zlecenia where glowny=:login").setParameter("login", login).getResultList();
		
		return tempZlecenia;
	}
	
	@Override
	public Zlecenia getZlecenie(Integer id) {

		return zleceniaRepository.getOne(id);
		
	}

	@Override
	public void saveOrUpdate(Zlecenia tempZlecenie) {
		
		zleceniaRepository.save(tempZlecenie);
		
	}

	@Override
	public Set<Zlecenia> getZleceniaZZastepstewm(String login) {
		
		LocalDate localDate = LocalDate.now();
		String dzien = getDzienPolski(LocalDate.now().getDayOfWeek().toString());
		String queryZastepstwo = "from zlecenia where zastepca1=:zastepca1" + " and (" + dzien + "=true OR cykl=1) and (data!=:datka OR mailing=true)";
		
		@SuppressWarnings("unchecked")
		List<Zlecenia> listaZastepstw = entityManager.createQuery(queryZastepstwo)
			.setParameter("zastepca1", login)
			.setParameter("datka", LocalDate.now())
			.getResultList();
		
		Set<Zlecenia> finalZlecenia = new HashSet<Zlecenia>();
		
		
		for(Zlecenia p : listaZastepstw)
		{
			Pracownicy tempPrac = pracownicyServiceImpl.getPracownikByLogin(p.getGlowny().trim()) ;
			
			if(tempPrac.getUrlop()>0)
				{
					finalZlecenia.add(p);
				}
		}
	
		return finalZlecenia;

	}

	
	@Override
	public List<Zlecenia> getDzienneZlecenia(String login)
	{
		String dzien = getDzienPolski(LocalDate.now().getDayOfWeek().toString());
		
		@SuppressWarnings("unchecked")
		List<Zlecenia> zlecenia = entityManager.createQuery("from zlecenia where (" + dzien + "=true OR cykl=:cykll) and glowny=:login and (data!=:dzisiaj OR mailing=true) order by id asc ")
			.setParameter("login", login)
			.setParameter("cykll", 1)
			.setParameter("dzisiaj", LocalDate.now())
			.getResultList();
		
		return zlecenia;
	}
	
	
	@Override
	public List<Zlecenia> getZakresoweZlecenia(String login)
	{
		String params[];
		Integer pierwsza;
		Integer druga;
		@SuppressWarnings("unchecked")
		List<Zlecenia> zlecenia = entityManager.createQuery("from zlecenia where glowny=:log and cykl=:trojeczka and wykonano=false and zakresy != null")
			.setParameter("log", login)
			.setParameter("trojeczka", 3)
			.getResultList();
		
		List<Zlecenia> outputZlecenia = new ArrayList<Zlecenia>();
		
		for(Zlecenia tempZlec : zlecenia)
		{
			params = tempZlec.getZakresy().split("-");
			pierwsza = Integer.parseInt(params[0]);
			druga = Integer.parseInt(params[1]);
			
			if(pierwsza <= LocalDate.now().getDayOfMonth() && LocalDate.now().getDayOfMonth() <= druga) 
			{
				outputZlecenia.add(tempZlec);
			}
		}
		
		return outputZlecenia;
			
	}
	

	public String getDzienPolski(String angielski)
	{
		switch(angielski)
		{
			case "MONDAY":
				return "poniedzialek";
			case "TUESDAY":
				return "wtorek";
			case "WEDNESDAY":
				return "sroda";
			case "THURSDAY":
				return "czwartek";
			case "FRIDAY":
				return "piatek";
		
		}
		return "niedziela";
	}


	public void pierwszyDzienMiesiaca()
	{
		if(LocalDate.now().getDayOfMonth()==1)
		{
			@SuppressWarnings("unchecked")
			List<Zlecenia> tempZlec = entityManager.createQuery("from zlecenia where wykonano!=null").getResultList();
			
			for(Zlecenia x: tempZlec)
			{
				x.setWykonano(false);
				saveOrUpdate(x);
			}
		}
	}
	
	}