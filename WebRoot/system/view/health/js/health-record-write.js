$(function() {
	survey_write.init();
})


var survey_write = {
	//
	init : function() {
		//加载用户
		this.loadUser();
		//初始化选项
		this.initSelect();
		//绑定保存
		this.bindClickSave();
	},
	//绑定保存
	bindClickSave : function() {
		$('#do_save').on('click', function() {
			survey_write.doSave();
		});
	},
	//加载用户信息
	loadUser : function() {
		//用户信息
		var user_id = mkh.getUrlParam("id");
		var data = {
			user_id : user_id,
			user_type : "1500001"
		};
		$.ajax({
			url : "../../../survey/queryUserInfo.action",
			type : "post",
			dataType : 'json',
			data : data,
			success : function(data) {
				if (data) {
					$('#userMessage').find('li span').each(function() {
						var name = $(this).attr('data-name');
						if (data[name]) {
							if (name == "user_sex") {
								$(this).text(data[name] == '200001' ? '男' : '女');
							} else {
								$(this).text(data[name]);
							}
						}
					});
				}
			}
		});
	},
	//初始化选项
	initSelect : function() {
		var that = this;
		//
		this.loadoOptionContent().done(function(){
			that.loadUserSurvey();
		});
	},
	//获取用户调查信息
	loadUserSurvey : function(){
		//当前用户
		var user_id = mkh.getUrlParam("id");
		//
		$.ajax({
			url : "../../../survey/queryUserSurvey.action",
			type : "post",
			dataType : 'json',
			data : {user_id : user_id},
			success : function(data) {
				if (data) {
					//基础数据
					$('#userMessage').find('li span').each(function() {
						var name = $(this).attr('data-name');
						if (data[name]) {
							if (name == "user_sex") {
								$(this).text(data[name] == '200001' ? '男' : '女');
							} else {
								$(this).text(data[name]);
							}
						}
					});
					//答案
					$(":input").each(function() {
						var select_name = $(this).attr("data-select");
						var input_name = $(this).attr("data-name");
						//选择框
						if(select_name){
							select_name = select_name.substring(select_name.indexOf('_') + 1, select_name.length);
							var self = $(this);
							$.each(data.answers, function(i, m) {
								if (select_name == m.question_k_order_id) {
									self.val(m.survey_option_order_id);
									return false;
								}
							});
						}
						//输入框
						if(input_name){
							if (data[input_name]) {
								$(this).val(data[input_name]);
							}
						}
					});
					//多选框
					$(".check-box").each(function(){
						var select_name = $(this).attr("data-select");
						var that = this;
						if(select_name){
							select_name = select_name.substring(select_name.indexOf('_') + 1, select_name.length);
							var self = $(this);
							$.each(data.answers, function(i, m) {
								if (select_name == m.question_k_order_id) {
									if(m.survey_option_content == '是'){
										$(that).find(".icheckbox-blue").addClass("checked");
									}
									return false;
								}
							});
						}
					});
				}
			}
		});
	},
	//加载选项值
	loadoOptionContent : function() {
		var self = this;
		return $.ajax({
			url : "../../../health-record/getOptionContent.action",
			type : "post",
			dataType : 'json',
			data : {},
			success : function(data) {
				if (data) {
					//
					self.initSelectData(data);
					//
					self.initCheckBox(data);
				}
			}
		});
	},
	//
	initSelectData : function(data){
		$("select.radius", ".collection-baseinfo").each(function() {
			var $t = $(this);
			var name = $t.attr('data-select');
			var html = "<option value=''>请选择</option>";
			var items = data[name];
			if (name && items && items.length > 0) {
				//
				$.each(items, function(i, item) {
					html += "<option value='" + item.order_id + "''>" + item.survey_option_content + "</option>";
				});
				$t.empty();
				$t.append(html);
			}
		});
	},
	//
	initCheckBox : function(data) {
		$('.check-box').each(function() {
			var name = $(this).attr('data-select');
			var items = data[name];
			if (name && items && items.length > 0) {
				$(this).data(name, items);
			}
		});
	},
	//
	doSave : function() {
		var param = {};
		//用户信息
		var user_id = mkh.getUrlParam("id");
		if (user_id != "") {
			param.user_id = user_id;
		} else {
			return;
		}
		$('input:visible').each(function() {
			var name = this.name;
			if (name) {
				param[name] = $.trim($(this).val());
			}
		});
		//
		$("select.radius", ".collection-baseinfo").each(function() {
			var value = this.value;
			//
			var key_name = $(this).attr('data-select');
			key_name = key_name.substring(key_name.indexOf('_') + 1, key_name.length);
			param[key_name] = value;
		});
		//慢性疾病
		$('.check-box').each(function(){
			var $checked = $(this).find('.icheckbox-blue');
			var name = $(this).attr('data-select');
			var flag = $checked.hasClass("checked");
			if(name){
				if(flag){
					var data = $(this).data(name);
					$.each(data, function(i, item){
						var value = item.survey_option_content;
						if(value == "是"){
							var key = item.survey_question_order_id;
							param[key] = item.order_id;
						}
					});
				}else{
					var data = $(this).data(name);
					$.each(data, function(i, item){
						var value = item.survey_option_content;
						if(value == "否"){
							var key = item.survey_question_order_id;
							param[key] = item.order_id;
						}
					});
				}
			}
		});
		//
		return $.ajax({
			url : "../../../survey/saveAnswer.action",
			type : "post",
			dataType : 'json',
			data : param,
			success : function(data) {
				if(data.flag){
					layer.alert('保存成功！');
					//清空数据
					//survey_write.clearData();
					//
					survey_write.bindClickSave();
				}else{
					layer.alert(data.errmsg);
				}
			}
		});
	},
	//
	clearData : function(){
		$('input').each(function() {
			$(this).val('');
		});
		//
		$("select.radius", ".collection-baseinfo").each(function() {
			$(this).val('');
		});
		//慢性疾病
		$('.check-box').each(function(){
			var $checked = $(this).find('.icheckbox-blue');
			$checked.removeClass('checked');
		});
	}
};