
package com.example;


import com.example.interfaceService.EmployeeService;

import com.example.model.Employee;
import com.example.storagefile.FileStorageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class EmployeeController {
      @Autowired
      private EmployeeService employeeservice;
      @Autowired
  	private FileStorageService fileStorageService;
      

    @PostMapping(value = "/employee/save")
   	
    public ResponseEntity<?> save(@RequestBody Employee entity) {
		Map<String, Object> map = new HashMap<>();
		try {
			Employee employee = employeeservice.save(entity);
			map.put("message", "Employee save successfully");
			map.put("Data", employee);
			map.put("Status code", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Employee saved failed");
			map.put("Data", null);
			map.put("Status code", 400);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}
    
    @PostMapping("/saveemployee_withfile")
	public ResponseEntity<Map> saveFormData(@ModelAttribute Employee addEmployee,
			@RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			String fileName = fileStorageService.storeFile(file);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(fileName).toUriString();
			addEmployee.setImages(fileName);
			addEmployee.setImagesUri(fileDownloadUri);

//			UserModel user = userService.findById(userId).get();
//			advertisingForm.setUser(user);

			addEmployee = employeeservice.save(addEmployee);
			map.put("status", "Success");
			map.put("data", addEmployee);
			map.put("message", "Data saved successfully");
			return ResponseEntity.ok(map);

		} catch (Exception e) {
			map.put("status", "failed");
			map.put("data", null);
			map.put("message", e.getLocalizedMessage());
			return ResponseEntity.status(500).body(map);
		}

	}
	
    
    @PostMapping(value = "/employee/getAll")
    @Transactional
    public ResponseEntity<?> getAll() {
       Map<Object,Object> map = new HashMap<Object, Object>();
        try {
           
            List<Employee> entityList = (List<Employee>) employeeservice.findAll();
            map.put("Data", entityList);
            map.put("message", "Employee get successfully");
			map.put("Status code", 200);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }
    
    @GetMapping(value = "/employee/getOne/{id}")
   
    public ResponseEntity<?> getOne(@PathVariable(value = "id") long id) {
    	Map<String, Object> map = new HashMap<>();
        try {
           
        	Employee employee = employeeservice.findById((int) id).get();
            map.put("Data", employee);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }
    
    @PostMapping("/employee/update")
	public ResponseEntity<?> update(@RequestBody Employee entity) {
		Map<String, Object> map = new HashMap<>();
		try {
			Employee employee = employeeservice.save(entity);
			map.put("message", "Employee updated successfully");
			map.put("Data", employee);
			map.put("Status code", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Employee updated failed");
			map.put("Data", null);
			map.put("Status code", 400);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}
      
    @GetMapping(value = "/employee/delete/{id}")
    
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
    	Map<String, Object> map = new HashMap<>();
		Employee employee = employeeservice.findById((int) id).get();
		try {
			employeeservice.delete(employee);
			map.put("message", "Employee deleted successfully");
			map.put("Data", employee);
			map.put("Status code", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Employee deletation failed");
			map.put("Data", null);
			map.put("Status code", 400);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
    } 
    
    @GetMapping(value = "/employee/search")
	public ResponseEntity<?> search(@RequestParam(value = "searchText") String searchText) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Employee> customer = employeeservice.searchEmployee(searchText);
			map.put("message", "Data get successfully");
			map.put("Data", customer);
			map.put("Status code", 200);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Data fetch failed");
			map.put("Data", null);
			map.put("Status code", 400);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}
}
