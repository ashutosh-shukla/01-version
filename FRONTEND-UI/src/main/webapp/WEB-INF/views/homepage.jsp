<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Application Submitted Successfully</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f8fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 400px;
            margin: 80px auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 32px;
            text-align: center;
        }
        .success {
            color: #28a745;
            font-size: 2em;
            margin-bottom: 16px;
        }
        .btn {
            display: inline-block;
            margin-top: 24px;
            padding: 10px 24px;
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 1em;
            cursor: pointer;
        }
        .btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success">&#10003;</div>
        <h2>Application Submitted Successfully!</h2>
        <p>The Customer and Documents has been added to the system.</p>
        
        <a href="/kyc/view-application?documentId=${documentId}" class="btn">
        
        View Application</a>

    </div>
</body>
</html>

