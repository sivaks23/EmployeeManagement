package org.siva.restapis.EmployeeManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeServices {
	private static Map<Long, Employee> empMap = null;
	private static Map<Long, List<Employee>> managerMap = null;
	
	private static EmployeeServices service = null;
	
	private EmployeeServices() {
		initEmps();
        initManager();
	}
	
	//Method to provide instance of EmployeeServices
    public static EmployeeServices getService() {
    	if(service == null) {
    		synchronized(EmployeeServices.class) {
    			if(service == null) {
    				service = new EmployeeServices();
    			}
    		}
    	}
    	return service;
    }
    
    //Initialize the managerMap, used for several computations.
    private void initManager() {
    	managerMap = new ConcurrentHashMap<>();
    	for(Employee emp : empMap.values()) {
    		long managerId = emp.getManagerId();
    		managerMap.computeIfPresent(managerId, (id, list) -> { 
    			list.add(emp);
    			return list;
   			});
   			managerMap.computeIfAbsent(managerId, (id -> {
   				List<Employee> list = new ArrayList<>();
   				list.add(emp);
   				return list;
    		}));
    	}
    }
    
    //Initialize employees by loading data from JSON
    private void initEmps() {
        String path = "C:\\Users\\siyellap.ORADEV\\Documents\\MorganStanley\\EmployeeData.json";
        HashMap<Long,Employee> tempMap = (HashMap<Long, Employee>)ReadJsonFile.readJSON(path);
        empMap = new ConcurrentHashMap<>(tempMap);
    }
    
    //Update manager of empId with mgrId
    public Employee updateManager(long empId, long mgrId) {
    	Employee emp = empMap.get(empId);
    	long prevMgrId = emp.getManagerId();
    	List<Employee> subs = managerMap.get(prevMgrId);
    	subs.removeIf( e -> (e.getEmployeeId() - empId) == 0);
    	if(subs.size() == 0) {
    		managerMap.remove(prevMgrId);
    	}
    	emp.setManagerId(mgrId);
    	
    	managerMap.computeIfPresent(mgrId, (id, list) -> { 
			list.add(emp);
			return list;
		});
		managerMap.computeIfAbsent(mgrId, (id -> {
			List<Employee> list = new ArrayList<>();
			list.add(emp);
			return list;
		}));
    	
    	return emp;
    }
    
    //Find out employee having maximum direct sub-ordinates
    public Employee employeeWithMaxSubOrdinates() {
    	int max = Integer.MIN_VALUE;
    	Employee out = null;
    	
    	for (Map.Entry<Long, List<Employee>> entry : managerMap.entrySet()) {
    		int size = entry.getValue().size();
    		long id = entry.getKey();
    		if(size > max) {
    			max = size;
    			out = empMap.get(id);
    		}
    	}
    	return out;
    }
    
    //Compute total salary of all sub-ordinates for a given empId
    public long totalSalary(long empId) {
    	Queue<Employee> queue = new LinkedList<>();
    	queue.add(empMap.get(empId));
    	long salary = 0;
    	while(!queue.isEmpty()) {
    		Employee emp = queue.remove();
    		List<Employee> subs = managerMap.get(emp.getEmployeeId());
    		if(subs != null) {
        		for(Employee e : subs) {
        			queue.add(e);
        			salary += e.getSalary();
        		}
    		}
    	}
    	return salary;
    }
    
    //Print the organisation structure
    public String orgStructure() {	
    	List<Employee> list = managerMap.get(new Long(0));
    	Employee root = list.get(0);
    	Queue<Employee> queue = new LinkedList<>();
    	queue.add(root);
    	queue.add(null);
    	System.out.println(root.getFirstName());
    	while(!queue.isEmpty()) {
    		Employee emp = queue.remove();
    		if(emp == null) {
    			if(!queue.isEmpty())
    				queue.add(null);
    			System.out.println();
    		}else {
    			List<Employee> subs = managerMap.get(emp.getEmployeeId());
    			if(subs != null) {
    				for(Employee e : subs) {
    					System.out.print(e.getFirstName() + " ");
    					queue.add(e);
    				}
    			}
    		}
    	}
    	return managerMap.toString();
    }
    
    public boolean isLevelSame(long empId1, long empId2) {
    	int level1 = findLevel(empId1);
    	int level2 = findLevel(empId2);
    	
    	if(level1 == level2 && level1 != -1)
    		return true;
    	else
    		return false;
    	
    }
    
    private int findLevel(long empId) {
    	List<Employee> list = managerMap.get(new Long(0));
    	Employee root = list.get(0);
    	Queue<Employee> queue = new LinkedList<>();
    	queue.add(root);
    	queue.add(null);
    	int level = 1;
    	while(!queue.isEmpty()) {
    		Employee emp = queue.remove();
    		if(emp == null) {
    			if(!queue.isEmpty())
    				queue.add(null);
    			level++;
    		}
    		else {
    			List<Employee> subs = managerMap.get(emp.getEmployeeId());
    			if(subs != null) {
        			for(Employee e : subs) {
        				if(e.getEmployeeId() == empId) {
        					return level;
        				}
        				queue.add(e);
        			}
    			}
    		}
    	}
    	return -1;
    }
}
