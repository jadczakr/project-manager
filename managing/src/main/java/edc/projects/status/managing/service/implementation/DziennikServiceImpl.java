package edc.projects.status.managing.service.implementation;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edc.projects.status.managing.entities.Dziennik;
import edc.projects.status.managing.repository.DziennikRepository;
import edc.projects.status.managing.service.DziennikService;

@Service
public class DziennikServiceImpl implements DziennikService {

	
	private DziennikRepository dziennikRepository;
	
	@Autowired
	private EntityManager entityManager ;
	
	
	@Autowired
	public DziennikServiceImpl(EntityManager entityManager, DziennikRepository dziennikRepository)
	{
		this.entityManager = entityManager;
		this.dziennikRepository = dziennikRepository;
	}
	
	
	@Override
	public void addNotatke(Dziennik dziennik)
	{
		dziennikRepository.save(dziennik);
	}
	
	
	@Override
	public List<Dziennik> getAll2(String login)
	{
		@SuppressWarnings("unchecked")
		List<Dziennik> dziennik = entityManager.createQuery("from dziennik where uzytkownik=:jedynka and typ=:jed order by priorytet desc")
			.setParameter("jedynka", login)
			.setParameter("jed", "0")
			.getResultList();
		
		return dziennik;
	}
	
	@Override
	public void usunPoTerminie()
	{
		
		System.out.println("wywolanie usunpoterminie");
		System.out.println("wywolanie usunpoterminie");
		System.out.println("wywolanie usunpoterminie");
		System.out.println("wywolanie usunpoterminie");
		System.out.println("wywolanie usunpoterminie");
		
		List<Dziennik> lista = getAll2("wszyscy");
	
		int i = LocalDate.now().getDayOfYear();

		if(lista.isEmpty())
		{
			
		}
		else
		{
			
				for(Dziennik p : lista)
				{
					if(p.getDate()==null)
					{
						lista.remove(p);
					}
				}
				
				for(Dziennik k : lista)
				{
					if(i==k.getDate().getDayOfYear())
					{
						dziennikRepository.delete(k);
					}
				}
	}
	}
	
	@Override
	public void deleteNotatka(Dziennik dziennik)
	{
		dziennikRepository.delete(dziennik);
	}
	
	
	@Override
	public Dziennik getNotatkaById(Integer theId)
	{
		return dziennikRepository.getOne(theId);
	}
	
	@Override
	public void deleteNotatkaById(Integer theId)
	{
		dziennikRepository.delete(dziennikRepository.getOne(theId));
	}


	@Override
	public List<Dziennik> getAllOgolne() {
		
		@SuppressWarnings("unchecked")
		List<Dziennik> dziennik = entityManager.createQuery("from dziennik where typ=:jedynka order by priorytet desc")
			.setParameter("jedynka", "1")
			.getResultList();
		
		return dziennik;
	}
	
}
