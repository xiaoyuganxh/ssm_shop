var szyFile = {
    fileDom:null,//html 文件上传控件
    preview:null,//图片预览的区域
    imgMaxSize:2,//图片大小最大2M
    filterDom:[],//符合条件的元素
    filterImgDataUrl:[],//图片的dataUrl数据
    dragArea:null,//拖放区域
	jcrop_api:null,
	selectWidth:300,//选框宽度
	selectHeight:217,//选框高度
	Ratio:1,//上传图片比例
    imgRegExp:function(f){
        if(!/\.(jpg|jpeg|png)$/i.test(f.name)){
            alert('您上传的不是图片,请重新选择后上传！');
            return false;
        }
        return true;
    },
	clearCoords:function(){
		 $('#coords input').val('');
	},
	showCoords:function(c){
		$('#x1').val(c.x);
		$('#y1').val(c.y);
		$('#x2').val(c.x2);
		$('#y2').val(c.y2);
		$('#w').val(c.w);
		$('#h').val(c.h);
	},
	jcropInit:function(ID,modle){
		if(modle == 0){
			$(ID).Jcrop({
				  onChange:   szyFile.showCoords,
				  onSelect:   szyFile.showCoords,
				  onRelease:  szyFile.clearCoords,
				  aspectRatio: szyFile.Ratio,
				  keySupport: false
				},function(){
				  szyFile.jcrop_api = this;
				});
		}else{
			$(ID).Jcrop({
			  onChange:   szyFile.showCoords,
			  onSelect:   szyFile.showCoords,
			  onRelease:  szyFile.clearCoords,
			  aspectRatio: szyFile.Ratio,
			  keySupport: false
			},function(){
			  szyFile.jcrop_api = this;
			});
		}
		$('#coords').on('change','input',function(e){
		  var x1 = $('#x1').val(),
			  x2 = $('#x2').val(),
			  y1 = $('#y1').val(),
			  y2 = $('#y2').val();
		  szyFile.jcrop_api.setSelect([x1,y1,x2,y2]);
		});
	},
    isSupport:function(){
        if(window.File && window.FileReader && window.FileList && window.Blob) {
            return true;
        } else {
           return false;
        }
    },
    select:function(e){
        var e = e || window.event;
		if(szyFile.isSupport()){
			if(szyFile.jcrop_api){
				szyFile.jcrop_api.destroy();
				$('#target').remove();
				$('<img src="" id="target" width="100%" style="display:none;position:absolute;top:0;left:0;"/>').insertBefore('#filterPrew');
			}
			$('#target').show();
			$('#filterPrew').hide();
			var files = e.target.files || e.dataTransfer.files;
			for(var i = 0, f; f = files[i]; i++){
				if(f){
					if(szyFile.imgRegExp(f)){
						if(f.size > szyFile.imgMaxSize*1024*1024){
							alert('图片过大,您上传的图片大于'+szyFile.imgMaxSize+'MB');
							return false;
						}else{
							szyFile.filterDom.push(f);
							var reader = new FileReader();
							reader.onload = (function(){
								return function(e){
									$('#target').attr('src',this.result);
									var resultImg = this.result;
									var img = new Image();
									img.onload = function(){
										var w = this.width;
										var h = this.height;
										var _top = 0;
										var _left = 0;
										if(w >= h){
											var imgB = szyFile.selectWidth/w;
											var imgB1 = w/h;
											if(h*imgB <= szyFile.selectHeight){
												$('#target').css('width',szyFile.selectWidth+'px');
												$('#target').css('height',h*imgB+'px');
												_top = ((szyFile.selectHeight-h*imgB)/2);
												_left = 0;
												$('#target').css('top',_top+'px');
												$('#target').css('left',_left+'px');
												$('#R').val(w/szyFile.selectWidth);
											}else{
												$('#target').css('width',szyFile.selectHeight*imgB1+'px');
												$('#target').css('height',szyFile.selectHeight+'px');
												_top = 0;
												_left = ((szyFile.selectWidth-szyFile.selectHeight*imgB1)/2);
												$('#target').css('top',_top+'px');
												$('#target').css('left',_left+'px');
												$('#R').val(h/szyFile.selectHeight);
											}
											szyFile.jcropInit('#target',1);
											$('.jcrop-holder').css({'position':'absolute','top':_top+'px','left':_left+'px'});
										}else{
											var imgB = szyFile.selectHeight/h;
											var imgB1 = h/w;
											if(w*imgB <= szyFile.selectWidth){
												$('#target').css('width',w*imgB+'px');
												$('#target').css('height',szyFile.selectHeight+'px');
												_top = 0;
												_left = (szyFile.selectWidth - w*imgB)/2;
												$('#target').css('top',_top+'px');
												$('#target').css('left',_left+'px');
												$('#R').val(h/szyFile.selectHeight);
											}else{
												$('#target').css('width',szyFile.selectWidth+'px');
												$('#target').css('height',szyFile.selectWidth*imgB1+'px');
												_top = (szyFile.selectHeight - szyFile.selectWidth*imgB1)/2;
												_left = 0;
												$('#target').css('top',_top+'px');
												$('#target').css('left',_left+'px');
												$('#R').val(w/szyFile.selectHeight);
											}
											szyFile.jcropInit('#target',1);
											$('.jcrop-holder').css({'position':'absolute','top':_top+'px','left':_left+'px'});
										}
									}
									img.src = this.result;
								};
							})(f);
							reader.readAsDataURL(f);
						}
					}
				}
			}
		}else{
			if(szyFile.jcrop_api){
				szyFile.jcrop_api.destroy();
				$('<div id="filterPrew" style="display:none;position:absolute;top:0;left:0;"></div>').insertAfter('#target');
			}
			$('#target').hide();
			$('#filterPrew').show();
			var src = $('#fileChange')[0].value;
			try {
                 var image = new Image();
                 image.dynsrc = src;
                 var filesize = image.fileSize;
             } catch (err) {
                 $('#fileChange')[0].select();
                 src = document.selection.createRange().text;
             }
			if(!/\.(jpg|jpeg|png)$/i.test(src)){
				alert('您上传的不是图片,请重新选择后上传！');
				return false;
			}
			$('#filterPrew').css('filter','none');
			$('#filterPrewLoad').css('filter','none');
			$('#filterPrew').css('filter', 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = "scale", src="'+src+'")');
			$('#filterPrewLoad').css('filter', 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = "image", src="'+src+'")');
			var w = $('#filterPrewLoad').width();
			var h = $('#filterPrewLoad').height();
			var _top = 0;
			var _left = 0;
			if(w >= h){
				var imgB = szyFile.selectWidth/w;
				var imgB1 = w/h;
				if(h*imgB <= szyFile.selectHeight){
					$('#filterPrew').css('width',szyFile.selectWidth+'px');
					$('#filterPrew').css('height',h*imgB+'px');
					_top = ((szyFile.selectHeight-h*imgB)/2);
					_left = 0;
					$('#filterPrew').css('top',_top+'px');
					$('#filterPrew').css('left',_left+'px');
					$('#R').val(w/szyFile.selectWidth);
				}else{
					$('#filterPrew').css('width',szyFile.selectHeight*imgB1+'px');
					$('#filterPrew').css('height',szyFile.selectHeight+'px');
					_top = 0;
					_left = ((szyFile.selectWidth-szyFile.selectHeight*imgB1)/2);
					$('#filterPrew').css('top',_top+'px');
					$('#filterPrew').css('left',_left+'px');
					$('#R').val(h/szyFile.selectHeight);
				}
				szyFile.jcropInit('#filterPrew',0);
				$('.jcrop-holder').css({'position':'absolute','top':_top+'px','left':_left+'px'});
				$('.jcrop-tracker').css({'filter':'alpha(opacity=30); BACKGROUND-COLOR: white'});
			}else{
				var imgB = szyFile.selectHeight/h;
				var imgB1 = h/w;
				if(w*imgB <= szyFile.selectWidth){
					$('#filterPrew').css('width',w*imgB+'px');
					$('#filterPrew').css('height',szyFile.selectHeight+'px');
					_top = 0;
					_left = (szyFile.selectWidth - w*imgB)/2;
					$('#filterPrew').css('top',_top+'px');
					$('#filterPrew').css('left',_left+'px');
					$('#R').val(h/szyFile.selectHeight);
				}else{
					$('#filterPrew').css('width',szyFile.selectWidth+'px');
					$('#filterPrew').css('height',szyFile.selectWidth*imgB1+'px');
					_top = (szyFile.selectHeight - szyFile.selectWidth*imgB1)/2;
					_left = 0;
					$('#filterPrew').css('top',_top+'px');
					$('#filterPrew').css('left',_left+'px');
					$('#R').val(w/szyFile.selectHeight);
				}
				szyFile.jcropInit('#filterPrew',0);
				$('.jcrop-holder').css({'position':'absolute','top':_top+'px','left':_left+'px'});
				$('.jcrop-tracker').css({'filter':'alpha(opacity=30); BACKGROUND-COLOR: white'});
			}
		}
    },
    fileOnchange:function(elm){
		if(elm.addEventListener){
			 elm.addEventListener('change', this.select, false);
		}else{
			 elm.onchange = function(){
				szyFile.select();
			 }
		}
    },
    fileValue:function(){
        return this.fileDom.value;
    },
    init:function(dom,prev){
        if(dom) this.fileDom = dom;
        if(prev) this.preview = prev;
        this.fileOnchange(dom);
    }
}