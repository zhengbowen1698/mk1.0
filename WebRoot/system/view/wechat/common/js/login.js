$(function(){
	verify.init();
});
var verify = {
	init : function(){
		//this.clickBox();
	},
	//点击弹出验证框
	clickBox : function(){
		$(".btn").on("click",function(){
			var stringBox = "<div style='background-color:#fff;border-radius:2.5rem 2.5rem 1.5rem 1.5rem '>";//最外层
			    stringBox += "<div class='imgBox' style ='position: relative;'>";//头部
				stringBox += "<img style='width:100%;' src='common/image/upbox.png'/>";
				stringBox += "<img  style='width:42%;display:block;position: absolute;left:29%;top:-23%;' src='common/image/uphead.png'/>";
				stringBox += "<div class='fork' style='position: absolute;top:10%;right:3%;color:#F9674A;width:2.5rem;height:2.5rem;border-radius: 100%;background-color:#fff;line-height:2.5rem;font-size:3.2rem;text-align:center;'> ×</div>";
				stringBox += "</div>";//头部
				stringBox += "<div style='width:84%;margin:auto; padding:2.4rem 0 ;'>";//输入框
				stringBox += "<div style='border:0.1rem solid #E6E6E6;border-radius:4px'>";
				stringBox += "<input type='button' value='5+4=?' style='font-weight:bolder;color:#6697B0;width:59%;line-height:3.7rem;margin:0 0 0 1px;background-color:#fff;font-size:2.3rem'/>";
				stringBox += "<input class='answerIN' type='text' value='请输入答案' style='width:39%;background-color:#D9D9D9;border-bottom:2px solid #D9D9D9;line-height:3.7rem;margin:-2px -1px 0 2px;font-size:1.7rem;color:#A6A6A6;text-align:center;float:right;border-radius:0 4px 4px 0'/>";
				stringBox += "<div class='clearfix'></div></div>";//运算式
				stringBox += "<input type='button' style='width:84%;margin:auto;display:block;margin-top:2.4rem;line-height:4.3rem;border-radius:4px;background-color:#F9674A;color:#fff;' class='ok' value='确定' />";
				stringBox += "</div>";//输入框
				stringBox += "</div>";//最外层
			var first = layer.open({
				type: 1,
				shade: 'background-color: rgba(0,0,0,.3)',
				fixed: false,
				top:-185,
				style:'width:80%;border-radius:33%;',
				content: stringBox
			});
			verify.close(".ok",first);
			verify.close(".fork",first);
			verify.answerIuput();
		});
	},
	close : function(element,first){
		$(element).on('click',function(){
				layer.close(first);
			});
	},
	answerIuput : function(){
		$(".answerIN").on('mousemove',function(){
			$(this).val("");
			$(this).css("color","#6697B0");
		})
	},
	//保存的方法
	
}
