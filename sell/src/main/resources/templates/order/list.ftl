<html lang=zh-CN>

<#include "../common/header.ftl" >

<body>
<div id="wrapper" class="toggled">
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
                                <li>
                                    <a href="/sell/seller/order/list?page=${pageIndex}&size=${pageSize}">${pageIndex}</a>
                                </li>
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

<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button  onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn
                btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新订单</button>
            </div>
        </div>

    </div>
</div>

<#--音乐播放-->
<audio id="notice">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg">
</audio>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>

<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var webSocket = null;
    if ('WebSocket' in window) {
        webSocket = new WebSocket('ws://wlzhfn.natapp1.cc/sell/webSocket');
    } else {
        window.alert('浏览器不支持webSocket');
    }

    webSocket.onopen = function (event) {
        console.log('建立连接');
    };

    webSocket.onclose = function (event) {
        console.log('关闭连接');
    };

    webSocket.onmessage = function (event) {
        console.log('收到消息:' + event.data);
        //弹窗提醒播放音乐
        $('#myModal').modal('show');
        document.getElementById('notice').play();
    };

    webSocket.onerror = function (event) {
        alert('webSocket发生错误！');
    };

    window.onbeforeunload = function (ev) {
        webSocket.close();
    };
</script>

</body>
</html>
