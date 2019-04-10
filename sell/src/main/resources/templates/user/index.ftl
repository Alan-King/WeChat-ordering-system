<html lang=zh-CN>
<#include "../common/header.ftl" >

<body>
<div id="wrapper" class="toggled">
    <#--主要内容 content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">

                <div class="col-md-12 column">
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/user/login">

                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">用户名</label>
                            <input name="username" type="text"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                            <input name="password" type="password" />
                        </div>
                       <#if returnUrl ??>
                           <input hidden name="returnUrl" value="${(returnUrl)}"/>
                       </#if>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>