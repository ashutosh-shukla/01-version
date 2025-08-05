<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View KYC Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2>KYC Document Details</h2>
    <table class="table table-bordered">
        <tr><th>ID</th><td>${doc.id}</td></tr>
        <tr><th>Customer Name</th><td>${doc.fullName}</td></tr>
        <tr><th>Email</th><td>${doc.email}</td></tr>
        <tr><th>Phone</th><td>${doc.phoneNumber}</td></tr>
        <tr><th>Status</th><td>${doc.status}</td></tr>
        <tr><th>Created At</th><td>${doc.createdAt}</td></tr>
    </table>
    
    <a href="/kyc/admin/dashboard" class="btn btn-secondary">Back to Dashboard</a>
</div>
</body>
</html>
