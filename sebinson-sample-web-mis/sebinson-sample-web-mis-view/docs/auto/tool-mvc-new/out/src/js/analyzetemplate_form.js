$package('Gooagoo.analyzetemplate');
Gooagoo.analyzetemplate.form = function() {
    var _this = {
        onLoad : function() {
            $("#editWin #editForm input#type").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'prf格式'},{'id':'2','text':'配置模板'},{'id':'3','text':'ocr格式'}]                             
            });                            
            $("#editWin #editForm input#status").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'未启用'},{'id':'2','text':'启用'}]                             
            });                            
            $("#editWin #editForm input#isDel").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                url:'analyzeTemplate.do?method=findDicData'                          
            });
        }
    }
    return _this;
}();