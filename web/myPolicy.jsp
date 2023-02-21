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
        <c:if test="${PolicyBought!=null}">
            <h2> :My Policy List:</h2>
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
                        <c:if test="${PolicyApplied!=null}">
                            <th> 
                                Status                 
                            </th>
                        </c:if>
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

                            <c:if test="${PolicyApplied!=null && PolicyApplied.length()>0}">
                                <td> 
                                    <h6>Already applied Please Check Status</h6>                    
                                </td>
                            </c:if>
                            <c:if test="${PolicyApplied==null}">
                                <td> 
                                    <a href=ApplyPolicy?emailAddress=${user.emailAddress}>
                                        Apply
                                    </a>                     
                                </td>
                            </c:if>
                            <c:if test="${PolicyApplied!=null && PolicyApplied.length()>0}">
                                <td> 
                                   ${PolicyApplied}            
                                </td>
                            </c:if>

                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </c:if>
        <c:if test="${PolicyBought==null}">
            <h1>No Policy In Your List</h1>
        </c:if>



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
