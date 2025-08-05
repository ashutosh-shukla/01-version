<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>View KYC Documents</title>
</head>
<body>
    <h2>Customer KYC Document View</h2>

    <c:if test="${not empty document}">
        <p><strong>Customer ID:</strong> ${document.customerId}</p>
        <p><strong>Aadhar Number:</strong> ${document.aadharNumber}</p>
        <p><strong>PAN Number:</strong> ${document.panNumber}</p>
        <p><strong>Status:</strong> ${document.status}</p>

        <h3>Uploaded Documents</h3>

        <p><b>Aadhar Front:</b><br>
        <img src="http://localhost:6061/kyc-docs/view/${document.aadharFront}" width="300"/></p>

        <p><b>Aadhar Back:</b><br>
        <img src="http://localhost:6061/kyc-docs/view/${document.aadharBack}" width="300"/></p>

        <p><b>PAN Front:</b><br>
        <img src="http://localhost:6061/kyc-docs/view/${document.panFront}" width="300"/></p>

        <p><b>PAN Back:</b><br>
        <img src="http://localhost:6061/kyc-docs/view/${document.panBack}" width="300"/></p>

        <p><b>Photograph:</b><br>
        <img src="http://localhost:6061/kyc-docs/view/${document.photograph}" width="300"/></p>
    </c:if>

    <br><br>
    <a href="/kyc/admin">Back to Admin Panel</a>
</body>
</html>
