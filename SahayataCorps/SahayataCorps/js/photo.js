'use strict';
(function() {
	var video = document.getElementById('video'),
		canvas = document.getElementById('canvas'),
		context = canvas.getContext('2d'),
		photo = document.getElementById('photo'),
		imgg = document.getElementById('imgg'),
		send = document.getElementById('send'),
		videoSelect = document.querySelector('select#videoSource');
		

	
	navigator.getMedia = navigator.webkitGetUserMedia ||
						 navigator.mozGetUserMedia ||
						 navigator.msGetUserMedia ||
						 navigator.getUserMedia;


navigator.mediaDevices.enumerateDevices()
  .then(gotDevices).then(getStream).catch(handleError);


videoSelect.onchange = getStream;

function gotDevices(deviceInfos) {
  for (var i = 0; i !== deviceInfos.length; ++i) {
    var deviceInfo = deviceInfos[i];
    var option = document.createElement('option');
    option.value = deviceInfo.deviceId;
    if (deviceInfo.kind === 'videoinput') {
      option.text = deviceInfo.label || 'camera ' +
        (videoSelect.length + 1);
      videoSelect.appendChild(option);
      videoSelect.value = option.value;
    } else {
      console.log('Found one other kind of source/device: ', deviceInfo);
    }
  }
}

function getStream() {
  if (window.stream) {
    window.stream.getTracks().forEach(function(track) {
      track.stop();
    });
  }

  var constraints = {
    
    video: {
      deviceId: {exact: videoSelect.value}
    }
  };

  navigator.mediaDevices.getUserMedia(constraints).
    then(gotStream).catch(handleError);
    navigator.mediaDevices.webkitGetUserMedia(constraints).
    then(gotStream).catch(handleError);
    
    
}

function gotStream(stream) {
       window.stream = stream;
       video.srcObject = stream;
}

function handleError(error) {
  console.log('Error: ', error);
}




	document.getElementById('capture').addEventListener('click',function(){
		context.drawImage(video,0,0,256,256);
		photo.setAttribute('src', canvas.toDataURL('image/png'));
		imgg.setAttribute('value',canvas.toDataURL('image/png'));
	});

})();