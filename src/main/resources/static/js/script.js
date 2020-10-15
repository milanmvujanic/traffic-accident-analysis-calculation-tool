function changeLocale(localeData) {
	var url = location.href;
	var logoutSuccessIndex = url.indexOf('?success');
	var localeDataIndex = url.indexOf('?localeData=');

	if (localeDataIndex != -1) {
		localeDataIndex += '?localeData='.length;
		var toReplace = url.substr(localeDataIndex, 2);
		url = url.replace(toReplace, localeData);
	} else {
		if (logoutSuccessIndex != -1) {
			url = url.replace('?success', '');
		}
		url = url + '?localeData=' + localeData;
	}

	window.location.href = url;
}