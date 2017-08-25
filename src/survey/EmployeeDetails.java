package survey;


import com.sun.jersey.core.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class EmployeeDetails {
    public EmployeeDetails() {
        super();
    }

    public static void main(String[] args) {
//        EmployeeDetails employeeDetails = new EmployeeDetails();
//        employeeDetails.fetchDetails("ROBERT.JACKMAN","javatrial1185db","","fap0143");
        //employeeDetails.getAssignmentDetails("541");
        EmployeeDetails emp=new EmployeeDetails();
       System.out.println("output"+emp.fetchDetails("HCM_IMPL","gsedbsource","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IllRTTFaMURLaTR2bmc0ZVJoZU5fbUIxOWVwYyIsImtpZCI6InRydXN0c2VydmljZSJ9.eyJleHAiOjE1MDM0ODIwMDUsInN1YiI6IkhDTV9JTVBMIiwiaXNzIjoid3d3Lm9yYWNsZS5jb20iLCJwcm4iOiJIQ01fSU1QTCIsImlhdCI6MTUwMzQ2NzYwNX0.J0yce5HY4vQvouCcMVzAqCiErc7Pp2VUpVx_T138ZRGVJ06fvX7N2JLKd76JbA2RS7q0I3TOdDmHT44bx6bWbU_nm-rBSReSRCH_MBomWRvclCzKJZNJQklulBEi9JypI5xzPHeyXyO4eLyq-jxBUM_fWSIYc1jN123yU0oaygWrfL9gQOpO4kdt7PAFb1GJgPVofS5KzA83uRMA7ltGmFdQK9EvWqLn65qQuhDdNd2BMkVCYj0ZrD3ykwB7SZJrH-O6_cEARYsh2ZhVIZWqNOUc-X-XaCikUrNqrlj9AlHasrKgpzjrqhEzVRQ1DMm55IrVhOPVvsMENtRR79e6iA","efgv-dev1.fs.em3.oraclecloud.com"));
    }
    
    public String fetchDetails(String username,String jcsdb,String jwt,String host){
        String finalresponse="";
        String jwttoken = jwt.trim();
        System.out.println("jwttoken"+jwttoken);
        HttpURLConnection connection = null;

        Map<String, ArrayList> empDetailsmap = new HashMap<String, ArrayList>();  
        
                     String serverUrl ="https://efgv-dev1.hcm.em3.oraclecloud.com:443/hcmCoreApi/resources/11.1.10/emps/?q=UserName="+username+"&onlyData=true&fields=DisplayName,HireDate,PersonNumber,CitizenshipLegislationCode";
                //   String encodedString = new String(Base64.encode(connectionCredentials.getBytes()));
                    String jsonResponse="";
                    try {
                        URL url = new URL(serverUrl);
                       // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("www-proxy.us.oracle.com", 80));
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type", "application/json; Charset=UTF-8");
                    //    connection.addRequestProperty("Authorization", "Basic " + encodedString);
                        connection.addRequestProperty("Authorization", "Bearer " + jwttoken);
                        
                        int responseCode = connection.getResponseCode();

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        //print result
                        System.out.println("service 1 output ---> "+response.toString());
                         jsonResponse=response.toString();
                        JSONObject obj = new JSONObject(response.toString());
                        JSONArray arr = obj.getJSONArray("items");
                        
                        for (int i = 0; i < arr.length(); i++){
                            String DisplayName = arr.getJSONObject(i).getString("DisplayName");
                            String HireDate = arr.getJSONObject(i).getString("HireDate");
                            String CitizenshipLegislationCode;
                            System.out.println("code" +arr.getJSONObject(i));
                            if(arr.getJSONObject(i).isNull("CitizenshipLegislationCode")){
                                System.out.println("in if clause"+arr.getJSONObject(i).isNull("CitizenshipLegislationCode"));
                                 CitizenshipLegislationCode = "null";
                            }else{
                            
                             CitizenshipLegislationCode = arr.getJSONObject(i).getString("CitizenshipLegislationCode");
                            } 
                            String PersonNumber = arr.getJSONObject(i).getString("PersonNumber");
                            
                            ArrayList al = new ArrayList();
                            al.add(DisplayName);
                            al.add(HireDate);
                            al.add(CitizenshipLegislationCode);
                            
                            empDetailsmap.put(PersonNumber, al);
                         finalresponse=    getAssignmentDetails(empDetailsmap,jwttoken,host);
                            System.out.println("------finalresponse---------"+finalresponse);
                        }
    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    if(finalresponse.length()>1){}
                    else{finalresponse=jsonResponse;}
                    
                    return finalresponse;
}
    
    
    
    
    
    public String getAssignmentDetails(Map<String, ArrayList> empDetailsmap,String jwttoken,String host){
                    String empDetails="";
                    HttpURLConnection connection = null;
                    Iterator<Map.Entry<String, ArrayList>> entries = empDetailsmap.entrySet().iterator();
                    Map<String, ArrayList> finalempdetailsmap = new HashMap<String, ArrayList>(); 
                    while (entries.hasNext()) {
                        try{
                            Map.Entry<String, ArrayList> entry = entries.next();
                           String serverUrl ="https://efgv-dev1.hcm.em3.oraclecloud.com:443/hcmCoreApi/resources/latest/emps?q=PersonNumber="+entry.getKey()+"&expand=assignments";
                        
                            try {
                                URL url = new URL(serverUrl);
                                
                               // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("www-proxy.idc.oracle.com", 80));
                                connection = (HttpURLConnection) url.openConnection();
                                connection.setDoOutput(true);
                                connection.setDoInput(true);
                                connection.setRequestMethod("GET");
                                connection.setRequestProperty("Content-Type", "application/json; Charset=UTF-8");
                              //  connection.addRequestProperty("Authorization", "Basic " + encodedString);
                               connection.addRequestProperty("Authorization", "Bearer " + jwttoken);
                                
                                int responseCode = connection.getResponseCode();

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                
                                in.close();

                                //print result
                                System.out.println(response.toString());
                            ArrayList tmparrList = entry.getValue();
                            
                            ArrayList l = new ArrayList();
                            for(int i=0;i<tmparrList.size();i++){
                                l.add(tmparrList.get(i));
                            }
                            JSONObject obj = new JSONObject(response.toString());
                            JSONArray arr = obj.getJSONArray("items");
                            System.out.println("---length--"+arr.length());
                            JSONArray assignmets = new JSONArray();
                            for (int i = 0; i < arr.length(); i++){
                                String displayName=arr.getJSONObject(i).getString("DisplayName");
                            assignmets=    arr.getJSONObject(i).getJSONArray("assignments");
                                System.out.println("----size------"+assignmets.length());
                            }
                            
                            for (int i = 0; i < assignmets.length(); i++){
                                String assignmentname=assignmets.getJSONObject(i).getString("AssignmentName");
                                String AssignmentCategory=assignmets.getJSONObject(i).getString("AssignmentCategory");
                                String ManagerType=assignmets.getJSONObject(i).getString("ManagerType");
                                Long SalaryAmount=assignmets.getJSONObject(i).getLong("SalaryAmount");
                              String SA=   String.valueOf(SalaryAmount);
                                l.add(assignmentname);
                                l.add(AssignmentCategory);
                                l.add(ManagerType);
                                l.add(SA);
                                
                                
                                
                            }
                                
                                finalempdetailsmap.put(entry.getKey(),l);
                         empDetails=    prepareEmpDetailsJSON(finalempdetailsmap);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }                    catch(Exception e){
                        e.printStackTrace();
                    }
                       /* JSONObject obj = new JSONObject(response.toString());
                        JSONArray arr = obj.getJSONArray("items");
                        System.out.println("---length--"+arr.length());
                        JSONArray assignmets = new JSONArray();
                        for (int i = 0; i < arr.length(); i++){
                            String displayName=arr.getJSONObject(i).getString("DisplayName");
                        assignmets=    arr.getJSONObject(i).getJSONArray("assignments");
                            System.out.println("----size------"+assignmets.length());
                        }
                        
                        for (int i = 0; i < assignmets.length(); i++){
                            String assignmentname=assignmets.getJSONObject(i).getString("AssignmentName");
                            System.out.println("--------AN---------"+assignmentname);
                        }*/
    }
                    return empDetails;
                   
    }
    
    public String prepareEmpDetailsJSON(Map<String, ArrayList> empDetailsmap){
        String fresponse="";
        try{
            Iterator<Map.Entry<String, ArrayList>> entries1 = empDetailsmap.entrySet().iterator();
            JSONArray list1 = new JSONArray();

            while (entries1.hasNext()) {
                Map.Entry<String, ArrayList> entry = entries1.next();
               
                JSONObject obj = new JSONObject();
                obj.put("PersonNumber",entry.getKey());
               // String jsonarray += "{ /n \"AccountList\":[\n {\"partynumber\":\""+entry.getKey()+"\"";
                System.out.println("Key = " + entry.getKey());
                ArrayList arrlist=entry.getValue();
                    for (int i = 0; i < arrlist.size(); i++) {
                                       
                                        
                                        obj.put("DisplayName",arrlist.get(0));
                                        obj.put("HireDate",arrlist.get(1));
                                        obj.put("CitizenshipLegislationCode",arrlist.get(2));
                                        obj.put("AssignmentName",arrlist.get(3));
                                        obj.put("AssignmentCategory",arrlist.get(4));
                                        obj.put("ManagerType",arrlist.get(5));
                                        obj.put("SA",arrlist.get(6));
                                    }
                list1.put(obj);
            }  
         
             fresponse=list1.toString();
            System.out.println("----------list1-------------------------"+fresponse);
        }catch(Exception e){
            e.printStackTrace();
        }
        return fresponse;
    }
}
