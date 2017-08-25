<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>index</title>
        <script src="jquery-1.9.0.min.js" type="text/javascript"></script>
        <script type="text/javascript">
	function userinfo(){
           var referrer = document.referrer;
           console.log("referrer"+referrer);
           
            var arr = window.location.href.split('=');
            var arr1 = arr[0].split('/');
           // alert(arr1);
            var url1 = arr1[0]+"/"+arr1[1]+"/"+arr1[2]+"/"+arr1[3]+"/index.jsp?username="+ arr[1]+"="+arr[2]+"="+arr[3];
          //  alert("username:"+(arr[1].split('&'))[0]);
          //   alert("url:"+url1);
          //   alert("jwt Token:"+arr[3]);
         //    $("#data").html("username:"+(arr[1].split('&'))[0]+"------"+"url:"+url1+"-----"+"jwt Token:"+arr[3]);
             $("#user").html((arr[1].split('&'))[0]);
             $("#data").html("jwt Token:"+arr[3]);
	}
        
        
	
	  $(document).ready(function () {
              // we call the functionv +
              console.log("in document ready function");
              var referrer = document.referrer;
              console.log("referrer" + referrer);
              userinfo();
              $("#btn1").click(function(){ showEmplDetails();});
             
          });
          
          function showEmplDetails() {

              var arr = window.location.href.split('=');
              var username = (arr[1].split('&'))[0];
              displayname = username.split('.')[0] +username.split('.')[1];
            //  document.getElementById("displayname_header").innerHTML = displayname;
              var add = (arr[2].split('-'))[0];
              var addurl = add.split('//')[1];
              var host = (arr[2].split('-'))[1];
              var hosturl = addurl + "-" + host;
              var jwt = arr[3];
              var hostarr = arr[2].split('-');
              console.log(hosturl);
alert("hosturl:  "+hosturl);
              console.log(jwt);
              console.log("getemployeedetails?username=" + arr[1] + "&jwt=jwt");

              $.ajax( {
                  type : "GET", 
                  url : "getemployeedetails?username=" + username,
                  data :  {
                      token : jwt,
                      hostURL : hosturl
                  },
                  success : function (response) {
                        $("#output").html(response);
                      var json_obj = jQuery.parseJSON(JSON.stringify(response));//parse JSON
                      //var imm = "";
                        console.log("ajax response"+json_obj);
                        alert(response);
                      for (var i in json_obj) {
                          var samaple = json_obj[i]
                          console.log("Sample data in side json_obj -------> "+samaple);
                          console.log("Sample Name "+samaple['DisplayName']);
                          document.getElementById("displayname_header").innerHTML = samaple['DisplayName']
                          document.getElementById("displayname").innerHTML = samaple['DisplayName']
                          
//                          document.getElementById("ac").innerHTML = samaple['AssignmentCategory']
//                          document.getElementById("mt").innerHTML = samaple['ManagerType']
//                          document.getElementById("an").innerHTML = samaple['AssignmentName']
//                          document.getElementById("hiredate").innerHTML = samaple['HireDate']
//                          document.getElementById("citizen").innerHTML = samaple['CitizenshipLegislationCode']
//                          document.getElementById("personnumber").value = samaple['PersonNumber']
//                          document.getElementById("headername").value = samaple['DisplayName'

                          //  imm +='<tr>'+'<td class="tdcss">'+samaple['PartyId']+'</td>'+'<td class="tdcss">'+samaple['ContactName']+'</td>'+'<td class="tdcss">'+samaple['EmailAddress']+'</td>'+'<td class="tdcss">'+samaple['TaxpayerIdentificationNumber']+'</td>'+'<td class="tdcss">'+samaple['FormattedAddress']+'</td>'
                      }
                      var rtem = "";
                  },
                  error : function(err){console.log(err); $("#output").html(err)},
                  contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                  dataType: "json"
              })
          }
</script>
    </head>
    <body>
    Welcome Mr. <span id="user"></span> to PaaS4SaaS Demo Portal!!
    
    <h6 id="data">HCM</h6>
    
     <div style="float:right;vertical-align:bottom;">
        <span style='font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;font-size: 12px;font-weight: bold;'
              id="displayname_header"></span>
    </div>
<div>
 <span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="displayname" style='color: #4f4f4f;font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;font-size: 12px;font-weight: normal;color:#333333'></span>
</div> 
<button id="btn1" type="button">Get My Details</button>
<br>
<h6 id="output">
</h6>

        
    </body>
</html>