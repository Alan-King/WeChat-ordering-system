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
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/product/save">

                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">名称</label>
                            <input name="productName" type="text" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">价格</label>
                            <input name="productPrice" type="number" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">库存</label>
                            <input name="productStock" type="number" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">描述</label>
                            <input name="productDescription" type="text" value="${(productInfo.productDescription)!''}"/>
                        </div>

                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">图片</label>
                            <input name="productIcon" type="text" value="${(productInfo.productIcon)!''}"/>
                            <img height="100" width="100" src="${(productInfo.productIcon)!''}"/>
                        </div>

                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">类目</label>
                            <select name="categoryType" class="pagination-control">
                                <#list productCategoryList as category>
                                    <#if productInfo ??&&productInfo.categoryType==category
                                .categoryType>
                                        <option value="${category.categoryType}" selected>
                                    <#else >
                                        <option value="${category.categoryType}">
                                    </#if>
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden name="productId" value="${(productInfo.productId) !''}"/>
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