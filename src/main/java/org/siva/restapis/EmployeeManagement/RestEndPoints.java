package org.siva.restapis.EmployeeManagement;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public class RestEndPoints {
	private static final EmployeeServices empService = EmployeeServices.getService();
	
    @PUT
    @Path("{empId}/{mgrId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee updateEmployeesManager(@PathParam("empId") long empId, @PathParam("mgrId") long mgrId){
        return empService.updateManager(empId, mgrId);
    }
    
    @GET
    @Path("/empWithMaxSub")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee maxSubOrdinates(){
        return empService.employeeWithMaxSubOrdinates();
    }
    
    @GET
    @Path("salary/{empId}")
    @Produces(MediaType.TEXT_PLAIN)
    public long totalSalOfSubOrdinates(@PathParam("empId") long empId){
        return empService.totalSalary(empId);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String orgStructure(){
        return empService.orgStructure();
    }
    
    @GET
    @Path("isSameLevel/{empId1}/{empId2}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean isSameLevel(@PathParam("empId1") long empId1, @PathParam("empId2") long empId2) {
    	return empService.isLevelSame(empId1, empId2);
    }

}