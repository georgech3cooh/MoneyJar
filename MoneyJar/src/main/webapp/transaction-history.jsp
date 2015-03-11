<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- tag lib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction History</title>
        <%@ include file="head.jspf"%>
</head>
<body>
	<div class="content">

		<%@ include file="navigation.jspf"%>
		
		<div class="main">
        	<h2>Transactions for period: ${from} to ${to}</h2>
        	
		<div class="charts">
			<canvas id="categories" height="400" width="400"></canvas>
			<div id="categories-legend"></div>
		</div> <!-- charts -->

		<div class="transaction-history">



			<%@ include file="date-picker.jspf"%>
			<!-- Display all transactions here -->

			<form class="transaction-history-form" method="post" action="transactions">
			<input type="hidden" name="from" value="${from}"/>
			<input type="hidden" name="to" value="${to}" />
				<table>
					<thead>
					<tr>
						<th class="check-box"></th>
						<th class="date-col">Date</th>
						<th class="desc-col">Description</th>
						<th class="cat-col">Category</th>
						<th class="amnt-col">Amount</th>
						<th class="edit-col"></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="transaction" items="${transactions}">
						<tr id="transaction-row-${transaction.getId()}">
							<td class="check-box"><input type="checkbox"
								name="selection" value="${transaction.getId()}"></td>
							<td class="date-col">${transaction.getDate()}</td>
							<td class="desc-col">${transaction.getDescription()}</td>
							<td class="cat-col">${transaction.getCategory().getName()}</td>
							<td class="amnt-col">${transaction.getAmount()}</td>
							<td class="edit-col"><input type="button"
								id="${transaction.getId()}" value="Edit"></td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
					<tr class="bulk-action">
						<td colspan="6">
							<select class="category" name="category">
								<c:forEach var="category" items="${categories}">
									<option value="${category.getName()}">${category.getName()}</option>
								</c:forEach>
							</select> <input type="submit" value="Submit" />
						</td>
					</tr>
					</tfoot>
				</table>
			</form>
		</div> <!-- transaction-history -->
		
		</div> <!-- main -->
	</div>
</body>
</html>