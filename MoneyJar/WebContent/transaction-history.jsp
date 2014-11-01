<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- tag lib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transaction History</title>
	<%@ include file="head.jspf" %>
</head>
<body>

	<h2>Transactions for period: ${from} to ${to}</h2>
	<%@ include file="navigation.jspf" %>
	
	
	<%@ include file="date-picker.jspf" %>
    <div id="transaction-history">
        <!-- Display all transactions here -->
        <table id="transaction-history">
            <c:forEach var="transaction" items="${transactions}">
                <tr id="transaction-row-${transaction.getId()}">
                    <td>${transaction.getId()}</td>
                    <td>${transaction.getDate()}</td>
                    <td>${transaction.getDescription()}</td>
                    <td>${transaction.getAmount()}</td>
                    <td><input type="button" 
                               id="${transaction.getId()}"
                               value="Edit"></td>
                </tr>
            </c:forEach>

        </table>
    </div>
</body>
</html>