function CMLE() {
}

CMLE.prototype.check = function (callback) {
    cordova.exec(
        function(result) {
            if (result == 1) {
                callback(1);
            }
	        else if (result == 2) {
		        callback(2);
	        }
            else {
                callback(0);
            }
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
