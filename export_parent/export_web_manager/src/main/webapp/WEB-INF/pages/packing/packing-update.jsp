<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            装箱管理
            <small>新增装箱单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">装箱管理</a></li>
            <li class="active">新增装箱单</li>
        </ol>
    </section>

    <!-- 内容头部 /-->
    <form id="editForm" action="${ctx}/cargo/packing/edit.do" method="post">
<%--        <input type="hidden" name="exportIds" value="${packing.exportIds}" >--%>
        <input type="hidden" name="packingListId" value="${packing.packingListId}" >
<%--        <input type="hidden" name="contractNo" value="${packing.customerContract}">--%>
    <!-- 正文区域 -->
    <section class="content">
        <div class="panel panel-default">
            <div class="panel-heading">新增装箱</div>

                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">卖方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卖方" name="seller" value="${packing.seller}"/>
                    </div>

                    <div class="col-md-2 title">买方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="买方" name="buyer" value="${packing.buyer}"/>
                    </div>

                    <div class="col-md-2 title">发票号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="发票号" name="invoiceNo" value="${packing.invoiceNo}"/>
                    </div>

                    <div class="col-md-2 title">发票日期</div>
                    <div class="col-md-4 data">
                        <input type="text" name="invoiceDate" class="form-control" placeholder="发票日期"value="${packing.invoiceDate}"/>
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data">
                        <input type="text" name="marks" class="form-control" placeholder="唛头" value="${packing.marks}"/>
                    </div>
                    <div class="col-md-2 title">描述</div>
                    <div class="col-md-4 data">
                        <input type="text" name="descriptions" class="form-control" placeholder="描述" value="${packing.descriptions}"/>
                    </div>
                </div>
        </div>

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">报运单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">

                        <tr>
                            <td><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <th class="sorting">出口号</th>
                            <th class="sorting">货物/附件</th>
                            <th class="sorting">信用证号</th>
                            <th class="sorting">收货地址</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">目的港</th>
                            <th class="sorting">运输方式</th>
                            <th class="sorting">价格条件</th>
                            <th class="sorting">状态</th>
                        </tr>



                        <c:forEach items="${exportList}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
                                <td>
                                    <input type="checkbox" name="exportIds" value="${o.id}"
                                           <c:if test="${fn:contains(exportIdStr,o.id)}">checked</c:if>
                                    />
                                </td>
                                <td><input type="hidden" name="" value="${o.id}"/>${o.id}</td>
                                <td>
                                    <input style="width: 90px" name="" value=" ${o.proNum}/${o.extNum}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.lcno}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.consignee}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.shipmentPort}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.destinationPort}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.transportMode}">
                                </td>
                                <td>
                                    <input style="width: 90px" name="" value="${o.priceCondition}">
                                </td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                                </td>
                                <input type="hidden" name="state" value="${o.state}"/>
                               <%-- <td>
                                    <input style="width: 90px" name="exportProducts[${status.index}].tax" value="${o.tax}">
                                </td>--%>
                            </tr>
                        </c:forEach>

                    </table>
                </div>

            </div>
            <!-- /.box-body -->


            <!-- /.box-footer-->
        </div>



        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="submit"  class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->
    </section>
    </form>
</div>
<!-- 内容区域 /-->
</body>

</html>