<html>
<head>
    <style type="text/css">
        *{margin:0;padding:0;}
        a{text-decoration:none}
        .container{background:#10aeff;width:100%;height:540px;font-family:MicrosoftYaHei;}
        .container .form{background:#ffffff;box-shadow:0px 2px 10px 0px rgba(0,0,0,0.14);border-radius:4px;width:550px;height:628px;margin:auto;position:absolute;top:0;bottom: 0;left: 0;right: 0;}
        .container .form .title{width: 100%;font-size:36px;color:#999999;text-align:center;margin:44px 0 50px 0;}
        .form-group{width: 359px;margin:0 auto;margin-bottom: 29px;}
        .form-group .name{display: block;font-size:14px;color:#666666;text-align:left;margin-bottom: 10px;}
        .form-group .form-control{background:#f4f5fa;border:1px solid #e5e5e5;border-radius:4px;width:359px;height:49px;display: inline-block;padding: 20px;
            box-sizing: border-box;}
        .form-group .code{width:214px;}
        .form-group .verificationCode{background:#ffe7e7;width:130px;height:50px;    float: right;line-height: 50px;text-align:center;font-family:HYLeMiaoTiJ;
            font-size:24px;color:#666666;cursor:pointer;}
        .form-group .tip{font-size:12px;color:#10aeff;margin-top: 19px;display: block;}
        .form-group .btn{background:#10aeff;border-radius:4px;width:360px;height:60px;border:none;font-size:18px;color:#ffffff;cursor:pointer;}
        .form-group input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {font-size:12px;color:#999999; }
        .form-group input:-moz-placeholder, textarea:-moz-placeholder {font-size:12px;color:#999999;}
        .form-group input::-moz-placeholder, textarea::-moz-placeholder { font-size:12px;color:#999999;}
        .form-group input:-ms-input-placeholder, textarea:-ms-input-placeholder { font-size:12px;color:#999999; }
    </style>
</head>
<body>
<div class="container">
    <form role="form" action="login" method="post" class="form">
        <div class="title">欢迎登录</div>
        <div class="form-group">
            <label for="username" class="name">请输入用户名</label>
            <input type="text" class="form-control" id="username"  name="username" value="admin" placeholder="请输入用户名"/>
        </div>
        <div class="form-group">
            <label for="password" class="name">请输入密码</label>
            <input type="password" class="form-control" id="password" name="password" value="123456" placeholder="请输入密码"/>

        </div>
        <div class="form-group">
            <!-- <label for="password" class="name">请输入验证码</label>
            <input type="password" class="form-control code" id="verificationCode" name="verificationCode" placeholder="请输入右边的验证码" />
            <span class="verificationCode">123</span> -->
            <a href="#" class="tip">忘记密码？</a>
        </div>
        <div class="form-group">
            <button type="submit" class="btn">登录</button>
        </div>
    </form>
</div>
</body>
</html>
