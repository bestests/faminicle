$("#loginForm").submit(function(){
		var param = $(this).serialize();
		console.log(param);
		
		//ajax로 아이디와 비밀번호 가져오기
		$.post(
			contextRoot+"/chronicle/login.do",
			{
				id:$("#id").val(),
				pass:$("#pass").val()
			},
			function(resultObj){
				console.log(resultObj);
				console.log("로그인 상태 : "+resultObj.ajaxResult.status);
				
				if(resultObj.ajaxResult.status == "success"){
					
					swal({   
						title: "로그인 되었습니다."+resultObj.ajaxResult.data.name+" 님 안녕하세요!",   
						text: "자동으로 닫혀요~.",
						imageUrl: "../images/slide/success.jpg",
						timer:1000,
						showConfirmButton: false 
					});
					
//					alert("로그인 되었습니다. "+resultObj.ajaxResult.data.name+" 님 안녕하세요!")

					location.href="main.html";
				}else{
					console.log("로그인 실패");
					alert("아이디 혹은 비밀번호가 일치하지 않습니다.다시한번 확인해주세요");
				}
				    
			},"json");
		return false;
});


//join

var  idFlag=false;
function idCk(event){
   var ckId= $("#id").val();
   
   //아이디 체크 정규식 a~z, 0~9까지 5~15숫자이내
   var regId = /^[a-z0-9_]{5,15}$/;
   if( !regId.test(ckId)){
      $("#text").html("5~15자의 영문 소문자, 숫자만 사용가능합니다.");
      $("#text").css('color',"red");
      idFlag=false;
      $("#id").focus();
      return false;
   }else{
      console.log(ckId);
      $.post(
         contextRoot + "/chronicle/checkId.do",
         {id: ckId},
         function(resultObj){                  
            if(resultObj.data < 1){                     
               $("#text").html("사용가능한 아이디입니다.");
               $("#text").css('color',"blue");
               idFlag=true;
            }else{
               $("#text").html("이미 사용중인 아이디입니다.");
               $("#text").css('color',"red");
            }
         },"json");
      return false;   
   }
};

function pwCk(){
	  if($("#pass").val()==$("#pass2").val()){                     
		  $("#text2").html("비밀번호가 동일합니다.");
        $("#text2").css('color',"blue");
        idFlag=true;
     }else{
        $("#text2").html("비밀번호가 동일하지않습니다.");
        $("#text2").css('color',"red");
     }
}

// 회원가입 누를때 
$("#registMember").submit(function () {
   
   var regTel = /^\d{10,11}$/;
   if( !regTel.test($("#tel").val())){
      alert("연락처는 -없이 숫자(10~11자리)만 입력 가능합니다");
      return false;
   }
   
   var regPass = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
   if( !regPass.test($("#pass").val())){
      alert("비밀번호는 특수문자와 숫자를 포함하여 8~16자로 설정하여야합니다.");
      return false;
   }
   
   
   
   if(idFlag==false){
      alert("중복된 아이디 입니다");
      return false;
   }
   
   if($("#pass").val() != $("#pass2").val()){
      alert("비밀번호가 일치하지 않습니다. 다시한번 확인해주세요");
      $("#pass2").focus();
      return false;
   }
   
   var param = $(this).serialize();   
   console.log(param);
   console.log("테스트중");
   $.post(
      contextRoot + "/chronicle/registMember.do",
      param,
      function (resultObj) {   
         console.log(resultObj);
         console.log(resultObj.data);
         console.log(resultObj.members.id);
         
         alert("등록이 완료되었습니다");
         
         
         
         
         location.href="main5.html";
      },"json") 
      .fail(function(){	
      	alert("실패");
		});

     
   return false;
});



















