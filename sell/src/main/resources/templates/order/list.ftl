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
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr style="text-align: center">
                            <th>订单ID</th>
                            <th>买家姓名</th>
                            <th>买家地址</th>
                            <th>买家电话</th>
                            <th>买家openId</th>
                            <th>下单时间</th>
                            <th>订单金额</th>
                            <th>支付状态</th>
                            <th>订单状态</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerOpenid}</td>
                                <td>${orderDTO.createTime}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                <#if orderDTO.getOrderStatusEnum().code==0>
                                    <td><a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a></td>
                                <#else>
                                    <td>取消</td>
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
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${pageSize}">上一页</a></li>
                        </#if>

                        <#list 1..orderDTOPage.totalPages as pageIndex>
                            <#if pageIndex==currentPage>
                                <li class="disabled"><a href="#">${pageIndex}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${pageIndex}&size=${pageSize}">${pageIndex}</a></li>
                            </#if>

                        </#list>
                        <#if currentPage == orderDTOPage.totalPages>
                            <li class="disabled"><a>下一页</a></li>
                        <#else >
                            <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${pageSize}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>


</body>
</html>

<#--
<#list orderDTOPage.content as orderDTO>
    ${orderDTO.}

</#list>-->
