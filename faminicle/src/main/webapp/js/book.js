/**************
name : book.js
maker : play-ground.kr okhi1@naver.com
version : 1.1
date : 20150909

사용시 출처를 명시바랍니다.
***************/
(function($){
	var ele,opt,idx=0,pagewidth=0,speed=10,bookbg='#eee',active = false;
	$.fn.book = function(option){
		ele = $(this);
		opt = option;
		pagewidth = ele.width()/2;
		if(option.speed) speed = option.speed;
		if(option.bookbg) bookbg = option.bookbg;
		
		setting();

		$('.page',ele).click(function(){
			if(active==true) return false;
			active=true;
			idx = $('.page',ele).index($(this)); 
			if(idx%2==0){ //left
				flipleft();
			}else{ //right
				flipright();
			}
		});


		function setting(){
			
			$('.page',ele).last().attr('last','true');

			if($('.page',ele).length%2==0){//odd
				ele.prepend("<div class='page'> </div>");
				ele.append("<div class='page'> </div>");
			}else{//even
				ele.prepend("<div class='page'> </div>");
			}
			
			ele.css({'position':'relative'});
			$('.page',ele).css({
				'position':'absolute',
				'overflow':'hidden',
				'top':'0px',
				'background':bookbg,
				'width':'0px',
				'height':ele.height()+'px'
				});
			$('.page',ele).each(function(i,e){
				if($(this).children().length>0){
					$(this).css('background','url('+$(this).children().attr('src')+')');
					$(this).empty();
				}

				if(i%2==0){
					$(this).css({'left':'0px','z-index':1});
				}else{
					$(this).css({'right':'0px','z-index':1});
				}
				if(i==0 || i==1) $(this).css({'width':pagewidth+'px'});	
			});

			
		}

		function flipright(){ 
			var nextidx = idx+1;
			var next2idx = nextidx+1;
			var i=0; 
			var Interval = setInterval(function(){
				if(i<=pagewidth){
					
					$('.page',ele).eq(nextidx).css({
						'width':i+'px',
						'right':'auto',
						'left':ele.width()-(i*2)+'px',
						'box-shadow':'inset -25px 0px 20px -20px rgba(255,255,255,0.7)',
						'background-position':'0px'
					}); 
					$('.page',ele).eq(next2idx).css({
						'width':i+'px',
						'background-position':((pagewidth*(-1))+i)+'px 0px',
						'background-repeat':'no-repeat',
						'box-shadow':'inset '+(i/2+25)+'px 0px 20px -20px rgba(0,0,0,'+((pagewidth/2)-(i/2))*0.0035+')'		
					});
					i+=10;

				}else{
					
					
					if((next2idx+1)==$('.page',ele).length){
						$('.page',ele).eq(nextidx).css({
							'box-shadow':''
						});
						$('.page',ele).eq(next2idx).css({
							'box-shadow':''
						});
					}else{
						$('.page',ele).eq(nextidx).css({
							'box-shadow':'inset -25px 0px 20px -20px rgba(0,0,0,0.7)'
						});
						$('.page',ele).eq(next2idx).css({
							'box-shadow':'inset 25px 0px 20px -20px rgba(0,0,0,0.7)'
						});
					}


					
					clearInterval(Interval);
					active = false;
				}
			},speed);
			

		}

		function flipleft(){ 
			if(idx<=0) {active=false; return false;}
			var previdx = idx;
			var prev2idx = previdx-1;
			var nextidx = previdx+1;
			var i=0; 
			
			$('.page',ele).eq(prev2idx).css({'right':'auto','left':'0px','width':'0px','z-index':2});

			var Interval = setInterval(function(){
				if(i<=pagewidth){
					
					$('.page',ele).eq(prev2idx).css({
						'width':i+'px',
						'left':i+'px',
						'box-shadow' : '',
					});

					$('.page',ele).eq(prev2idx-1).css({
						'box-shadow':'inset -'+((pagewidth-(i/2+25))+(pagewidth/5))+'px 0px 20px -20px rgba(0,0,0,'+((pagewidth/2)-(i/2))*0.0035+')'	
					});

					$('.page',ele).eq(previdx).css({
						'width':(pagewidth-i)+'px',
						'left':(i*2)+'px',
						'background-position':((i*2)*(-1))+'px 0px',
						'background-repeat':'no-repeat'
					});
					
					if($('.page',ele).eq(previdx).attr('last')!='true'){
						$('.page',ele).eq(previdx).css({
							'box-shadow': 'inset -'+(25+i)+'px 0px 20px -20px rgba(0,0,0,0.7)'
						});
					}
					
					
					i+=10;
				}else{

					if(prev2idx>1){

						$('.page',ele).eq(nextidx).css({
							'width':'0px',
							'left':'auto',
							'right':'0px'
						});

						$('.page',ele).eq(prev2idx).css({
							'z-index':1,
							'box-shadow':'inset 25px 0px 20px -20px rgba(0,0,0,0.7)'
						});

						$('.page',ele).eq(prev2idx-1).css({
							'box-shadow':'inset -25px 0px 20px -20px rgba(0,0,0,0.7)'
						});
					}else{

						$('.page',ele).eq(nextidx).css({
							'width':'0px',
							'left':'auto',
							'right':'0px'
						});

						$('.page',ele).eq(prev2idx).css({
							'z-index':1
						});
					}
					
					
					clearInterval(Interval);
					active = false;
				}
			},speed);
				
		}
	};



})(jQuery);