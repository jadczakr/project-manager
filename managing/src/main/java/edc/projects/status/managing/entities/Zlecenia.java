package edc.projects.status.managing.entities;


import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table
@Entity(name="zlecenia")
public class Zlecenia {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="firma")
	private String firma;
	
	@Column(name="nazwa_zlecenia")
	private String nazwaZlecenia;
	
	@Column(name="path")
	private String path;
	
	@Column(name="wykonano")
	private boolean wykonano;
	
	@Column(name="godzina_max")
	private LocalTime godzinaMax;
	
	@Column(name="glowny_wykonawca")
	private String glowny;
	
	@Column(name="zastepca1")
	private String zastepca1;
	
	@Column(name="path_manual")
	private String manualPath;
	
	@Column(name="data_ostatniej_modyfikacji")
	private LocalDate data;
	
	@Column(name="standard")
	private boolean standard;
	
	@Column(name="opis")
	private String opis;
	
	@Column(name="zakresy")
	private String zakresy;

	@Column(name="poniedzialek")
	boolean poniedzialek;
	
	@Column(name="wtorek")
	boolean wtorek;
	
	@Column(name="sroda")
	boolean sroda;
	
	@Column(name="czwartek")
	boolean czwartek;
	
	@Column(name="piatek")
	boolean piatek;
	
	@Column(name="daily")
	boolean daily;
	
	@Column(name="mailing")
	boolean mailing;
	
	@Column(name="cykl")
	Integer cykl;
	
	@Column(name="today")
	boolean today;

	public String getZakresy() {
		return zakresy;
	}

	public void setZakresy(String zakresy) {
		this.zakresy = zakresy;
	}

	public boolean isToday() {
		return today;
	}

	public void setToday(boolean today) {
		this.today = today;
	}

	public Integer getCykl() {
		return cykl;
	}

	public boolean isPoniedzialek() {
		return poniedzialek;
	}

	public void setPoniedzialek(boolean poniedzialek) {
		this.poniedzialek = poniedzialek;
	}

	public boolean isWtorek() {
		return wtorek;
	}

	public void setWtorek(boolean wtorek) {
		this.wtorek = wtorek;
	}

	public boolean isSroda() {
		return sroda;
	}

	public void setSroda(boolean sroda) {
		this.sroda = sroda;
	}

	public boolean isCzwartek() {
		return czwartek;
	}

	public void setCzwartek(boolean czwartek) {
		this.czwartek = czwartek;
	}

	public boolean isPiatek() {
		return piatek;
	}

	public void setPiatek(boolean piatek) {
		this.piatek = piatek;
	}

	public boolean isDaily() {
		return daily;
	}

	public void setDaily(boolean daily) {
		this.daily = daily;
	}

	public boolean isMailing() {
		return mailing;
	}

	public void setMailing(boolean mailing) {
		this.mailing = mailing;
	}

	public Integer isCykl() {
		return cykl;
	}

	public void setCykl(Integer cykl) {
		this.cykl = cykl;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean getStandard() {
		return standard;
	}

	public void setStandard(boolean standard) {
		this.standard = standard;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getManualPath() {
		return manualPath;
	}

	public void setManualPath(String manualPath) {
		this.manualPath = manualPath;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getGlowny() {
		return glowny;
	}

	public void setGlowny(String glowny) {
		this.glowny = glowny;
	}

	public String getZastepca1() {
		return zastepca1;
	}

	public void setZastepca1(String zastepca1) {
		this.zastepca1 = zastepca1;
	}

	public LocalTime getGodzinaMax() {
		return godzinaMax;
	}

	public void setGodzinaMax(LocalTime godzinaMax) {
		this.godzinaMax = godzinaMax;
	}

	public String getNazwaZlecenia() {
		return nazwaZlecenia;
	}

	public void setNazwaZlecenia(String nazwaZlecenia) {
		this.nazwaZlecenia = nazwaZlecenia;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public Zlecenia() {}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "Zlecenia [id=" + id + ", firma=" + firma + ", nazwaZlecenia=" + nazwaZlecenia + ", path=" + path
				+ ", wykonano=" + wykonano + ", godzinaMax=" + godzinaMax + ", glowny=" + glowny + ", zastepca1="
				+ zastepca1 + ", manualPath=" + manualPath + ", data=" + data + ", standard=" + standard + ", opis="
				+ opis + ", zakresy=" + zakresy + ", poniedzialek=" + poniedzialek + ", wtorek=" + wtorek + ", sroda="
				+ sroda + ", czwartek=" + czwartek + ", piatek=" + piatek + ", daily=" + daily + ", mailing=" + mailing
				+ ", cykl=" + cykl + ", today=" + today + "]";
	}

	public boolean isWykonano() {
		return wykonano;
	}

	public void setWykonano(boolean wykonano) {
		this.wykonano = wykonano;
	}

	public Zlecenia(Zlecenia zlec)
	{
		this.firma = zlec.firma;
		this.nazwaZlecenia = zlec.nazwaZlecenia;
		this.path = zlec.path ;
		this.godzinaMax = zlec.godzinaMax;
		this.glowny = zlec.glowny;
		this.zastepca1 = zlec.zastepca1; 
		this.manualPath = zlec.manualPath;
		this.data = zlec.data;
		this.standard = zlec.standard;
		this.opis = zlec.opis;
		this.zakresy = zlec.zakresy;
		this.poniedzialek = zlec.poniedzialek; 
		this.wtorek = zlec.wtorek;
		this.sroda = zlec.sroda;
		this.czwartek = zlec.czwartek;
		this.piatek = zlec.piatek;
		this.daily = zlec.daily;
		this.mailing = zlec.mailing;
		this.cykl = zlec.cykl;
		this.today = zlec.today;
		this.wykonano = zlec.wykonano;
	}
}
