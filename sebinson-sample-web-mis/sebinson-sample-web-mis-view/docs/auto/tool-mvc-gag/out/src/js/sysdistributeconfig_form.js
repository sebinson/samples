$package('Gooagoo.sysdistributeconfig');
Gooagoo.sysdistributeconfig.form = function() {
    var _this = {
        onLoad : function() {
            $("#editWin #editForm input#businessType").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'账单'}]                             
            });                            
            $("#editWin #editForm input#distributeType").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'CXF'}]                             
            });                            
            $("#editWin #editForm input#authType").combobox({
                required: true,
                valueField: 'englishName',
                textField: 'chineseName',
                panelHeight : 'auto',
                editable : false ,
                url:'sysDistributeConfig.do?method=findDicData&dicName=p_auth_type'                          
            });
            $("#editWin #editForm input#isDel").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'Y','text':'已删除'},{'id':'N','text':'未删除'}]                             
            });                            
        }
    }
    return _this;
}();