<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <form class="edit-form" id="editForm">
        <table class="form-table" id="formTable">
            <input type="hidden" id="analyzeTemplateId" name="analyzeTemplateId"/>               
            <tr>
                <td><label>模板名称类型：</label></td>
                <td><input class="easyui-combobox" id="type" name="type"/></td>
            </tr>
            <tr>
                <td><label>模板名称：</label></td>
                <td><input class="easyui-textbox" id="name" name="name"/></td>
            </tr>
            <tr>
                <td><label>模板访问路径：</label></td>
                <td><input class="easyui-textbox" id="url" name="url"/></td>
            </tr>
            <tr>
                <td><label>状态：</label></td>
                <td><input class="easyui-combobox" id="status" name="status"/></td>
            </tr>
            <tr>
                <td><label>版本号：</label></td>
                <td><input class="easyui-textbox" id="ver" name="ver"/></td>
            </tr>
            <tr>
                <td><label>md5：</label></td>
                <td><input class="easyui-textbox" id="templateMd5" name="templateMd5"/></td>
            </tr>
            <tr>
                <td><label>备注：</label></td>
                <td><input class="easyui-textbox" id="remark" name="remark"/></td>
            </tr>
            <tr>
                <td><label>是否删除：</label></td>
                <td><input class="easyui-combobox" id="isDel" name="isDel"/></td>
            </tr>
            <tr>
                <td><label>创建时间：</label></td>
                <td><input class="easyui-datetimebox" id="createTime" name="createTime"/></td>
            </tr>
            <tr>
                <td><label>最后一次修改时间：</label></td>
                <td><input class="easyui-datetimebox" id="cTimeStamp" name="cTimeStamp"/></td>
            </tr>
        </table>
    </form>
</body>
</html>