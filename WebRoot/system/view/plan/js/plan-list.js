//明细
$(".table").on('click', '.detail', function() {
	var id = $(this).parents('tr').attr('data-id');
	window.location.href = "plan-detail.html?id=" + id;
});

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
		url : "../../../chronicManage/gridQuery.action",
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
		html += "<tr data-id='" + item.user_id + "'><td>" + (i + 1) + "</td><td>" + item.user_name + "</td>" +
			"<td>" + item.user_card + "</td><td>" + item.user_phone + "</td><td>" + item.doctor_name + "</td>" +
			"<td>" + item.ahdi_value + "</td>" +
			"<td>" + item.chronic_name + "</td>" +
			"<td>" + item.user_sex + "</td>" +
			"<td>" + item.user_age + "</td>" +
			"<td>" + item.user_marriage + "</td>" +
			"<td>" + item.user_work + "</td>" +
			"<td>" + (item.is_end == '101' ? "<span style='color:green'>已完成</span>" : "<span style='color:red'>进行中</span>") + "</td>" +
			"<td>" + item.update_date + "</td>" +
			"<td><a class='detail btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe627;</i>&nbsp;查看详情</a></td></tr>";

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
		if (type && type!= 'chronic') {
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
							html += "<option value='" + item.order_id + "'>" + item.unicode_name + "</option>";
						});
						$(self).empty();
						$(self).append(html);
					}
				}
			});
		}
	});
}();