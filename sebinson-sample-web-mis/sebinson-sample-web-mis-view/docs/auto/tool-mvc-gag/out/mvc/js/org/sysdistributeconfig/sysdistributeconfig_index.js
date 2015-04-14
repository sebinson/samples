$package('Gooagoo.sysdistributeconfig');
Gooagoo.sysdistributeconfig.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'sysDistributeConfig.do?method=page',               
                columns : [ [
                  {
                    field : 'checkboxfield',
                    checkbox : true                   
                  }
                 ,{
                    field : 'id',
                    hidden:true,
                    title : '自动编号'
                  }
                 ,{
                    field : 'partnerId',
                    title : '合作机构编号'
                  }
                 ,{
                    field : 'businessType',
                    title : '业务类型'
                  }
                 ,{
                    field : 'distributeType',
                    title : '分发类型'
                  }
                 ,{
                    field : 'distributeUrl',
                    title : '分发地址'
                  }
                 ,{
                    field : 'encryptKey',
                    title : '机构秘钥'
                  }
                 ,{
                    field : 'authType',
                    title : '权限类型'
                  }
                 ,{
                    field : 'shopId',
                    title : '商家编号'
                  }
                 ,{
                    field : 'entityId',
                    title : '实体店编号'
                  }
                 ,{
                    field : 'brandId',
                    title : '品牌'
                  }
                 ,{
                    field : 'areaId',
                    title : '地区'
                  }
                 ,{
                    field : 'isDel',
                    title : '是否删除'
                  }
                 ,{
                    field : 'createTime',
                    formatter : formatDatetime,
                    title : '创建时间'
                  }
                 ,{
                    field : 'cTimeStamp',
                    formatter : formatDatetime,
                    title : '最后一次修改时间'
                  }
                ]],
                idField : 'id',
                pagination : true
            },
            action: {
                'add' : basePath+'sysDistributeConfig.do?method=add',
                'remove' : basePath+'sysDistributeConfig.do?method=delete',
                'update' : basePath+'sysDistributeConfig.do?method=update'
            },
            event : {
                edit : function() {
                    var rows = $('#dataList').datagrid('getChecked');
                    var $dialog = $("#editWin");
                    if (rows && rows.length == 0) {
                        Gooagoo.alert('警告', '请选择一行记录.', 'warning');
                    } else if (rows && rows.length == 1) {
                        $dialog.dialog('open');                        
                        $dialog.dialog('setTitle', '编辑');
                        $dialog.dialog('refresh', 'sysDistributeConfig.do?method=formU&id=' + rows[0]['id']);
                    } else if (rows && rows.length > 1) {
                        Gooagoo.alert('警告', '仅能选择一行记录.', 'warning');
                    }
                    return;
                },
                add : function() {
                    var $dialog = $("#editWin");
                        $dialog.dialog('open');                        
                        $dialog.dialog('setTitle', '添加');
                        $dialog.dialog('refresh', 'sysDistributeConfig.do?method=formA');
                    return;
                }
            }
        },        
        initSearchForm : function(){
            $("#searchForm input#businessType").combobox({
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'账单'}]                             
            });                            
            $("#searchForm input#distributeType").combobox({
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'CXF'}]                             
            });                            
        },       
        init : function() {
            this.initSearchForm();
            var dg = new GDataGrid(_this.config);
            dg.init();
            return dg;
        }
    }
    return _this;
}();

$(function() {
    Gooagoo.sysdistributeconfig.index.init();
});