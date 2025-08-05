<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bank Management System - Home</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            padding: 40px;
            text-align: center;
            max-width: 500px;
            width: 90%;
            backdrop-filter: blur(10px);
        }
        
        .logo {
            font-size: 2.5em;
            color: #333;
            margin-bottom: 10px;
            font-weight: bold;
        }
        
        .subtitle {
            color: #666;
            margin-bottom: 30px;
            font-size: 1.1em;
        }
        
        .welcome-text {
            color: #555;
            margin-bottom: 40px;
            line-height: 1.6;
        }
        
        .btn {
            display: inline-block;
            padding: 15px 30px;
            margin: 10px;
            border: none;
            border-radius: 50px;
            text-decoration: none;
            font-size: 1.1em;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 200px;
        }
        
        .btn-primary {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }
        
        .btn-secondary {
            background: linear-gradient(45deg, #f093fb, #f5576c);
            color: white;
        }
        
        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(240, 147, 251, 0.3);
        }
        
        .btn-outline {
            background: transparent;
            color: #667eea;
            border: 2px solid #667eea;
        }
        
        .btn-outline:hover {
            background: #667eea;
            color: white;
            transform: translateY(-2px);
        }
        
        .features {
            margin-top: 40px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 20px;
        }
        
        .feature {
            padding: 20px;
            background: rgba(102, 126, 234, 0.1);
            border-radius: 15px;
            border: 1px solid rgba(102, 126, 234, 0.2);
        }
        
        .feature h3 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 1.1em;
        }
        
        .feature p {
            color: #666;
            font-size: 0.9em;
            line-height: 1.4;
        }
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 10px;
            font-weight: 500;
        }
        
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        @media (max-width: 600px) {
            .container {
                padding: 30px 20px;
            }
            
            .btn {
                display: block;
                margin: 10px auto;
                min-width: 250px;
            }
            
            .features {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">🏦</div>
        <h1>Bank Management System</h1>
        <p class="subtitle">Secure • Reliable • Efficient</p>
        
        <p class="welcome-text">
            Welcome to our comprehensive banking platform. Manage your accounts, 
            perform transactions, and access banking services with ease and security.
        </p>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <div class="action-buttons">
            <a href="/auth/register-page" class="btn btn-primary">
                📝 New Customer Registration
            </a>
            <a href="/auth/login-page" class="btn btn-secondary">
                🔐 Customer Login
            </a>
            <a href="/kyc/form" class="btn btn-outline">
                📋 KYC Upload
            </a>
        </div>
        
        <div class="features">
            <div class="feature">
                <h3>🔒 Secure Banking</h3>
                <p>Advanced security measures to protect your financial data</p>
            </div>
            <div class="feature">
                <h3>💳 Account Management</h3>
                <p>Manage your accounts and view transaction history</p>
            </div>
            <div class="feature">
                <h3>📱 24/7 Access</h3>
                <p>Access your banking services anytime, anywhere</p>
            </div>
            <div class="feature">
                <h3>🔄 Easy Transfers</h3>
                <p>Quick and secure money transfers between accounts</p>
            </div>
        </div>
    </div>
</body>
</html>

