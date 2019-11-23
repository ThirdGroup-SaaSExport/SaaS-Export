<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>EXPORT</title>
    <link rel="stylesheet" href="./plugins/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/apply.css">
    <script src="plugins/jquery.min.js" type="text/javascript"></script>
    <script src="plugins/bootstrap.min.js" type="text/javascript"></script>
    <script src="plugins/messages_zh.js" type="text/javascript"></script>
    <script src="plugins/jquery.validate.min.js" type="text/javascript"></script>

    <script>
        function apply() {
            $.ajax({
                url:"/apply.do",
                data:$("#signupForm").serialize(),
                type:"POST",
                success:function(data){
                    if(data=='1'){
                        location.href="commit.jsp";
                    }else{
                        alert("提交用户信息失败");
                    }
                }
            });
        }
    </script>
</head>



<body>
    <!-- head-nav -->
    <header id="header-wrap">
        <div class="navbar navbar-default menu-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                        aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="./images/logo.png" alt="">
                    </a>
                </div>

                <div class="collapse navbar-collapse head-type" id="bs-example-navbar-collapse-1">
                    <a href="" class="button apply-btn pull-right visible-lg">免费申请</a>
                    <a href="#" class="button login-btn pull-right visible-lg">登录</a>
                    <nav>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active">
                                <a href="#">首页
                                    <span class="sr-only">(current)</span>
                                </a>
                            </li>
                            <li>
                                <a href="#">产品</a>
                            </li>
                            <li>
                                <a href="#">定价</a>
                            </li>
                            <li>
                                <a href="#">服务支持</a>
                            </li>
                            <li>
                                <a href="#">联系我们</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </header>



    <!-- apply-form -->
    <div class="apply-form">
        <div class="container">
            <div class="form-box">
                <div class="title">
                    <h4>企业申请</h4>
                    <p>Enterprise application</p>
                </div>
                <form  class="form-horizontal" id="signupForm">
                    <div class="form-group">
                        <input type="text" class="form-control" name="name" id="name" placeholder="企业名称">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="address" id="address" placeholder="企业地址">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="representative" id="representative" placeholder="联系人">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="联系电话">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="companySize" id="companySize" placeholder="公司规模">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="industry" id="industry" placeholder="所属行业">
                    </div>
                    <div class="form-group">
                        <div id="check">
                            <span>
                                <input type="checkbox" class="input_check" id="check1" />
                                <label for="check1"></label>
                            </span>
                            <label for="check1"> 《用户服务协议》</label>
                        </div>
                    </div>
                    <div class="submit">
                        <button type="button" onclick="apply()" class="btn btn-default">同意以上协议，提交申请</button>
                    </div>

                </form>
            </div>

        </div>
    </div>
    <!-- footer -->
    <div class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="item">
                            <img src="./images/address.png" alt="">
                            <div class="item-text">
                                <h4 class="media-heading">地址</h4>
                                <p class="media-text">北京市昌平区建材城西路金燕龙写字楼</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="item">
                            <img src="./images/phone.png" alt="">
                            <div class="item-text">
                                <h4 class="media-heading">客服电话</h4>
                                <p class="media-text phonenumber">400-618-4000</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="item">
                            <img src="./images/email.png" alt="">
                            <div class="item-text">
                                <h4 class="media-heading">E-mail</h4>
                                <p class="media-text">business@online.com</p>
                            </div>
                        </div>
                    </div>
    
                    <div class="col-md-3 col-sm-6 col-xs-12">
                        <div class="item">
                            <img src="./images/wechat.png" alt="">
                            <div class="item-text">
                                <h4 class="media-heading">微信公众号</h4>
                                <img src="./images/weixin.png" alt="">
                            </div>
                        </div>
                    </div>
    
                </div>
    
                <p class="right">Copyright @ 2019 传智播客教育集团 京ICP备08015045 All Rights Reserved</p>
            </div>
        </div>
    <script type="text/javascript" src="./plugins/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="./plugins/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>

</html>