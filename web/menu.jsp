<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!doctype html>
<html lang="en">
    <head>
        <title>Employee Management</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <meta name="theme-color" content="#712cf9">

        <!-- Custom styles for this template -->
        <link href="css/header.css" rel="stylesheet">
        <!--<link href="css/carousel.css" rel="stylesheet">-->
    </head>

    <header class="p-3 text-bg-dark">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <img src="images/flower-logo.jpg" width="75" height="75"></img>
                </a>

                <c:set var="user" value="${User}"/>
                <c:set var="login_check" value="${Loggedin}"/>
                <c:if test="${login_check!=null && user.getRoleId()==1}">
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

                        <li><a  style="cursor:pointer;" class="nav-link px-2 text-white" onclick="fetchContent('buyPolicy.jsp', 'contentBody')">Buy Policy</a></li>
                        <li><a style="cursor:pointer;" class="nav-link px-2 text-white" onclick="fetchContent('search.jsp', 'contentBody')">Apply policy</a></li>
                        <li><a style="cursor:pointer;" class="nav-link px-2 text-white"  onclick="fetchContent('myPolicy.jsp', 'contentBody')">My Policy list</a></li>
                        

                        <a class="nav-link px-2 text-bg-light">
                            WELCOME :
                            
                            <c:out value="${user.getFirstName()} ${user.getLastName()} " />!!!!
                        </a>
                    </c:if>
                    <c:set var="login_check_insurance_officer" value="${LoggedinInsuranceOfficer}"/>
                    <c:if test="${login_check!=null && user.getRoleId()==2}">
                        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

                            <li><a  style="cursor:pointer;" class="nav-link px-2 text-white" onclick="fetchContent('ShowApprovedPolicy', 'contentBody')">Issue Policy</a></li>



                        </c:if>
                        <c:set var="login_check_underwriter" value="${LoggedinUnderwriter}"/>
                        <c:if test="${login_check!=null && user.getRoleId()==3}">
                            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">

                                <li><a  style="cursor:pointer;" class="nav-link px-2 text-white" onclick="fetchContent('ShowAppliedPolicy', 'contentBody')">Show Applied Policies</a></li>


                                
                            </c:if>
                            <c:if test="${login_check==null && login_check_underwriter==null && login_check_insurance_officer==null}">
                                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                                    <li><a href="ConsumeDMVApi" class="nav-link px-2 text-white">Fetch DMV Info</a></li>
                                    <li><a href="ConsumeInsuranceMasterApi" class="nav-link px-2 text-white">Fetch Insurance Master</a></li>
                                   

                                </c:if>
                            </ul>

                            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
                                <input type="search" class="form-control form-control-dark text-bg-dark" placeholder="Search..." aria-label="Search">
                            </form>


                            <div class="text-end">
                                <c:choose>
                                    <c:when test="${login_check==null && login_check_underwriter==null && login_check_insurance_officer==null}">
                                        <a href="landingLogin.jsp">
                                            <button type="button" class="btn btn-outline-light me-2">Login</button>
                                        </a>
                                        <a href="PreSignUp">
                                            <button type="button" class="btn btn-warning">Sign-up</button>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="Logout">
                                            <button type="button" class="btn btn-outline-light me-2">Log Out</button>
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div id="details">

                            </div>
                            </div>
                            </div>
                            </header>