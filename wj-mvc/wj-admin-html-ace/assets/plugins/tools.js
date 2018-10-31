var requery_temp = 0;
var post = function(url,data,success,error){
	var indexLoadding = layer.load(1, {
	  shade: [0.1,'#fff']
	});
	$.ajax({
		type:"post",
		url:url,
		data:data,
		async:true,
		xhrFields: {
		    withCredentials: true
		},
		success:function(d){
			layer.close(indexLoadding);
			if(d.code == 0){
				success(d);
			}else{
				switch(d.code){
					case -1:
					if(requery_temp == 0){
						layer.alert('您的登录已过期<br>请重新登录',{icon:7,end:function(){
							toLoginPage();
						}});
						requery_temp = 1;
					}
					break;
					case -12:
						layer.msg(d.msg,{icon:7});
					break;
					case -500:
						layer.msg(d.msg,{icon:2});
					break;
					default:
						error(d);
					break;
				}
			}
		},
		error : function(e){
			layer.close(indexLoadding);
			layer.msg("网络错误",{icon:2});
			console.log(e);
		}
	});
};


var uploadImg = function(t,success,error){
		var file = $(t).get(0).files[0];
		var reg=new RegExp("image/?");
		if(file && file.type &&  reg.test(file.type)){
			var index = layer.load(1, {
			  shade: [0.1,'#fff']
			});
			var oData = new FormData();
			oData.append("file",file);
			$.ajax({
			  url: urlConfig.common.imgUpload,
			  type: "POST",
			  data: oData,
			  processData: false,  // 不要去处理发送的数据
			  contentType: false ,  // 不设置Content-Type请求头
			  success : function(data){
			  	layer.close(index);
			  	if(data.code == 0){
			  		success(data);
			  	}else{
			  		error(data);
			  	}
			  },
			  error : function(e){
			  	layer.close(index);
			  	console.error(e);
			  	layer.msg("网络错误",{icon:2});
			  }
			});
		}else{
			layer.msg("请选择图片",{icon:7});
		}
};

var uploadFile = function(t,success,error){
		var file = $(t).get(0).files[0];
		if(file != null){
			var index = layer.load(1, {
			  shade: [0.1,'#fff']
			});
			var oData = new FormData();
			oData.append("file",file);
			$.ajax({
			  url: urlConfig.common.fileUpload,
			  type: "POST",
			  data: oData,
			  processData: false,  // 不要去处理发送的数据
			  contentType: false ,  // 不设置Content-Type请求头
			  success : function(data){
			  	layer.close(index);
			  	if(data.code == 0){
			  		success(data);
			  	}else{
			  		error(data);
			  	}
			  },
			  error : function(e){
			  	layer.close(index);
			  	console.error(e);
			  	layer.msg("网络错误",{icon:2});
			  }
			});
		}else{
			layer.msg("请选择文件",{icon:7});
		}
};
