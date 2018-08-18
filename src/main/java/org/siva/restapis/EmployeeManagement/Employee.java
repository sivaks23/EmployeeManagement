package org.siva.restapis.EmployeeManagement;

import java.io.Serializable;

public class Employee implements Serializable{
	private long employeeId;
	private long managerId;
	private long salary;
	private String firstName;
	private String secondName;
	private String cityName;
	
	//Needed for converting Java object to JSON object
	public Employee() {}
	public long getEmployeeId() {
		return employeeId;
	}
	public long getManagerId() {
		return managerId;
	}
	public long getSalary() {
		return salary;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public String getCityName() {
		return cityName;
	}
	public Employee(long employeeId, String cityName, long salary, String firstName, String secondName, long managerId) {
		this.employeeId = employeeId;
		this.cityName = cityName;
		this.salary = salary;
		this.firstName = firstName;
		this.secondName = secondName;
		this.managerId = managerId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Override
	public String toString() {
		return "" + employeeId;
	}
	
	
}
