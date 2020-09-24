package edc.projects.status.managing.service.implementation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edc.projects.status.managing.entities.Pracownicy;
import edc.projects.status.managing.entities.Swieta;
import edc.projects.status.managing.repository.PracownicyRepository;
import edc.projects.status.managing.service.PracownicyService;

@Service
public class PracownicyServiceImpl implements PracownicyService{

	private PracownicyRepository pracownicyRepository;

	private SwietaServiceimpl swietaService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	public PracownicyServiceImpl(EntityManager entityManager, PracownicyRepository pracownicyRepository, SwietaServiceimpl swietaService)
	{
		this.entityManager = entityManager;
		this.pracownicyRepository = pracownicyRepository;
		this.swietaService = swietaService;
	}
	
	
	@Override
	public List<Pracownicy> getAllPracownicy() {

		
		@SuppressWarnings("unchecked")
		List<Pracownicy> tempp = entityManager.createQuery("from pracownicy c order by c.nazwisko asc").getResultList();
		
		
		if(pracownicyRepository.findAll().isEmpty())
		{
			return null;
		}
		else
			return tempp;
		
	}
	@Override
	public Pracownicy getPracownikByLogin(String login) {
		
		
		String query = "from pracownicy where login=:log";
		
		@SuppressWarnings("unchecked")
		List<Pracownicy> listaPracownikow = entityManager.createQuery(query)
			.setParameter("log", login)	
			.getResultList();
		
		
		return listaPracownikow.get(0);		
	}
	@Override
	public void save(Pracownicy pracownik) {

		pracownicyRepository.save(pracownik);
		
	}
	@Override
	public void urlopy() {

		
		List<LocalDate> tempDate ;
		
		List<Swieta> swieta = swietaService.getAll();
		
		
		
		List<Integer> tempDateInteger = new ArrayList<Integer>();
		
		
		List<LocalDate> lokal = new ArrayList<LocalDate>();
		
		for(Swieta z : swieta)
		{
			lokal.add(z.getData());
		}
		
		System.out.println(lokal);
		int i = LocalDate.now().getDayOfYear();

		
		
		for(Pracownicy zxc : pracownicyRepository.findAll())
		{
			tempDateInteger.removeAll(tempDateInteger);
			tempDate = getDatesBetweenUsingJava8(zxc.getUrlopOd(), zxc.getUrlopDo().plusDays(1));
			
			tempDate.addAll(lokal);
			
			for(LocalDate k : tempDate)
			{
				tempDateInteger.add(k.getDayOfYear());
			}
			
			if(tempDateInteger.contains(i))
			{
				
				zxc.setUrlop(1);
				save(zxc);
			}
			else
			{
				
				zxc.setUrlop(0);
				save(zxc);
			}
		}
		
	}
	
	@Override
	public boolean jestNaUrlopie(String login)
	{
		
		@SuppressWarnings("unchecked")
		List<Pracownicy> tempPracownicy = entityManager.createQuery("from pracownicy where login=:log")
			.setParameter("log", login)
			.getResultList();
		
		Pracownicy tempPrac = tempPracownicy.get(0);
		
		
		System.out.println("CZY JEST NA URLOPIE? "  + tempPrac.toString() + (tempPrac.getUrlop()==1));
		
		if(tempPrac.getUrlop()==1)
			return true;
		else
			return false;
		
			
	}

	
	
	@Override
	public void setUrlop(String login, LocalDate od, LocalDate doo) {
	
		@SuppressWarnings("unchecked")
		List<Pracownicy> tempPrac = entityManager.createQuery("from pracownicy where login=:logg")
			.setParameter("logg", login)
			.getResultList();
		
		Pracownicy pra = tempPrac.get(0);
		pra.setUrlopOd(od);
		pra.setUrlopDo(doo);
		save(pra);
	}
	
	
	
	public static List<LocalDate> getDatesBetweenUsingJava8(
			  LocalDate startDate, LocalDate endDate) { 
			  
			    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
			    return IntStream.iterate(0, i -> i + 1)
			      .limit(numOfDaysBetween)
			      .mapToObj(i -> startDate.plusDays(i))
			      .collect(Collectors.toList()); 
			}
	
	
}
