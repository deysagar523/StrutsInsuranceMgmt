<%-- 
    Document   : landingPage
    Created on : 22-Dec-2022, 12:00:33 PM
    Author     : Avijit Chattopadhyay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <!--        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <!--        <link rel="stylesheet" href="css/all.min.css">-->
        <!--        <link rel="stylesheet" href="css/reset-min.css">-->
        <!--        <link rel="stylesheet" href="css/algolia-min.css">
                <link rel="stylesheet" href="css/header.css">
                <link rel="stylesheet" href="css/docs.min.css">
                <link rel="stylesheet" href="css/index.css">-->
        <title>Employee Management</title>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.2.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css"/>

        <style>
            #example_wrapper{
                width: 75%;
                display: block;
                margin: auto;
            }
            h2{
                text-align: center;
            }
            .msg_style{
                width: 25%;
                text-align: center;
                margin: auto;
            }
        </style>    
    </head>
    <body>
          
            <h2> :Policy List:</h2>
       
        <table  class="table table-striped border" >
            <thead>
                <tr class="table-striped table-primary">
                    <th>Policy Id</th>
                    <th>Policy Name</th>
                    <th>Sum Assured</th>
                    <th>Time</th>
                    
                    <th>
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:set var="user" value="${User}"/>
                <c:forEach items="${PolicyList}" var="policy">
                    <tr data-index="0">
                        <td><c:out value="${policy.policyId}"> </c:out></td>
                        <td><c:out value="${policy.policyName}"> </c:out>
                        <td><c:out value="${policy.sumAssured}"> </c:out></td>
                        <td><c:out value="${policy.time}"> </c:out></td>
                       
                            <td>??
                                <a href=BuyPolicy?emailAddress=${user.emailAddress}>
                                You Have Already Bought
                            </a>???????????????????????????????????????? 
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            

        </table>
                <h1>Now back to previous Page </h1>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.1/js/dataTables.bootstrap5.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#example').DataTable();
            });
        </script>
    </body>

</html>
