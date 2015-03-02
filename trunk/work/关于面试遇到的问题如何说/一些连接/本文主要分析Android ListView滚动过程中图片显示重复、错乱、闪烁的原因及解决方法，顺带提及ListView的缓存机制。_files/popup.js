var _amzn_popup = {
	vertical_offset : '-3px',
	horizontal_offset : '-3px',
	start_delay : 500,
	end_delay : 500,
    clientX: 0,
    clientY: 0,
    onshow_callback: null,
    onhide_callback: null,
    eventObject:null,
    init_done:false,
    paramsForLogging: null,
    doNothing: null,
    
    init: function(){
        if(!this.init_done){
            var amzn_popup_div = document.createElement('div');
            amzn_popup_div.id = 'amzn_popup_div';
            document.body.appendChild(amzn_popup_div);
            amzn_popup_div.onmouseover = function() {
                if (_amzn_popup) {
                    return _amzn_popup._continue();
                }
            }

            amzn_popup_div.onmouseout = function() {
                if (_amzn_popup) {
                    return _amzn_popup._stop();
                }
            }
            this.init_done=true;
        }
    },

    showpreview : function(innerHTML, obj, event, endDelay, tag, linkCode,  marketPlace, asin){
        if (typeof tag == 'undefined') { tag = null; }
        if (typeof linkCode == 'undefined') { linkCode = null; }
      

        if (!tag && !linkCode ) {
            this.paramsForLogging = null;
        } else {
            this.paramsForLogging = new Object();
            this.paramsForLogging.tag = tag;
            this.paramsForLogging.linkCode = linkCode;
            this.paramsForLogging.marketPlace = marketPlace;
            this.paramsForLogging.asin = asin;
        }

        if(!this.init_done){
            return;
        }
        if(this.eventObject==obj){
            this._clear_tip();
            return;// do nothing if source is the same;
        }
	var mouseX_Y = this.getMouseX_Y(event);
        this.clientX = mouseX_Y[0];
        this.clientY = mouseX_Y[1];
        var delay=this.end_delay;
        if(typeof endDelay != "undefined" || endDelay == null){
            delay=endDelay;
        }
        this.show(unescape(innerHTML), obj, event, delay);
    },   

	show : function(content, obj, e, delay) {
	        if(!this.init_done){
	            return;
	        }
        	if (window.event) { 
			event.cancelBubble=true;
		} else if (e.stopPropagation) {
			e.stopPropagation();
		}
		if(typeof delay == "undefined"){
			delay=this.end_delay;
		}
		this._do_hide();
		var tooltip = document.getElementById('amzn_popup_div');
		tooltip.innerHTML = content;
		if ((e.type == 'click' && tooltip.style.visibility == 'hidden') || e.type == 'mouseover') {
			var self = this;
			this._show_handler = setTimeout(function() {self._do_show();}, delay);
		} else if (e.type=='click') {
			tooltip.style.visibility = 'hidden';
		}
		tooltip.x = this._get_pos_offset(obj, true);
		tooltip.y = this._get_pos_offset(obj, false);
		var pos = this._clear_browser_edge(obj);
        	tooltip.style.left = pos[0] + 'px';
        	tooltip.style.top = pos[1] + 'px';
	        this.eventObject=obj;
		return true;
	},

    hide : function(obj) {
        if(!this.init_done){
            return;
        }
        this._must_hide = true;
        var self = this;
        var different_parent=false;
        if(obj && this.eventObject!=obj){
            different_parent=true;
        }

        if(this._am_visible && !different_parent ){
            this._clear_tip();
            this._hide_handler = setTimeout(function() { self._do_hide(); }, this.start_delay);
        }else{
            this._do_hide();// if I am not open - hide myself immediately - so that there is no flicker
        }
        return true;
    },
	
	hideNow : function() {
        if(!this.init_done){
            return;
        }
        this._must_hide = true;
		var self = this;
 		this._hide_handler = setTimeout(function() { self._do_hide(); }, 0);
 		return true;
	},

	//---Private
	_show_handler : null,
	_hide_handler : null,
	_must_hide : true,
	_am_visible : false,

	_clear_tip : function() {
		if (typeof this._hide_handler != 'undefined' || this._hide_handler !== null) {
			clearTimeout(this._hide_handler);
			delete(this._hide_handler);
		}
		if (typeof this._show_handler != 'undefined' || this._show_handler !== null) {
			clearTimeout(this._show_handler);
			delete(this._show_handler);
		}
	},
	
	_get_pos_offset : function(what, is_left) {
		var total_offset = (is_left) ? what.offsetLeft : what.offsetTop;
		var parentEl = what.offsetParent;
		while (parentEl !== null) {
			total_offset = (is_left) ? total_offset + parentEl.offsetLeft : total_offset + parentEl.offsetTop;
			parentEl = parentEl.offsetParent;
		}
		return total_offset;
	},
	
	_clear_browser_edge : function(obj) {
                var tooltip = document.getElementById('amzn_popup_div');
                var edge_offset = parseInt(this.horizontal_offset, 10)*-1;
                var is_ie = document.all && !window.opera;
                var window_edge, content_measure, edge_offsetX, edge_offsetY;
                if (is_ie) {
                        var ie_body = this.ie_body();
                }
                window_edge = is_ie ? ie_body.scrollLeft + ie_body.clientWidth-15 : window.pageXOffset+window.innerWidth-15;
                content_measure = tooltip.offsetWidth;
                var siteStripePreviewDiv = document.getElementById("preview_eys_div");
                if (window_edge - tooltip.x < content_measure) {
                        edge_offset= content_measure - obj.offsetWidth;
                }
                if(siteStripePreviewDiv && siteStripePreviewDiv != null){
                    edge_offset-=siteStripePreviewDiv.scrollLeft;
                }
                if(siteStripePreviewDiv && siteStripePreviewDiv != null){
                    edge_offset-=siteStripePreviewDiv.scrollTop;
                }
                var objX = tooltip.x % window_edge;
                if(objX - edge_offset + content_measure > window_edge) {
                        edge_offset = objX - (window_edge - content_measure - 10);
                }
                edge_offsetX = tooltip.x - edge_offset;
                edge_offset = parseInt(this.vertical_offset, 10)*-1 - obj.offsetHeight;
                window_edge = is_ie ? ie_body.scrollTop + ie_body.clientHeight - 15 : window.pageYOffset + window.innerHeight - 18;
                content_measure= tooltip.offsetHeight;
                var isDisplayedAbove = false;
                if ((window_edge - tooltip.y < content_measure) || (this.clientY + content_measure > window_edge)) {
                        edge_offset = content_measure + 5;
                        isDisplayedAbove = true;
                }
                edge_offsetY = tooltip.y - edge_offset;
                if(this.clientX && this.clientY){
                        if(!isDisplayedAbove && this.clientY >= edge_offsetY){
                                edge_offsetY =  this.clientY + obj.offsetHeight*2/3;
                        }
                                                                                                                                                             
                }
                                                                                                                                                             
                return [edge_offsetX, edge_offsetY];
        },
        getMouseX_Y : function(e){
        	var x = 0;
	        var y = 0;
	        if (!e) var e = window.event;
	        if (e.pageX || e.pageY)         {
	                x = e.pageX;
	                y = e.pageY;
	        }
	        else if (e.clientX || e.clientY)        {
	                x = e.clientX + document.body.scrollLeft
	                        + document.documentElement.scrollLeft;
	                y = e.clientY + document.body.scrollTop
	                        + document.documentElement.scrollTop;
	        }
	        return [x,y];
        },

	ie_body : function() {
 		return (document.compatMode && document.compatMode!='BackCompat') ? document.documentElement : document.body;
	 },

 	_do_show : function() {
	 	document.getElementById('amzn_popup_div').style.visibility='visible';
         this._am_visible=true;
         if (this.paramsForLogging != null) {
             _amzn_utils.recordPopover(this.paramsForLogging.tag, this.paramsForLogging.linkCode,  this.paramsForLogging.marketPlace, this.paramsForLogging.asin);
         }
         if(this.onshow_callback!=null && typeof this.onshow_callback == "function"){
             this.onshow_callback();
         }
 	},
 	
 	_do_hide : function() {
 		if (this._must_hide) {
			document.getElementById('amzn_popup_div').style.visibility='hidden';
             if(this.onhide_callback!=null && typeof this.onhide_callback == "function" && this._am_visible){
                 this.onhide_callback();
             }
             this._am_visible=false;
             this.eventObject=null;
 		}
		this._clear_tip();
 	},
 	
 	_continue : function() {
 		this._must_hide = false;
 	},
 	
 	_stop : function() {
 		this._must_hide = true;
 		this.hide();
 	}
};

