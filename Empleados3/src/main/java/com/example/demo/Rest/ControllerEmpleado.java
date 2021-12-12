package com.example.demo.Rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Empleado;
import com.example.demo.entity.Feina;
import com.example.demo.repository.BaseDatos;
import com.example.demo.repository.BaseDatos1;



@RestController
@RequestMapping("empleados")
public class ControllerEmpleado implements Filter{
	
	@Autowired
	private BaseDatos baseDatos;
	
	@Override
	public void init(FilterConfig filterConfig)throws ServletException{	
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    httpServletResponse.setHeader("IT-Academy-Exercise", "Simple Service");
	    chain.doFilter(request, response);	
	}

	@Override
	public void destroy() {  
	}
	
	
	@GetMapping("/crearDB")
	public String crearDataBase() {
		
		BaseDatos1 dB = new BaseDatos1();
		
		dB.iniciar();
		ArrayList<Empleado> empleadosDb = dB.getBaseDatos();
		
		baseDatos.saveAll(empleadosDb);
		
		return "Base de datos creada";
	}
	
	
	@GetMapping("/Listar")
	public ResponseEntity <List<Empleado>> getEmpleado(){
		List<Empleado> empleados=baseDatos.findAll();//conexió a  la base de dades
		
		//Empleado empleado1=new Empleado();
						
		return ResponseEntity.ok(empleados);
}

	@RequestMapping(value="{empleadosId}")
	public ResponseEntity<Empleado>getEmpleadoById(@PathVariable("empleadosId")int empleadoId){
		Optional<Empleado>optionalEmpleado=baseDatos.findById(empleadoId);
		if(optionalEmpleado.isPresent()) {
			return ResponseEntity.ok(optionalEmpleado.get());
		}else {
			return ResponseEntity.noContent().build();
			
		}
	}
	@PostMapping("/create/{nombre}/{apellido}/{feina}")
	public ResponseEntity<Empleado> crearEmpleado(@PathVariable(name="nombre")String nombre,@PathVariable(name="apellido")String apellido,@PathVariable(name="feina")Feina feina){
		
		Empleado empleado= new Empleado(nombre, apellido, feina);
		baseDatos.save(empleado);
		return ResponseEntity.ok(empleado);
		
}
	@GetMapping("/FiltroxFeina/{feina}")
	public ResponseEntity<List<Empleado>> getEmpleadosFiltered(@PathVariable(name="feina") Feina feina){
		
		return ResponseEntity.ok(baseDatos.findAll().stream()
				.filter(x-> x.getCarrec().equals(feina.getCarrec())).collect(Collectors.toList()));
		

	}
	@DeleteMapping(value="{empleadosId}")// /empleados(DELETE)
	public ResponseEntity<Void>deleteEmpleado(@PathVariable("empleadosId")int empleadoId){
		baseDatos.deleteById(empleadoId);
		return  ResponseEntity.ok(null);
	}
	
	@PutMapping ("/update") // /empleados(update)
	public ResponseEntity<Empleado>UpdateEmpleado(@RequestBody Empleado empleado){
		Optional<Empleado>optionalEmpleado=baseDatos.findById(empleado.getId());
		
		if(optionalEmpleado.isPresent()) {
		 Empleado updateEmpleado=optionalEmpleado.get();
		 updateEmpleado.setNombre(empleado.getNombre());
		 updateEmpleado.setApellido(empleado.getApellido());
		 updateEmpleado.setCarrec(empleado.getCarrec());
		 
		 baseDatos.save(updateEmpleado);
		 return ResponseEntity.ok(updateEmpleado);
		}else {
			
			return  ResponseEntity.notFound().build();
		}
	}
	
	//para subir la foto
    @PostMapping(value = "/uploadPhoto")
       public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

           ResponseEntity<?> result = null;

           try {
                  if (!file.isEmpty()) {

                      if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
                          throw new IOException("Error- Tipo de archivo aceptado: JPEG o PNG");
                      }
                      File pujarArxiu = new File("./uploads");

                      String rutaArchivo = pujarArxiu.getAbsolutePath() + "//" + file.getOriginalFilename();
                      FileOutputStream output = new FileOutputStream(rutaArchivo);
                      output.write(file.getBytes());
                      output.close();
                  }else {
                      throw new IOException("Debe seleccionar una imagen");
                  }
               result = ResponseEntity.status(HttpStatus.OK).body("Imagen subida correctamente");

           } catch (Exception e) {
               result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
           }

           return result;

       }
	
	/*@PostMapping(value="/form")
	public String guardar(@RequestParam("fichero") MultipartFile foto) {
	
		String message=null;
		try {
			if(!foto.isEmpty()) {
				
				Path rootPath=Paths.get("uploads")
					.resolve(foto.getOriginalFilename())
					.toAbsolutePath();
				Files.copy(foto.getInputStream(),rootPath);
				message="Ficheros subido:"+foto.getOriginalFilename();
			}else {
				message="Fichero vacio";
			}
		}catch(IOException e) {
			message=e.getMessage();
		}
		return message;
	}*/
  //download photo
    @GetMapping("/downloadPhoto/{filename}") // localhost:8080/employees/downloadPhoto/{filename} -> GET
    public ResponseEntity<Resource> getEmployeePhoto(@PathVariable("filename") String filename, HttpServletResponse response) {

          String upload_folder = "./uploads/";
            Path path = Paths.get(upload_folder).toAbsolutePath().resolve(filename);

            Resource resource;
            try {
                resource = new UrlResource(path.toUri());

            } catch (MalformedURLException e) {
                throw new RuntimeException("Problema al leer el archivo", e);
            }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + resource.getFilename())
                .body(resource);
    }
    /*
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource>verFoto(@PathVariable String filename){
		
		Resource recurso=null;
		
		Path pathFoto=null;
		
		try {
			pathFoto=Paths.get("uploads").resolve(filename).toAbsolutePath();
			recurso=new UrlResource(pathFoto.toUri());
			if(!recurso.exists()||!recurso.isReadable()) {
				throw new RuntimeException("Error:no se puede  cargar la imagen:"+pathFoto.toString());
			}
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
	return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+
					recurso.getFilename()+"\"")
			.body(recurso);
	
	}*/
}
