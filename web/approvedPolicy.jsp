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
       
            <h2> :Approved Policy List:</h2>
            <table  class="table table-striped border" >
                <thead>
                    <tr class="table-striped table-primary">
                        <th>Claim Id</th>
                        <th>Claim Status</th>
                        <th>Driver Name</th>
                        <th>Email Address</th>
                        <th>Time of Apply</th>

                        <th>
                            Action
                        </th>
                        
                            
                        
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach items="${ApprovedPolicyList}" var="claim">
                        <tr data-index="0">
                            <td><c:out value="${claim.claimId}"> </c:out></td>
                            <td><c:out value="${claim.claimStatus}"> </c:out>
                            <td><c:out value="${claim.driverName}"> </c:out></td>
                            <td><c:out value="${claim.emailAddress}"> </c:out></td>
                            <td><c:out value="${claim.date}"> </c:out></td>

                            
                            
                                <td> 
                                    <a href=IssuePolicy?claimId=${claim.claimId}>
                                        Issue
                                    </a>  
                                                       
                                </td>
                           
                            

                        </tr>
                    </c:forEach>
                </tbody>

            </table>
       



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
