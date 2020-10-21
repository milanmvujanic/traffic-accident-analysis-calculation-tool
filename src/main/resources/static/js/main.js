function changeLocale(localeData) {
	var url = location.href;
	var logoutSuccessIndex = url.indexOf('?success');
	var loginErrorIndex = url.indexOf('?error');
	var localeDataIndex = url.indexOf('?localeData=');
	var appendLocaleIndex;

	if (localeDataIndex != -1) {
		appendLocaleIndex = localeDataIndex + '?localeData='.length;
		url = url.substring(0, appendLocaleIndex) + localeData;
	} else {
		if (logoutSuccessIndex != -1) {
			url = url.replace('?success', '');
		}
		if (loginErrorIndex != -1) {
			url = url.replace('?error', '');
		}
		url = url + '?localeData=' + localeData;
	}

	window.location.href = url;
}
