<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    request.setAttribute("basePath", "");
    request.setAttribute("staticPath", "http://stc.sebinson.net");
%>
<script type="text/javascript">
	var basePath = '${basePath}';
</script>
<link rel="stylesheet" type="text/css" href="${staticPath}/common/js/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/common/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/common/js/easyui/themes/color.css">
<link rel="stylesheet" type="text/css" href="${staticPath}/common/css/custom.css">
<script type="text/javascript" src="${staticPath}/common/js/easyui/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/package.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/Gooagoo.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/GDataGrid.js"></script>
<script type="text/javascript" src="${staticPath}/common/js/common.js"></script>