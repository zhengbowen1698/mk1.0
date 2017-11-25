//明细
$("#role_add").on('click', function() {
	layer_show('角色添加', 'role-add.html', '50', '72');
});

//编辑
$(".table").on('click', 'a.edit', function() {
	var id = $(this).parents('tr').attr('data-id');
	layer_show('角色编辑', 'role-edit.html?id=' + id, '50', '72');
});
//角色详情
$(".table").on('click', 'a.detail', function() {
	var id = $(this).parents('tr').attr('data-id');
	layer_show('角色详情', 'role-detail.html?id=' + id, '50', '72');
});

function reload() {
	window.location.reload();
}

var initDataFun = function(param) {
	var data = {
		pageNo : 1
	};
	$('#form').find(":input").each(function() {
		var name = this.name;
		if (name) {
			data[name] = $(this).val();
		}
	});
	data = $.extend(data, param || {});
	return mkh.ajax({
		url : "../../../role/gridQuery.action",
		type : "post",
		dataType : 'json',
		data : data,
		success : function(data) {
			createTable(data);
		}
	});
}
//回调
initDataFun().done(function(data) {
	//console.log(data)
	if (data) {
		$('#Pagination').pagination(data.recordCount, {
			jump : true,
			page_count : data.pageCount,
			callback : function() {
				var pageNo = $('#pagevalue').val();
				var param = {
					pageNo : pageNo
				};
				initDataFun(param);
			}
		});
	}
});
//生成表
var createTable = function(json) {
	//
	var html = "";
	$.each(json.data, function(i, item) {
		//console.log(item)
		html += "<tr data-id='" + item.order_id + "'><td><input type='checkbox' value='" + item.order_id + "'/></td>" +
			"<td>" + (i + 1) + "</td>" +
			"<td>" + item.order_id + "</td>" +
			"<td>" + item.role_name + "</td><td>" + item.role_note + "</td><td>" + item.create_date +
			"<td><a title='编辑' class='edit btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe60c;</i>&nbsp;编辑</a>&nbsp;&nbsp;"+
			"<a title='详情' class='detail btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe667;</i>&nbsp;详情</a></td></tr>";

	});
	$('.table tbody').empty();
	$('.table tbody').append(html);
}

//查询
var search_report = function() {
	$('.btn-success', '#form').on('click', function() {
		initDataFun().done(function(data) {
			//console.log(data)
			if (data) {
				$('#Pagination').pagination(data.recordCount, {
					jump : true,
					page_count : data.pageCount,
					callback : function() {
						var pageNo = $('#pagevalue').val();
						var param = {
							pageNo : pageNo
						};
						initDataFun(param);
					}
				});
			}
		});
	});
}();


//清空
var clear_report = function() {
	$('.btn-danger').on('click', function() {
		$('#form').find(":input").each(function() {
			$(this).val("");
		});
	});
}();

//初始化字典
var init_unicode = function() {
	$('.select_style', '.collection-baseinfo').each(function() {
		var type = $(this).attr('data-type');
		var self = this;
		if (type) {
			mkh.ajax({
				url : "../../../unicode/type.action",
				type : "get",
				dataType : 'json',
				data : {
					type : type
				},
				success : function(data) {
					if (data.status === 1) {
						var items = data.data;
						var html = "<option value=''>全部</option>";
						$.each(items, function(i, item) {
							html += "<option value='" + item.unicode_name + "'>" + item.unicode_name + "</option>";
						});
						$(self).empty();
						$(self).append(html);
					}
				}
			});
		}
	});
}();

//删除
$('#role_delete').on('click', function() {
	var len = $('input:checked').length;
	if (len < 1) {
		layer.msg("请选择角色");
		return;
	}
	layer.confirm('你确定删除吗？', {
		btn : [ '确定', '取消' ] //按钮
	}, function() {
		var arr = new Array();
		$('input:checked').each(function(i) {
			if ($(this).val() != 'on') {
				var obj = {};
				obj.order_id = $(this).val();
				arr.push(obj);
			}
		});
		mkh.ajax({
			url : "../../../role/deleteRole.action",
			type : "post",
			dataType : 'json',
			data : {
				list : arr
			},
			success : function(data) {
				if (data.info == "success") {
					reload();
					layer.alert("删除成功");
				} else if (data.info == "bind") {
					layer.alert("角色已绑定用户，不能删除！");
				} else {
					layer.alert("删除失败");
				}

			}
		});
	}, function() {
		//
	});

});
//系统角色
var sys_role = function() {
	$('.table').on('click', ':checkbox', function() {
		var value = $(this).val();
		if (value == '1500002' || value == '1500003' || value == '1500004') {
			this.checked = false;
			layer.msg('角色不能删除');
		}
	});
}();

$(function(){
	//
	$("#checkAll").find('input').off();
	$("#checkAll").find('input').on("click", function(){
		var checked = this.checked;
		if(checked){
			$('.table tbody').find('input').each(function(){
				var value = $(this).val();
				if (value == '1500002' || value == '1500003' || value == '1500004') {
					this.checked = false;
				}else{
					this.checked = true;
				}
			});
		}else{
			$('.table tbody').find('input').each(function(){
				this.checked = false;
			});
		}
	});

	
});