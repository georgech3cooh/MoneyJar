<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- tag lib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transaction History</title>
<%@ include file="head.jspf"%>
</head>
<body>
    <div id="content">
        <%@ include file="navigation.jspf"%>
        <h3>Transactions for period: ${from} to ${to}</h3>

        <div id="transaction-history">
            <%@ include file="date-picker.jspf"%>
            <!-- Display all transactions here -->
            <table>
                <tr>
                    <th class="date-col">Date</th>
                    <th class="desc-col">Description</th>
                    <th class="amnt-col">Amount</th>
                </tr>
                <c:forEach var="transaction" items="${transactions}">
                    <tr id="transaction-row-${transaction.getId()}">
                        <td class="date-col">${transaction.getDate()}</td>
                        <td class="desc-col">${transaction.getDescription()}</td>
                        <td class="amnt-col">${transaction.getAmount()}</td>
                        <td class="edit-col">
                            <input type="button" 
                                   id="${transaction.getId()}" 
                                   value="Edit">
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>
</body>
</html>