package edc.projects.status.managing.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Table
@Entity(name="dziennik")
public class Dziennik {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="opis")
	private String opis;
	
	@Column(name="priorytet")
	private String priorytet;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="data_wygasniecia")
	private LocalDate date;

	@Column(name="typ")
	private String typ;
	
	@Column(name="uzytkownik")
	private String uzytkownik;
	
	
	public String getUzytkownik() {
		return uzytkownik;
	}


	public void setUzytkownik(String uzytkownik) {
		this.uzytkownik = uzytkownik;
	}


	public String getTyp() {
		return typ;
	}


	public void setTyp(String typ) {
		this.typ = typ;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Dziennik()
	{
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getPriorytet() {
		return priorytet;
	}

	public void setPriorytet(String priorytet) {
		this.priorytet = priorytet;
	}
	
	
	
}
