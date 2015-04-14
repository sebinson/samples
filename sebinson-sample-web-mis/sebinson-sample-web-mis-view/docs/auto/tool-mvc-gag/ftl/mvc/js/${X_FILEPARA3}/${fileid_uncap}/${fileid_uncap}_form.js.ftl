$package('Gooagoo.${fileid_uncap}');
Gooagoo.${fileid_uncap}.form = function() {
    var _this = {
        onLoad : function() {
            <#list list as column>               
                <#if column.x_showInForm?upper_case=='Y' && column.x_cpattern?contains('combo')>
                    <#if column.x_cvalue??>
                        <#if column.x_cvalue?contains('[')>
            ${'$'}("#editWin #editForm input#${column.x_cname}").combobox({
                required: true,
                valueField: '${column.x_cvalue?split("|")[1]!"id"}',
                textField: '${column.x_cvalue?split("|")[2]!"text"}',
                panelHeight : 'auto',
                editable : false ,
                data:${column.x_cvalue?split("|")[0]!"[]"}                             
            });                            
                        </#if>
                        <#if column.x_cvalue?contains('.do')>
            ${'$'}("#editWin #editForm input#${column.x_cname}").combobox({
                required: true,
                valueField: '${column.x_cvalue?split("|")[1]!"id"}',
                textField: '${column.x_cvalue?split("|")[2]!"text"}',
                panelHeight : 'auto',
                editable : false ,
                url:'${column.x_cvalue?split("|")[0]!"#"}'                          
            });
                        </#if>
                    </#if>
                </#if>               
            </#list>
        }
    }
    return _this;
}();