function CMLE() {
}

CMLE.prototype.check = function (callback) {
    cordova.exec(
        function(result) {
			var obj = JSON.parse(result);
			
			if(typeof obj.appName === "undefined"){
				obj.appName = [];	
			}
			
			callback(obj);
        }, 
        function(error) {
            console.log(error);
        },
        "CheckMockLocationsEnabled", "check", []);
};

CMLE.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.cml = new CMLE();
    return window.plugins.cml;
};

cordova.addConstructor(CMLE.install);
