package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Empleado;

// DAO

@Repository
public interface BaseDatos extends JpaRepository<Empleado,Integer>{
	
	/*public List<Empleado> save(Empleado empleado);
	public List<Empleado>deleteById(int id);
	public List<Empleado>findById(int id);
	
	
	public void inserta(Empleado empleado);
	public void borrar(int id);
	public void modifica(Empleado empleado);
	public Empleado getEmpleado(int id);*/
}
