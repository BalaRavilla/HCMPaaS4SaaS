package survey;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getEmployeeDetails extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameterNames());
        String jwt = request.getParameter("token");
        System.out.println("jwt"+jwt);
        String host = request.getParameter("hostURL");
        System.out.println(host);
        String env = host.trim();
        String username= request.getParameter("username");
        username=  username.toUpperCase();
        String hostname = request.getServerName();
        String[] jcsinstance=hostname.split("-");
       // String jcsdb = jcsinstance[0]+"db";
       String jcsdb = "gsedbsource";
        System.out.println("jcsdbinstance------"+jcsinstance);
        EmployeeDetails employeeDetails = new EmployeeDetails();
        String fresponse= employeeDetails.fetchDetails(username,jcsdb,jwt,env);
       System.out.println("fresponse------>"+fresponse);
       if(fresponse.length()==0){
           fresponse="No Data found in HCM for this user ";
       }
       // fresponse="{data : "+fresponse+"}";
       out.write(fresponse);
        out.close();
    }
}
