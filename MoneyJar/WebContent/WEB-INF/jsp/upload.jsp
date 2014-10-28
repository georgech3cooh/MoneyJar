<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

        <form action="upload" enctype="multipart/form-data" method="post" >
        <p>Please select a file to upload.</p>
        <p>
                <input type="file" name="file" size="50">
                <input type="text" name="name">
       	</p>
        <p><input type="Submit" value="Upload"></p>
        </form>



	</body>
</html>