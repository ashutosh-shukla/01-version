<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KYC Upload Success</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-success text-center fs-5">
        Done! Your KYC documents were uploaded successfully.<br>
        Please wait for Admin approval for your account creation.
    </div>

   <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">
                            <a href="${pageContext.request.contextPath}/customer/dashboard/${customerId}">Back to Customer Dashboard</a>
                            </button>
                            
                        </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
