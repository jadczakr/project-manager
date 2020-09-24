package edc.projects.status.managing.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edc.projects.status.managing.entities.Zlecenia;
import edc.projects.status.managing.entities.ZleceniaDone;
import edc.projects.status.managing.repository.ZleceniaDoneRepository;
import edc.projects.status.managing.service.ZleceniaDoneService;

@Service
public class ZleceniaDoneServiceImpl implements ZleceniaDoneService {

	
	private ZleceniaDoneRepository zleceniaDoneRepository;
	private ZleceniaServiceImpl zleceniaService;
	
	
	@Autowired
	private EntityManager entityManager;
	

	
	@Autowired
	public ZleceniaDoneServiceImpl(EntityManager entityManager, ZleceniaDoneRepository zleceniaDoneRepository, ZleceniaServiceImpl zleceniaService)
	{
		this.zleceniaService = zleceniaService ;
		this.entityManager = entityManager ;
		this.zleceniaDoneRepository = zleceniaDoneRepository;
	}
	
	@Override
	public ZleceniaDone getZlecenieById(Integer id)
	{
		return zleceniaDoneRepository.getOne(id);
	}
	
	@Override
	public ZleceniaDone getZlecenieDoneById(Integer id) {
		
		return zleceniaDoneRepository.getOne(id);
	
	}
	
	@Override
	public void addZlecenieDone(ZleceniaDone done) {
	
		zleceniaDoneRepository.save(done);
		
	}
	
	
	@Override
	public List<ZleceniaDone> getAnulowane(String login)
	{
		@SuppressWarnings("unchecked")
		List<ZleceniaDone> zleconka = entityManager.createQuery("from zlecenia_done where wykonawca=:login AND status=:anulowane")
		.setParameter("login", login)
		.setParameter("anulowane", "ANULOWANO")
		.getResultList();
		 
		List<ZleceniaDone> zleconka2 = new ArrayList<ZleceniaDone>();
		
		for(ZleceniaDone xtent : zleconka)
		{
			if(xtent.getDataDodania().equals(LocalDate.now()))
			{
				zleconka2.add(xtent);
			}
		}
		
		return zleconka2;
	}
	
	@Override
	public List<ZleceniaDone> getZleceniaDoneByLogin(String login) {
	
		@SuppressWarnings("unchecked")
		List<ZleceniaDone> lk = entityManager.createQuery("from zlecenia_done where wykonawca=:wyk AND data_dodania=:dquery")
		.setParameter("wyk", login)
		.setParameter("dquery", LocalDate.now())
		.getResultList();

		return lk;
	}
	
	
	@Override
	public List<ZleceniaDone> getZleceniaDoneByLogin2(String login)
	{
	
		@SuppressWarnings("unchecked")
		List<ZleceniaDone> zd = entityManager.createQuery("from zlecenia_done where wykonawca=:wyk")
			.setParameter("wyk", login)
			.getResultList();
		
		return zd;
	}

	@Override
	public void saveOrUpdate(ZleceniaDone zlecenie) {
		
		zleceniaDoneRepository.save(zlecenie);
	}
	
	
	@Override
	public List<ZleceniaDone> getZawieszone(String login)
	{
		@SuppressWarnings("unchecked")
		List<ZleceniaDone> zlecenia = entityManager.createQuery("from zlecenia_done where status=:akcept AND wykonawca=:login order by dataDodania")
			.setParameter("login",login)
			.setParameter("akcept","W AKCEPTACJI")
			.getResultList();
		
		return zlecenia;
	}
	
	
	@Override
	public List<ZleceniaDone> getDoneNiestandardowe(String login)
	{
		 
		@SuppressWarnings("unchecked")
		List<ZleceniaDone> zrobioneDzis = entityManager.createQuery("from zlecenia_done where dataDodania=:dzis AND wykonawca=:login AND standard=:zero")
			.setParameter("dzis", LocalDate.now())
			.setParameter("login", login)
			.setParameter("zero", 0)
			.getResultList();
		
	
		return zrobioneDzis;
	}
	
	@Override
	public List<ZleceniaDone> getDoneByDetails(String query)
	{

		@SuppressWarnings("unchecked")
		List<ZleceniaDone> tempZlecenia = entityManager.createQuery(query).getResultList();
		
		return tempZlecenia;
	}

	
	
	String k = "from zlecenia_done where " + transform(LocalDate.now().getDayOfWeek().toString()) +"=true" ;
	// PRZYKLAD ZASTOSOWANIA METODY TRANSFORM 
	private String transform(String day)
	{
		
		switch(day)
		{
			default:
				return "SATURDAY";
				
			case "MONDAY":
				return "poniedzialek";
				
			case "TUESDAY":
				return "wtorek";
				
			case "WEDNESDAY":
				return "sroda";
				
			case "THURSDAY" :
				return "czwartek";
				
			case "FRIDAY":
				return "piatek";
				
		}
			
	}

	@Override
	public void deleteZlecenieDone(ZleceniaDone zleconko) {
		
		zleceniaDoneRepository.delete(zleconko);
		
	}
}
