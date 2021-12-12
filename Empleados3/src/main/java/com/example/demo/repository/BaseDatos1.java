package com.example.demo.repository;

import java.util.ArrayList;

import com.example.demo.entity.Empleado;
import com.example.demo.entity.Feina;


public class BaseDatos1 {
	
	ArrayList<Empleado> BdEmpleados= new ArrayList<Empleado>();
	
	public void iniciar() {
		
		BdEmpleados.add(new Empleado("Anna", "Tarres Perez",Feina.ACTOR));
		BdEmpleados.add(new Empleado("Oriol", "Tufet Segarra",Feina.CUINER));
		BdEmpleados.add(new Empleado("Cristina", "Turo CapVila",Feina.METGE));
		BdEmpleados.add(new Empleado("Antoni", "Barbera Soplillo",Feina.SECRETARIA));
	}
	
	public ArrayList<Empleado> getBaseDatos(){
		
		return BdEmpleados;
	}

	public void saveAll(ArrayList<Empleado> empleadosDb) {
		// TODO Auto-generated method stub
		
	}

}

