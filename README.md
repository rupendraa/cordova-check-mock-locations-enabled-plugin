# cordova-check-mock-locations-enabled-plugin

Fixed (Android Marshmallow) plugin to detect if "Allow Mock Locations" is enabled on <strong><bold>Android</bold></strong> phones.


### Installation:
```
cordova plugin add https://github.com/rafirr/cordova-check-mock-locations-enabled-plugin.git --nofetch
```

### How to use:
```javascript
window.plugins.cml.check(function (result) {
  if (result.active) {
    console.log("Mock locations is enabled!");
	
	//Android API Level 23+
	if(result.appName.length>0){
		//List of app which have permission: ACCESS_MOCK_LOCATION
		console.log(result.appName.join(", "));
	}
  }
});
````
