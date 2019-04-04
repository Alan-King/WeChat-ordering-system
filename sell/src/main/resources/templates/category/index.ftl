<html lang=zh-CN>
<#include "../common/header.ftl" >

<body>
<div id="wrapper" class="toggled">
    <#--边栏 ，sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容 content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">

                <div class="col-md-12 column">
                    <form class="form-horizontal" role="form" method="post"
                          action="/sell/seller/category/save">

                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">类目名字</label>
                            <input name="categoryName" type="text" value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">	type</label>
                            <input name="categoryType" type="number" value="${(category.categoryType)!''}"/>
                        </div>

                        <input hidden name="categoryId" value="${(category.categoryId) !''}"/>
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