package ${sheet_uncap}.vo;

import java.io.Serializable;
import java.util.Date;

public class F${fileid_cap_first} implements Serializable
{
	private static final long serialVersionUID = 1L;

	<#list list as column>
	private ${column.x_ctype} ${column.x_cname?uncap_first};
	</#list>
	<#list list as column>
	public ${column.x_ctype} get${column.x_cname?cap_first}(){
		return this.${column.x_cname?uncap_first};
	}
	public void set${column.x_cname?cap_first}(${column.x_ctype} ${column.x_cname?uncap_first}){
		this.${column.x_cname?uncap_first} = ${column.x_cname?uncap_first};
	}
	</#list>
}