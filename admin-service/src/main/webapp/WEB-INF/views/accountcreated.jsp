<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Account Created</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5 text-center">
    <h2 class="text-success">Account Successfully Created</h2>
    <p>Account ID: <strong>${account.accountId}</strong></p>
    <p>Customer: <strong>${account.customerId}</strong></p>
    <p>Type: <strong>${account.accountType}</strong></p>
    <p>Status: <strong>${account.status}</strong></p>
    <a href="/kyc/admin/dashboard" class="btn btn-primary mt-3">Return to Dashboard</a>
</div>
</body>
</html>
