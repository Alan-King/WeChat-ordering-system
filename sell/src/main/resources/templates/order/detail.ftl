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
                <#--订单主要信息-->
                <div class="col-md-4 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <th>买家姓名</th>
                        <th>买家地址</th>
                        <th>买家电话</th>
                        <th>订单金额</th>
                        <th>支付状态</th>
                        <th>订单状态</th>
                        </thead>
                        <tbody>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getPayStatusEnum().message}</td>
                        <td>${orderDTO.getOrderStatusEnum().message}</td>
                        </tbody>
                    </table>
                </div>

                <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr style="text-align: center">
                            <th>订单详情ID</th>
                            <th>商品ID</th>
                            <th>商品名字</th>
                            <th>商品价格</th>
                            <th>商品数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.getOrderDetailList() as orderDetail>
                            <tr>
                                <td>${orderDetail.detailId}</td>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>${orderDetail.productPrice}</td>
                                <td>${orderDetail.productQuantity}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--操作-->
                <div class="col-md-6 column">
                    <#if orderDTO.getOrderStatusEnum().getCode()==0>
                        <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}"
                           type="button" class="btn btn-default btn-success ">完结订单</a>

                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}"
                           type="button" class="btn btn-default btn-danger">取消订单</a>
                    </#if>
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
