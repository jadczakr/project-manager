package edc.projects.status.managing.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import net.bytebuddy.implementation.bind.annotation.Default;

@Table
@Entity(name="zlecenia_done")
public class ZleceniaDone {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="firma")
	private String firma;
	
	@Column(name="nazwa_zlecenia")
	private String nazwaZlecenia;
	
	@Column(name="status")
	private String status;
	
	@Column(name="path")
	private String path;
	
	@Column(name="dzien_tygodnia")
	private String dzienTygodnia;
	
	@Column(name="godzina_dodania")
	private LocalTime godzinaDodania;
	
	@Column(name="wykonawca")
	private String wykonawca;
	
	@Column(name="opis")
	private String opis;
	
	@Column(name="data_dodania")
	private LocalDate dataDodania;
		
	@Column(name="id_zlecenia")
	private Integer idZlecenia;
	
	@Value("1")
	@Column(name="standard")
	private Integer standard;
	
	
	
	
	public Integer getStandard() {
		return standard;
	}


	public void setStandard(Integer standard) {
		this.standard = standard;
	}


	public String getFirma() {
		return firma;
	}


	public void setFirma(String firma) {
		this.firma = firma;
	}


	public LocalTime getGodzinaDodania() {
		return godzinaDodania;
	}


	public void setGodzinaDodania(LocalTime godzinaDodania) {
		this.godzinaDodania = godzinaDodania;
	}


	public LocalDate getDataDodania() {
		return dataDodania;
	}


	public void setDataDodania(LocalDate dataDodania) {
		this.dataDodania = dataDodania;
	}


	public Integer getIdZlecenia() {
		return idZlecenia;
	}


	public void setIdZlecenia(Integer idZlecenia) {
		this.idZlecenia = idZlecenia;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getWykonawca() {
		return wykonawca;
	}


	public void setWykonawca(String wykonawca) {
		this.wykonawca = wykonawca;
	}


	public ZleceniaDone() {}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getNazwaZlecenia() {
		return nazwaZlecenia;
	}

	public void setNazwaZlecenia(String nazwaZlecenia) {
		this.nazwaZlecenia = nazwaZlecenia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDzienTygodnia() {
		return dzienTygodnia;
	}

	public void setDzienTygodnia(String dzienTygodnia) {
		this.dzienTygodnia = dzienTygodnia;
	}

	public LocalTime getGodzinaMax() {
		return godzinaDodania;
	}

	public void setGodzinaMax(LocalTime godzinaDodania) {
		this.godzinaDodania = godzinaDodania;
	}


	@Override
	public String toString() {
		return "ZleceniaDone [id=" + id + ", firma=" + firma + ", nazwaZlecenia=" + nazwaZlecenia + ", status=" + status
				+ ", path=" + path + ", dzienTygodnia=" + dzienTygodnia + ", godzinaDodania=" + godzinaDodania
				+ ", wykonawca=" + wykonawca + ", opis=" + opis + ", dataDodania=" + dataDodania + ", idZlecenia="
				+ idZlecenia + "]";
	}
	
	
	
}
