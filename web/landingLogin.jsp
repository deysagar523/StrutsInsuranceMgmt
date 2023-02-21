<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
        <title>Sign in - Employee Management</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/signin.css" rel="stylesheet">

        <style>
            .about_row_div{

                background-color: white;

                margin-bottom: 1rem;

                border:1px solid black;
                padding-top: 1rem;
                padding-right: 1rem;
                padding-bottom: 2rem;
                text-align: center;

            }
            .row_div
            {
                margin: auto;
            }
            .about_pic{
                width: 25%;
                display: block;
                margin-left: auto;
                margin-right: auto;
            }
        </style>
    </head>

    <body class="text-center">


        <main class="row_div">

            <div class="row">
                <div class="col-lg-4 col-md-6 about_row_div">
                    <img src="images/user.png" alt="" class="about_pic">
                    <h1 class="h3 mb-3 fw-normal">Please sign in for user</h1>
                    <a href="login.jsp" class="btn btn-primary">Please sign in for user</a>
                    <h5>New User?</h5>
                    <a href="PreSignUp">
                        <button type="button" class="btn btn-warning">Sign-up</button>
                    </a>
                    <p class="mt-5 mb-3 text-muted">&copy; 2017?2022</p>
                </div>
                <div class="col-lg-4 col-md-6 about_row_div">
                    <img src="images/admin.jpg" alt="" class="about_pic">
                    <h1 class="h3 mb-3 fw-normal">Please sign in for Insurance Officer</h1>
                    <a href="loginInsuranceOfficer.jsp" class="btn btn-primary">Please sign in for Insurance Officer</a>
                    <p class="mt-5 mb-3 text-muted">&copy; 2017?2022</p>
                </div>
                <div class="col-lg-4 col-md-6 about_row_div">

                    <img src="images/underwriter.png" alt="" class="about_pic">
                    <h1 class="h3 mb-3 fw-normal"-3 fw-normal">Please sign in for underwriter</h1>
                    <a href="loginUnderwriter.jsp" class="btn btn-primary">Please sign in for underwriter</a>
                    <p class="mt-5 mb-3 text-muted">&copy; 2017?2022</p>
                </div>
            </div>








        </main>



    </body>
</html>
