$(document).ready(function() {
	$("button").click(function() {
		if ($(this).attr('class') === 'enBtn') {
			changeLocale('en');
		} else {
			changeLocale('sr');
		}
	});
});

$(document).ready(function() {
	$("div").on('click', '.hamburger', function() {
		if ($(this).attr('id') === 'closedMenu') {
			hamburgerMenu('closedMenu');
		} else {
			hamburgerMenu('openedMenu');
		}
	});
});

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

function hamburgerMenu(menuStatus) {
	var menuIconClosed = document.getElementById('closedMenu');
	var menuIconOpened = document.getElementById('openedMenu');
	var menuContent = document.getElementsByClassName('menuContent')[0];

	if (menuStatus === 'closedMenu') {
		menuIconClosed.style.display = 'none';
		menuIconOpened.style.display = 'block';
		menuContent.classList.remove('hideMenu');
		menuContent.classList.add('showMenu');
	} else {
		menuIconClosed.style.display = 'block';
		menuIconOpened.style.display = 'none';
		menuContent.classList.remove('showMenu');
		menuContent.classList.add('hideMenu');
	}
}

