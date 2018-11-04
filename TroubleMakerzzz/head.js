$( document ).ready( function() {

$('body').noisy({
    intensity: 0.2,
    size: 200,
    opacity: 0.28,
    randomColors: false, // true by default
    color: '#000000'
});

	//Google Maps JS
	//Set Map
	function initialize() {
			var myLatlng = new google.maps.LatLng(53.3333,-3.08333);
			var imagePath = 'http://m.schuepfen.ch/icons/helveticons/black/60/Pin-location.png'
			var mapOptions = {
				zoom: 11,
				center: myLatlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			}

		var map = new google.maps.Map(document.getElementById('map'), mapOptions);
		//Callout Content
		var contentString = 'Some address here..';
		//Set window width + content
		var infowindow = new google.maps.InfoWindow({
			content: contentString,
			maxWidth: 500
		});

		//Add Marker
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map,
			icon: imagePath,
			title: 'image title'
		});

		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open(map,marker);
		});

		//Resize Function
		google.maps.event.addDomListener(window, "resize", function() {
			var center = map.getCenter();
			google.maps.event.trigger(map, "resize");
			map.setCenter(center);
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);

});





<script>
window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader");
var sticky = header.offsetTop;

function myFunction() {
  if (window.pageYOffset > sticky) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}
</script>















function demoAnimation() {
	var targetElements = [].slice.call(document.getElementsByClassName('wgh-slider-target'));
	var targetInputMode = targetElements[0].nodeName.toLowerCase() === 'input' && targetElements[0].type === 'radio';
	var targetIds = targetElements.map(function (element) {return element.getAttribute('id');});
	var targetIndex = 0;

	var triggerElements = [].slice.call(document.getElementsByClassName('wgh-slider-item__trigger'));
	var timeoutId = void 0;

	function changeSlide() {
		triggerElements[++targetIndex].click();
		if (targetIndex < Math.floor(targetElements.length / 2)) setTimeout(changeSlide, 1200);
	}

	changeSlide();
}

if (document.location.pathname.indexOf('fullcpgrid') > -1) {
	demoAnimation();
}

if (document.getElementsByClassName('wgh-slider-target')[0].nodeName.toLowerCase() !== 'input') {
	var resetBtn = document.createElement('button');
	resetBtn.type = 'button';
	resetBtn.textContent = 'Reset Hash URL';

	resetBtn.onclick = function () {
		document.location.hash = '';
	};

	document.body.insertBefore(resetBtn, document.body.firstChild);
}
