<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <script src="${request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>
    <script src="${request.contextPath}/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${request.contextPath}/webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <style>
        .col-center-block {
            float: none;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row myCenter">
        <div class="col-xs-6 col-md-4 col-center-block">
            <form action="${request.contextPath}/help/test">
                <div class="form-group">
                    <label for="code" class="sr-only">流水号:</label>
                    <br/>
                    <input type="text" class="form-control" placeholder="流水号" required autofocus id="code" name="code"
                           value="${code!}">
                    <button type="submit" class="btn btn-lg btn-primary btn-block">提交</button>
                </div>
            </form>
            <div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>状态</th>
                        <th>信息</th>
                        <th>地址</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list result as data>
                    <tr>
                        <td>${data.State!}</td>
                        <td>${data.Message!}</td>
                        <#if (data.Url)??>
                            <td><a href="${data.Url}">点击一哈</a></td>
                        <#else>
                            <td>尚无数据</td>
                        </#if>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
