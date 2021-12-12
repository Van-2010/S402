package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name="empleados")
public class Empleado implements Serializable {
	
	//CommonsMultipartFile fichero;
	private String fotoName;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)//genera autoincrement id
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	private Feina feina;
	
	@Column(name="carrec")
	private String carrec;
	
	@Column(name="salari")
	private double salari;

	public Empleado(String nombre, String apellido, Feina feina) {
		//this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.feina = feina;
		this.salari=feina.getSalari();
		this.carrec=feina.getCarrec();
	}

	public Empleado() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	
	public double getSalari() {
		return salari;
	}

	public void setSalari(int salari) {
		this.salari = salari;
	}

	
	public String getCarrec() {
		return carrec;
	}

	public void setCarrec(String carrec) {
		this.carrec = carrec;
	}
	

	/*public CommonsMultipartFile getFichero() {
		return fichero;
	}

	public void setFichero(CommonsMultipartFile fichero) {
		this.fichero = fichero;
	}*/

	public String getFotoName() {
		return fotoName;
	}

	public void setFotoName(String fotoName) {
		this.fotoName = fotoName;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", feina=" + carrec + ", carrec="
				+ carrec + ", salari=" + salari + "]";
	}
	
	
	
}
