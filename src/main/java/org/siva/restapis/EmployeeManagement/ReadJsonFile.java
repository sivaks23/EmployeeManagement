package org.siva.restapis.EmployeeManagement;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadJsonFile {

	public static Map<Long, Employee> readJSON(String path) {
		JSONParser parser = new JSONParser();
		Map<Long, Employee> employees = new HashMap<>();
		try {
	        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(path));
	        for (Object o : jsonArray) {
	            JSONObject employee = (JSONObject) o;

	            long employeeId = (long) employee.get("Employee ID");
	            String cityName = (String) employee.get("City Name");
	            long salary = (long) employee.get("Salary");
	            String firstName = (String) employee.get("First Name");
	            String secondName = (String) employee.get("Second Name");
	            long managerId = (long) employee.get("Manager Emp Id");
	            Employee emp = new Employee(employeeId, cityName, salary, firstName, secondName,managerId);
	            employees.put(employeeId, emp);
	            
	        }
        } catch (Exception e) {
        	 e.printStackTrace();
        }
		return employees;
	}
}
