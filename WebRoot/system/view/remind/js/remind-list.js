//初始化
$(function() {
	remind.init();
	
});

var remind = {
	init : function() {
		//初始化字典
		this.init_unicode();
		//清空
		this.clear_report();
		//查询
		this.search_report();
		//加载数据
		this.initData();
		//明细
		$(".table").on('click', '.detail', function() {
			var id = $(this).parents('tr').attr('data-id');
			window.location.href = "remind-detail.html?id=" + id;
		});

	},
	initData : function() {
		var self = this;
		self.initDataFun().done(function(data) {
			//console.log(data)
			if (data) {
				$('#Pagination').pagination(data.recordCount, {
					jump : true,
					page_count : data.pageCount,
					callback : function() {
						var pageNo = $('#pagevalue').val();
						var param = {
							pageno : pageNo
						};
						self.initDataFun(param);
					}
				});
			}
		});
	},
	initDataFun : function(param) {
		var self = this;
		//
		var data = {
			pageno : 1
		};
		$('#form').find(":input").each(function() {
			var name = this.name;
			if (name) {
				data[name] = $(this).val();
			}
		});
		data = $.extend(data, param || {});
		return mkh.ajax({
			url : "../../../remind/message/page.action",
			type : "post",
			dataType : 'json',
			data : data,
			success : function(data) {
				self.createTable(data);
			}
		});
	},
	//生成表格
	createTable : function(json) {
		//
		var html = "";
		$.each(json.data, function(i, item) {
			//console.log(item)
			html += "<tr data-id='" + item.user_id + "'><td>" + (i + 1) + "</td>" +
				"<td>" + item.user_name + "</td>" +
				"<td>" + item.user_card + "</td>" +
				"<td>" + item.user_phone + "</td>" +
				"<td>" + item.doctor_name + "</td>" +
				"<td>" + item.ahdi_value + "</td>" +
				"<td>" + item.chronic + "</td>" +
				"<td>" + item.user_sex + "</td>" +
				"<td>" + item.user_age + "</td>" +
				"<td>" + item.user_marriage + "</td>" +
				"<td>" + item.user_work + "</td>" +
				"<td><a class='detail btn btn-primary radius size-S'><i class='Hui-iconfont'>&#xe627;</i>&nbsp;查看详情</a></td></tr>";

		});
		$('.table tbody').empty();
		$('.table tbody').append(html);
	},
	//清空
	clear_report : function() {
		$('.btn-danger').on('click', function() {
			$('#form').find(":input").each(function() {
				$(this).val("");
			});
		});
	},
	//查询
	search_report : function() {
		var self = this;
		$('.btn-success', '#form').on('click', function() {
			self.initDataFun().done(function(data) {
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
							self.initDataFun(param);
						}
					});
				}
			});
		});
	},
	//初始化字典
	init_unicode : function() {
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
								html += "<option value='" + item.order_id + "'>" + item.unicode_name + "</option>";
							});
							$(self).empty();
							$(self).append(html);
						}
					}
				});
			}
		});
	},
	//分页

};