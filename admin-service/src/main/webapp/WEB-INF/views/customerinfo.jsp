<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2>Customer Profile</h2>
    <table class="table table-striped table-bordered">
        <tr><th>ID</th><td>${customer.customerId}</td></tr>
        <tr><th>Full Name</th><td>${customer.fullName}</td></tr>
        <tr><th>Email</th><td>${customer.email}</td></tr>
        <tr><th>Phone</th><td>${customer.phone}</td></tr>
        <tr><th>Address</th><td>${customer.address}</td></tr>
        <tr><th>KYC Status</th><td>${customer.kycStatus}</td></tr>
    </table>
    <a href="/kyc/admin/dashboard" class="btn btn-secondary">Back to Dashboard</a>
</div>
</body>
</html>
