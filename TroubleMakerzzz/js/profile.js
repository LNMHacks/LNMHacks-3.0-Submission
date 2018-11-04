/*-------------- IMPORTANT! --------------
	The JS below is for demo only.
	It is not advised to keep it in production.
	Use at your own risk!
	----------------------------------------*/

angular

	.module('wemApp', ['ngMaterial', 'ngMessages'])

	.config(function($mdThemingProvider) {

	// Add jahia blue as default 500 for blue palette
	var jahiaBlue = $mdThemingProvider.extendPalette('blue', {
		'500': '#00A0E3',
		'contrastDefaultColor': 'light'
	});

	var mfPink = $mdThemingProvider.extendPalette('pink', {
		'500': '#F21D8E',
		'contrastDefaultColor': 'light'
	});

	$mdThemingProvider.definePalette('jahia-blue', jahiaBlue);

	$mdThemingProvider.definePalette('mf-pink', mfPink);

	$mdThemingProvider.theme('wemApp')
		.primaryPalette('jahia-blue')
		.accentPalette('deep-purple')
		.warnPalette('mf-pink');

	$mdThemingProvider.setDefaultTheme('wemApp');

})


//======= Main Toolbar Controller =======//

	.controller('ToolbarCtrl', function ($scope) {

})


//======= Profile details page Controller =======//

	.controller('ProfileDetails', function($scope, $mdDialog) {

	$scope.user = {
		p1: 'Riley',
		p2: 'Blue',
		p3: 'rblue',
		p4: 'ipsum@lorem.com',
		p6: '',
		p7: '',
		p8: '',
		p9: '',
		p10: '',
		p11: '',
		p12: 28,
		p13: new Date('1989-08-08'),
		p14: '',
		p15: '',
		p16: '',
		p17: '',
		p18: '',
		site: 'All sites'
	};

	$scope.sites = ('All sites, My amazing site, Another great website').split(', ').map(function(site) {
		return {mysite: site};
	});


	$scope.toggleGroup = true;

	$scope.$watch('toggleGroup', function(){
		$scope.toggleGroupIcon = $scope.toggleGroup ? 'keyboard_arrow_up' : 'keyboard_arrow_down';
	})

})



//======= Custom JS (jQuery) =======//


// Page loader

$(window).load(function() {
	setTimeout(function(){
		$("#loadingSpinner").fadeOut('fast');
	}, 500);
});



$(document).ready(function(){


	// Toggle custom range selector

	$("#custom-range").click(function(event) {
		$(".time-shortcuts-container").hide();
		$(".custom-range-container").fadeIn(200);
	});
	$("#default-ranges").click(function(event) {
		$(".time-shortcuts-container").fadeIn(200);
		$(".custom-range-container").hide();
	});


	// Toggle Chart view on profiling card.

	$("#chart-view").click(function(event) {
		$(".chips-list-view, .profiling-card-filter").hide();
		$("#list-view, .profiling-chart-view").fadeIn(200);
		$(this).hide();
	});
	$("#list-view").click(function(event) {
		$(".chips-list-view, .profiling-card-filter, #chart-view").fadeIn(200);
		$(".profiling-chart-view").hide();
		$(this).hide();
	});


});