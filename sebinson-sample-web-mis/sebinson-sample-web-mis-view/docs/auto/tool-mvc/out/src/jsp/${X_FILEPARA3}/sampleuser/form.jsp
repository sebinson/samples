<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <form class="edit-form" id="editForm">
        <table class="form-table" id="formTable">
            <input type="hidden" id="userId" name="userId"/>               
            <tr>
                <td><label>用户名：</label></td>
                <td><input class="easyui-textbox" id="userName" name="userName" value="${data.userName}"/></td>
            </tr>
            <tr>
                <td><label>登入密码：</label></td>
                <td><input class="easyui-textbox" id="password" name="password" value="${data.password}"/></td>
            </tr>
            <tr>
                <td><label>创建日期：</label></td>
                <td><input class="easyui-datetimebox" id="cdt" name="cdt" value="${data.cdt}"/></td>
            </tr>
            <tr>
                <td><label>更新日期：</label></td>
                <td><input class="easyui-datetimebox" id="udt" name="udt" value="${data.udt}"/></td>
            </tr>
        </table>
    </form>
</body>
</html>