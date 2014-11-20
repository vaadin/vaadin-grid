window.com_vaadin_prototype_webcomponentwrapper_element_WebComponentWrapper = function() {
  
  window.debugConnector = this;
  
  console.log("")
	var _self = this;
	
	this.ids = {
		"0" : this.getElement()
	};
	var ids = this.ids;

	var handlers = {
		createElement : function(id, tagName) {
			ids[id] = document.createElement(tagName);
		},
		createText: function(id, text) {
			ids[id] = document.createTextNode(text);
		},
		setAttribute: function(id, name, value) {
			ids[id].setAttribute(name, value);
		},
		removeAttribute: function(id, name) {
			ids[id].removeAttribute(name);
		},
		appendChild: function(parentId, childId) {
			ids[parentId].appendChild(ids[childId]);
		},
		remove: function(id) {
			ids[id].remove();
			delete ids[id];
		},
		hookShadowDOM: function(id, idsTree) {
			var element = ids[id]
			console.log("Hooking shadow DOM: " + JSON.stringify(idsTree))
			if(element.shadowRoot) {
				var shadowDOM = element.shadowRoot.childNodes
				
				var assignIds = function(nodes, idsTree) {					
					var indexed = []
					var i = 0
					for(var prop in idsTree) {
						indexed[i++] = prop
					}
					
					for(var i = 0; i < indexed.length; i++) {
						var prop = indexed[i]
						var numeric = parseInt(prop)
						var node = nodes[i]
						ids[numeric] = node
						assignIds(node.childNodes, idsTree[prop])
					}
				}
				
				assignIds(shadowDOM, idsTree);

			}
		},
		
		"import": function(url) {
			var e = document.createElement("link");
			e.setAttribute("rel", "import");
			e.setAttribute("href", url);
			document.head.appendChild(e);
		},
		eval: function(id, script, params, callbacks) {
			callbacks.forEach(function (i) {
				var callbackId = params[i];
				params[i] = function() {
					// Convert array-like object to proper array
					var args = Array.prototype.slice.call(arguments, 0);
					_self.callback(id, callbackId, args);
				}
			});
			console.log("running " + script + " " + params);
			(new Function("e", "param", script))(ids[id], params);
		},
	}

	this.run = function(commands) {
		commands.forEach(function(command) {
		  console.log("running: " + command);
			var name = command[0]; 
			var params = command.slice(1);
			handlers[name].apply(null, params);
		});
	}
}