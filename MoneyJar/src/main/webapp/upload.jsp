<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Statement</title>
        <%@ include file="head.jspf" %>
</head>
<body>
<div class="content">

        <%@ include file="navigation.jspf" %>
        
        <div class="main">
        <h2>Please select a file to upload.</h2>
        
            <form action="upload" enctype="multipart/form-data" method="post" >
                <p><input type="file" name="file" size="50"></p>
                <p><input type="Submit" value="Upload"></p>
            </form>
        </div> <!-- main -->
</div> <!-- content -->
</body>
</html>