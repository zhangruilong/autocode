<?xml version="1.0" encoding="UTF-8"?>
<beans>
	<bean id="404" class="com.system.tools.base.HandlerFor404" />
	<bean id="BaseAction" class="com.system.tools.base.BaseAction" />
	<bean id="System_userAction" class="com.system.action.more.System_userAction" />
	<bean id="System_powerAction" class="com.system.action.more.System_powerAction" />
	<bean id="System_powerviewAction" class="com.system.action.System_powerviewAction" />
	<bean id="System_roleAction" class="com.system.action.System_roleAction" />
	<bean id="System_rolepowerAction" class="com.system.action.more.System_rolepowerAction" />
	<bean id="System_roleuserAction" class="com.system.action.System_roleuserAction" />
	<bean id="System_roleuserviewAction" class="com.system.action.System_roleuserviewAction" />
	<bean id="System_statusAction" class="com.system.action.System_statusAction" />
	<bean id="System_attachAction" class="com.system.action.more.System_attachAction" />
	<bean id="Om_treeviewAction" class="com.om.action.Om_treeviewAction" />
	<bean id="Om_positionAction" class="com.om.action.Om_positionAction" />
	<bean id="Om_posiorgviewAction" class="com.om.action.Om_posiorgviewAction" />
	<bean id="Om_organizationAction" class="com.om.action.Om_organizationAction" />
	<bean id="Om_grouptreeviewAction" class="com.om.action.Om_grouptreeviewAction" />
	<bean id="Om_groupposiAction" class="com.om.action.Om_groupposiAction" />
	<bean id="Om_groupempviewAction" class="com.om.action.Om_groupempviewAction" />
	<bean id="Om_groupAction" class="com.om.action.Om_groupAction" />
	<bean id="Om_empposorgviewAction" class="com.om.action.Om_empposorgviewAction" />
	<bean id="Om_emppositionAction" class="com.om.action.Om_emppositionAction" />
	<bean id="Om_emporgAction" class="com.om.action.Om_emporgAction" />
	<bean id="Om_employeeAction" class="com.om.action.Om_employeeAction" />
	<bean id="Om_empgroupAction" class="com.om.action.Om_empgroupAction" />
	<bean id="Om_dutyAction" class="com.om.action.Om_dutyAction" />
	<bean id="Om_busiorgAction" class="com.om.action.Om_busiorgAction" />
	<bean id="Cms_seoAction" class="com.cms.action.Cms_seoAction" />
	<bean id="Cms_applycAction" class="com.cms.action.Cms_applycAction" />
	<bean id="Cms_intentioncAction" class="com.cms.action.Cms_intentioncAction" />
	<bean id="Cms_jobpublishAction" class="com.cms.action.Cms_jobpublishAction" />
	<bean id="Cms_contentAction" class="com.cms.action.more.Cms_contentAction" />

<#list entity as mentity>
	<bean id="${mentity.name}Service" class="${package}.service.${mentity.name}Service" />
</#list>
</beans>