<html lang=zh-CN>

<#include "../common/header.ftl" >

<body>
<div id ="wrapper" class="toggled">
    <#--边栏 ，sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容 content-->
    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed html-editor-align-center">
                        <thead style="align-content: center">
                        <tr style="text-align: center">
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>商品图片</th>
                            <th>商品价格</th>
                            <th>商品描述</th>
                            <th>商品类目</th>
                            <th>商品库存</th>
                            <th>商品状态</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productInfoPage.content as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td><img height="100" width="100" src="${productInfo.productIcon}"/></td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productDescription}</td>
                                <td>${productInfo.categoryType}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.getProductStatusEnum().message}</td>

                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>

                                <#if productInfo.getProductStatusEnum().code==0>
                                    <td><a href="/sell/seller/product/off_sale?productId=${productInfo
                                        .productId}">下架</a></td>
                                <#else>
                                    <td><a href="/sell/seller/product/on_sale?productId=${productInfo
                                        .productId}">上架</a></td>
                                </#if>
                            </tr>

                        </#list>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage == 1>
                            <li class="disabled"><a>上一页</a></li>
                        <#else >
                            <li><a href="/sell/seller/product/list?page=${currentPage-1}&size=${pageSize}">上一页</a></li>
                        </#if>

                        <#list 1..productInfoPage.totalPages as pageIndex>
                            <#if pageIndex==currentPage>
                                <li class="disabled"><a href="#">${pageIndex}</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${pageIndex}&size=${pageSize}">${pageIndex}</a></li>
                            </#if>

                        </#list>
                        <#if currentPage == productInfoPage.totalPages>
                            <li class="disabled"><a>下一页</a></li>
                        <#else >
                            <li><a href="/sell/seller/product/list?page=${currentPage+1}&size=${pageSize}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>


</body>
</html>
