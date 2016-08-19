Ext.onReady(function() {
	var ${entity.name}classify = "${entity.chineseName}";
	var ${entity.name}title = "当前位置:业务管理》" + ${entity.name}classify;
	var ${entity.name}action = "${entity.name}Action.do";
	var ${entity.name}fields = ['${entity.keyColumn.fieldName}'
	               			<#list entity.columns as column>
	        			    ,'${column.fieldName}' 
	        			    </#list>
	        			      ];// 全部字段
	var ${entity.name}keycolumn = [ '${entity.keyColumn.fieldName}' ];// 主键
	var ${entity.name}store = dataStore(${entity.name}fields, basePath + ${entity.name}action + "?method=selQuery");// 定义${entity.name}store
	var ${entity.name}dataForm = Ext.create('Ext.form.Panel', {// 定义新增和修改的FormPanel
		id:'${entity.name}dataForm',
		labelAlign : 'right',
		frame : true,
		layout : 'column',
		items : [ {
			columnWidth : 1,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '${entity.keyColumn.chineseName}',
				id : '${entity.name}${entity.keyColumn.fieldName}',
				name : '${entity.keyColumn.fieldName}',
				maxLength : 100
			} ]
		}
		<#list entity.columns as column>
		, {
			columnWidth : 1,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '${column.chineseName}',
				id : '${entity.name}${column.fieldName}',
				name : '${column.fieldName}',
				maxLength : 100
			} ]
		}
		</#list>
		]
	});
	
	var ${entity.name}bbar = pagesizebar(${entity.name}store);//定义分页
	var ${entity.name}grid =  Ext.create('Ext.grid.Panel', {
		height : document.documentElement.clientHeight - 4,
		width : '100%',
		forceFit: true,
		title : ${entity.name}title,
		store : ${entity.name}store,
		bbar : ${entity.name}bbar,
	    selModel: {
	        type: 'checkboxmodel'
	    },
	    plugins: {
	         ptype: 'cellediting',
	         clicksToEdit: 1
	    },
		columns : [{xtype: 'rownumberer',width:36}, 
		{// 改
			header : '${entity.keyColumn.chineseName}',
			dataIndex : '${entity.keyColumn.fieldName}',
			sortable : true, 
			minWidth: 80,
			editor: {
                xtype: 'textfield',
                editable: false
            }
		}
	<#list entity.columns as column>
		, {
			header : '${column.chineseName}',
			dataIndex : '${column.fieldName}',
			sortable : true,  
			minWidth: 80,
			editor: {
                xtype: 'textfield'
            }
		}
	</#list>
		],
		tbar : [{
				text : Ext.os.deviceType === 'Phone' ? null : "新增",
				iconCls : 'add',
				handler : function() {
					${entity.name}dataForm.form.reset();
					Ext.getCmp("${entity.name}${entity.keyColumn.fieldName}").setEditable (true);
					createTextWindow(basePath + ${entity.name}action + "?method=insAll", "新增", ${entity.name}dataForm, ${entity.name}store);
				}
			},'-',{
				text : Ext.os.deviceType === 'Phone' ? null : "保存",
				iconCls : 'ok',
				handler : function() {
					var selections = ${entity.name}grid.getSelection();
					if (Ext.isEmpty(selections)) {
						Ext.Msg.alert('提示', '请至少选择一条数据！');
						return;
					}
					commonSave(basePath + ${entity.name}action + "?method=updAll",selections);
				}
			},'-',{
				text : Ext.os.deviceType === 'Phone' ? null : "修改",
				iconCls : 'edit',
				handler : function() {
					var selections = ${entity.name}grid.getSelection();
					if (selections.length != 1) {
						Ext.Msg.alert('提示', '请选择一条数据！', function() {
						});
						return;
					}
					${entity.name}dataForm.form.reset();
					Ext.getCmp("${entity.name}${entity.keyColumn.fieldName}").setEditable (false);
					createTextWindow(basePath + ${entity.name}action + "?method=updAll", "修改", ${entity.name}dataForm, ${entity.name}store);
					${entity.name}dataForm.form.loadRecord(selections[0]);
				}
			},'-',{
	            text: '操作',
	            menu: {
	                xtype: 'menu',
	                items: {
	                    xtype: 'buttongroup',
	                    columns: 3,
	                    items: [{
	                    	text : "删除",
	        				iconCls : 'delete',
	        				handler : function() {
	        					var selections = ${entity.name}grid.getSelection();
	        					if (Ext.isEmpty(selections)) {
	        						Ext.Msg.alert('提示', '请至少选择一条数据！');
	        						return;
	        					}
	        					commonDelete(basePath + ${entity.name}action + "?method=delAll",selections,${entity.name}store,${entity.name}keycolumn);
	        				}
	                    },{
	                    	text : "导入",
	        				iconCls : 'imp',
	        				handler : function() {
	        					commonImp(basePath + ${entity.name}action + "?method=impAll","导入",${entity.name}store);
	        				}
	                    },{
	                    	text : "后台导出",
	        				iconCls : 'exp',
	        				handler : function() {
	        					Ext.Msg.confirm('请确认', '<b>提示:</b>请确认要导出当前数据？', function(btn, text) {
	        						if (btn == 'yes') {
	        							window.location.href = basePath + ${entity.name}action + "?method=expAll"; 
	        						}
	        					});
	        				}
	                    },{
	                    	text : "前台导出",
	        				iconCls : 'exp',
	        				handler : function() {
	        					commonExp(${entity.name}grid);
	        				}
	                    },{
	                    	text : "附件",
	        				iconCls : 'attach',
	        				handler : function() {
	        					var selections = ${entity.name}grid.getSelection();
	        					if (selections.length != 1) {
	        						Ext.Msg.alert('提示', '请选择一条数据！', function() {
	        						});
	        						return;
	        					}
	        					var fid = '';
	        					for (var i=0;i<${entity.name}keycolumn.length;i++){
	        						fid += selections[0].data[${entity.name}keycolumn[i]] + ","
	        					}
	        					commonAttach(fid, ${entity.name}classify);
	        				}
	                    }]
	                }
	            }
			},'->',{
				xtype : 'textfield',
				id : 'query${entity.name}action',
				name : 'query',
				emptyText : '模糊匹配',
				width : 100,
				enableKeyEvents : true,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							if ("" == Ext.getCmp("query${entity.name}action").getValue()) {
								${entity.name}store.load();
							} else {
								${entity.name}store.load({
									params : {
										query : Ext.getCmp("query${entity.name}action").getValue()
									}
								});
							}
						}
					}
				}
			}
		]
	});
	${entity.name}grid.region = 'center';
	${entity.name}store.on("beforeload",function(){ 
		${entity.name}store.baseParams = {
				query : Ext.getCmp("query${entity.name}action").getValue()
		}; 
	});
	${entity.name}store.load();//加载数据
	var win = new Ext.Viewport({//只能有一个viewport
		resizable : true,
		layout : 'border',
		bodyStyle : 'padding:0px;',
		items : [ ${entity.name}grid ]
	});
})
