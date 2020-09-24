package edc.projects.status.managing.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name="pracownicy")
public class Pracownicy {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@Column(name="imie")
	private String imie;
	
	@Column(name="nazwisko")
	private String nazwisko;
	
	
	@Column(name="login")
	private String login;
	
	
	@Column(name="haslo")
	private String haslo;

	@Column(name="urlop_od")
	private LocalDate urlopOd;
	
	
	@Column(name="urlop_do")
	private LocalDate urlopDo;
	
	@Column(name="urlop")
	private Integer urlop;
	
	
	public LocalDate getUrlopOd() {
		return urlopOd;
	}


	public void setUrlopOd(LocalDate urlopOd) {
		this.urlopOd = urlopOd;
	}


	public Integer getUrlop() {
		return urlop;
	}


	public void setUrlop(Integer urlop) {
		this.urlop = urlop;
	}


	public LocalDate getUrlopDo() {
		return urlopDo;
	}


	public void setUrlopDo(LocalDate urlopDo) {
		this.urlopDo = urlopDo;
	}


	@Override
	public String toString() {
		return imie + " " + nazwisko;
	}


	public Pracownicy() {}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getImie() {
		return imie;
	}


	public void setImie(String imie) {
		this.imie = imie;
	}


	public String getNazwisko() {
		return nazwisko;
	}


	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getHaslo() {
		return haslo;
	}


	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
	
	public String getImieNazwisko()
	{
		return this.imie + " " + this.nazwisko ;
	}
	
	
}
