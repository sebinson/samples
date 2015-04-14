<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <form class="edit-form" id="editForm">
        <table class="form-table" id="formTable">
            <input type="hidden" id="operId" name="operId"/>               
            <tr>
                <td><label>菜单标识：</label></td>
                <td><input class="easyui-textbox" id="menuId" name="menuId" value="${data.menuId}"/></td>
            </tr>
            <tr>
                <td><label>操作码：</label></td>
                <td><input class="easyui-textbox" id="operCode" name="operCode" value="${data.operCode}"/></td>
            </tr>
            <tr>
                <td><label>操作名称：</label></td>
                <td><input class="easyui-textbox" id="operName" name="operName" value="${data.operName}"/></td>
            </tr>
            <tr>
                <td><label>操作类型：</label></td>
                <td><input class="easyui-textbox" id="operType" name="operType" value="${data.operType}"/></td>
            </tr>
            <tr>
                <td><label>操作URI：</label></td>
                <td><input class="easyui-textbox" id="operActions" name="operActions" value="${data.operActions}"/></td>
            </tr>
        </table>
    </form>
</body>
</html>