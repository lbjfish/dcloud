<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <style type="text/css">
        *{padding:0;margin:0;}
        .container{background:#10aeff;width:100%;height:260px;font-family:MicrosoftYaHei;}
        .container .alertBox{background:#ffffff;box-shadow:0px 2px 10px 0px rgba(0,0,0,0.14);border-radius:4px;width:550px;height:220px;margin:auto;top:0;bottom: 0;left: 0;right: 0;position:absolute;overflow: hidden;}
        .container .alertBox .title{width: 100%;height:55px;background:#10aeff;line-height: 55px;font-size:20px;color:#ffffff;text-align: center;}
        .container .alertBox .content{font-size:14px;color:#333333;text-align: center;margin:37px 0 48px 0;}
        .container .alertBox .tip{color:#10aeff }
        .container .btnBox{text-align: center;}
        .container .btnBox .btn{background:#e7e7e7;border-radius:2px;width:60px;height:24px;border: none;font-size:12px;color:#666666;cursor: pointer;}
        .container .btnBox .warrant{background:#10aeff;color: #ffffff;margin-left: 30px;}
        #denyForm,#confirmationForm{display: inline-block;}
    </style>
</head>
<body>
<div class="container">
    <div class="alertBox">
        <div class="title">请确认</div>
        <div class="content">
            将授权 ${authorizationRequest.clientId} at "${authorizationRequest.redirectUri}" 访问您
            的权限
        </div>
        <div class="btnBox">
            <form id="denyForm" name="confirmationForm"
                  action="../oauth/authorize" method="post">
                <input name="user_oauth_approval" value="false" type="hidden"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn" type="submit">取消</button>
            </form>
            <form id="confirmationForm" name="confirmationForm"
                  action="../oauth/authorize" method="post">
                <input name="user_oauth_approval" value="true" type="hidden"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn warrant" type="submit">授权</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
