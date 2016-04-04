
		var index = 0;
		var index2 = 0;
		var left = 0;
		var num = 0;
		$.post(
			contextRoot + "/chronicle/selectEvent.do",function(result){
				var result = result.ajaxResult;
				for(var i in result.data) {
					if(result.data[i].evEnd != '1000-01-01'){
						html="<div class='tap'>"+result.data[i].evStart+" <br/>~ <br/>" +result.data[i].evEnd 
						+ "<form id='tForm'>"
						+"<input type='hidden' name='evStart' value='"+result.data[i].evStart+"'>"
						+"<input type='hidden' name='evEnd' value='"+result.data[i].evEnd+"'>"
						+"</form>"
						+"</div>";
						
						bookHtml="<div class='date"+[i]+"' id='date'>"
						+""+result.data[i].evTitle+"<hr>"
						+"<p id='dateContent'>"+result.data[i].evStart+" ~ "+result.data[i].evEnd+"</p>"
						+ "<form id='bForm'>"
						+"<input type='hidden' name='evStart' value='"+result.data[i].evStart+"'>"
						+"<input type='hidden' name='evEnd' value='"+result.data[i].evEnd+"'>"
						+"</form>"
						+"<div id='opadiv' class='"+[i]+"'/>"
						+"<div id='image'/>"
						+"<div id='noimage'/>"
						+"</div>";
						
						$("#tab").append(html);
						
						init_masonry ();
						$("#book").append(bookHtml).masonry("appended", bookHtml, true);	
						$("#book").masonry('reloadItems');
						$("#book").masonry("layout");
					}
					$.post(
							contextRoot + "/chronicle/seletePicByEvent.do",
								{evStart: result.data[i].evStart,
								evEnd: result.data[i].evEnd},	
							function(result){
								var result = result.ajaxResult
								console.log(result.data);
								if(result.data[0]){
								for(var k in result.data) {
									var picFilePath = result.data[k].picFilePath.substring(0, result.data[k].picFilePath.lastIndexOf("."));
									picFilePath += "_mini.jpg";
									html="<img src='"+picFilePath+"'/>";
									$(".date"+index+"> #image").append(html);
									index2++;
									if(index2 == result.data.length){index++; index2 = 0;};
								}}else{
									html="<img src='../images/slide/014.gif'>";
									html+="<h3>사진이 없습니다</h3>";
									$(".date"+index+"> #noimage").append(html);
								}
							},"json"
					);
					
				}
				if($("#tab").children().length > 10){
					$("#tabline").css({width:'88%'});
					$("#prev, #next").css({display:'inline-block'});
				}
			},"json"
		);
		$.getJSON(contextRoot + "/chronicle/list.do", function (result) {
			html="<img src='"+result.member.picMiniFilePath+"'>";
			$("#menu").prepend(html);
			$("#name").html(result.member.name + " 님");
			
		});
		$("#next").click(function(){
			if($("#tab").children().length-10 > num){
			 left += -10;
			$("#tab").css({left:''+left+'%'});
			num++;
			}else{
				swal({   title: "마지막 탭입니다.",   timer: 1000,   showConfirmButton: false, type: "warning" });
			}
		});
		$("#prev").click(function(){
			if(num != 0){
			 left += 10;
			$("#tab").css({left:''+left+'%'});
			num--;
			}else{
				swal({   title: "처음 탭입니다.",   timer: 1000,   showConfirmButton: false, type: "warning" });
			}
		});
// 		$("#book").click(function(event){
// 			location.href="album.html"
// 		})
		
		$("#tab").on('click',".tap", function(event) {
			var form = $(event.target).children()[2];
			var formData = new FormData(form);
			$.ajax({
				url:contextRoot + "/chronicle/seletePicByEvent.do",
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				success:function(result){
					var result = result.ajaxResult
					console.dir(result);
					var ac = result.data.length%4;
					if(ac != 0){ac = (result.data.length/4)+1}else{ac = result.data.length/4}
					var index=0;
					var count = result.data.length;
					console.log(ac);
					$("#book").empty();
					html="";
					for(var i=1; i <= ac; i++){
						html +="<div class='page'>";
						if(count < 4){
							for(var k =0; k < count; k++){
							html += "<img src='"+result.data[index].picFilePath +"'>";
							index++;
							}
						}else{
							for(var j =0; j<4; j++){
							html += "<img src='"+result.data[index].picFilePath +"'>";
							index++;
							}
							count = count - 4;
						html +="</div>";
					}
					}
					$("#book").append(html);
					$('#book').booklet({
						height: '88%',
				    	width: '86%',
				    	pagePadding: 10
				    });
				},dataType:"json"}
			);
			$("#tab div").css({background:'#CFF69B',color:'black'})
			$(event.target).css({color:'white',background:'maroon'})
		});
		
		$("#book").on('click',"#opadiv", function(event) {
			var form = ($(event.target).prev("form"));
			form.children("[name='evStart']").val();
			$.post(
				contextRoot + "/chronicle/seletePicByEvent.do",
				{evStart: form.children("[name='evStart']").val(),
				evEnd: form.children("[name='evEnd']").val()},function(result){
					var result = result.ajaxResult
					console.dir(result);
					var ac = result.data.length%4;
					if(ac != 0){ac = (result.data.length/4)+1}else{ac = result.data.length/4}
					var index=0;
					var count = result.data.length;
					console.log(ac);
					$("#book").empty();
					html="";
					for(var i=1; i <= ac; i++){
						html +="<div class='page'>";
						if(count < 4){
							for(var k =0; k < count; k++){
							html += "<img src='"+result.data[index].picFilePath +"'>";
							index++;
							}
						}else{
							for(var j =0; j<4; j++){
							html += "<img src='"+result.data[index].picFilePath +"'>";
							index++;
							}
							count = count - 4;
						html +="</div>";
						}
					}
					$("#book").unbind( 'click' );
					$("#book").append(html);
					$('#book').booklet({
				    	height: '88%',
				    	width: '86%',
				    	pagePadding: 10
				    });
					console.log($(event.target).attr('class'));
					$("#tab").children(":eq("+$(event.target).attr('class')+")").css({color:'white',background:'maroon'});
					
					if($(event.target).attr('class') > 9){
						left = ($(event.target).attr('class') - 9)*-10;
						console.log(left);
						$("#tab").css({left:''+left+'%'});
						num += $(event.target).attr('class') - 9;
					}
				},"json"
			);
		});
		$("#logOut").click(function() {
			swal({   title: "<span style='color:#FF0000'> 로그아웃 하시겠습니까? </span>",   
				text: "클릭시 로그아웃 됩니다.",   
				imageUrl: "../images/slide/balls.svg",
				confirmButtonColor:"#DD6B55",
				showCancelButton:true,
				closeOnConfirm:false,
				html:true
			},
		function(inConfirm) {
				
				if(inConfirm) {
					$.getJSON(
						contextRoot + "/chronicle/logout.do",
						function(result) {
							location.href="login.html";
						}
					)
					swal({   title: "로그 아웃",   
						text: "Faminicle을 이용해주셔서 감사합니다.",   
						imageUrl: "../images/slide/success.jpg" });
						
				}
			});
			return false;
		})
	
	function init_masonry () {
		var $container = $("#book");
		
		$container.masonry({
			itemSelector: ".date",
			initLayout: false
		});
		$container.imagesLoaded(function () {
		});
	};