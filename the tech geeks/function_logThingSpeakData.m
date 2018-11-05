%% People Counter Demo
% Automatically detects and tracks multiple faces in a webcam-acquired
% video stream.
%
% Copyright 2016 The MathWorks, Inc
% Clear everything, create a counter to keep track of the number of
% faces and create a flag to determine whether a special message has been displayed or not. 
clc ;
clear all;
close all;
delete(timerfindall)
count = 0;
imshown = 0;
% %% Check the latest value for total number of faces
  [initialNum,latestDate] = thingSpeakRead(616958,'ReadKey','L5JCCJB06DI9KABX');
     latestDate= '03-Nov-2018';
%   if dateshift(latestDate,'start','day') < datetime('today','TimeZone','local')
%   logThingSpeakData('reset')
%   end
%% Initialize objects for the video device, face detector, and KLT object tracker.
%  Make sure the tracker does not overwrite old entries if there is already
%  data in the channel.
vidObj = webcam('Integrated Webcam','Resolution','640x480');
faceDetector = vision.CascadeObjectDetector('MaxSize',[150 150]);%'MinSize',[50 45]); % Finds faces by default
tracker = MultiObjectTrackerKLT;
if ~isempty(initialNum)
    tracker.NextId  = initialNum(1) + 1;
end
%% Get a frame for frame-size information
frame = snapshot(vidObj);
frame = fliplr(frame);
frameSize = size(frame);
%% Create a video player instance
videoPlayer  = vision.VideoPlayer('Position',[200 100 fliplr(frameSize(1:2)+30)]);
%% Create a timer for logging data
tim = timer;
tim.ExecutionMode = 'FixedRate';
tim.Period = 20;
tim.TimerFcn = @(x,y) logThingSpeakData(tracker);
tim.StartDelay = 5;
%% Add a close button to the figure
fig = findall(groot,'Tag','spcui_scope_framework');
fig = fig(1); %In case there are multiple
setappdata(fig,'RequestedClose',false)
fig.CloseRequestFcn = @(~,~) setappdata(fig,'RequestedClose',true);
%% Iterate until we have successfully detected a face
bboxes = [];
while isempty(bboxes)
    framergb = snapshot(vidObj);
    frame = rgb2gray(framergb);
    bboxes = faceDetector.step(frame);
end
tracker.addDetections(frame, bboxes);
%% Loop through the main code until the player is closed
frameNumber = 0;
disp('Close the video player to exit');
start(tim)
while ~getappdata(fig,'RequestedClose')
    try
        framergb = snapshot(vidObj);
    catch
        framergb = snapshot(vidObj);
    end
    framergb = fliplr(framergb);
    frame = rgb2gray(framergb);
    
    if mod(frameNumber, 10) == 1
        % (Re)detect faces.
        % NOTE: face detection is more expensive than imresize; we can
        % speed up the implementation by reacquiring faces using a
        % downsampled frame:
        % bboxes = faceDetector.step(frame);
        bboxes = 2 * faceDetector.step(imresize(frame, 0.5));
        if ~isempty(bboxes)
            tracker.addDetections(frame, bboxes);
        end
    else
        % Track faces
        tracker.track(frame);
    end
    
    if ~isempty(tracker.Bboxes)
        % If the user is the __ user, show a bounding box around their face
        % with a special message and output a picture of the user.
        if any(mod(tracker.BoxIds,5) == 0) && imshown == 0
            count = count+1;
            displayFrame = insertObjectAnnotation(framergb, 'rectangle',...
                [tracker.Bboxes(1) tracker.Bboxes(2) 5 5] , ['You are the lucky ' num2str(count*5) 'th visitor this session :)']);
            imwrite(displayFrame,'NewFace.png');
            imshow('NewFace.png');
            imshown = 1;
            pause(5)
            close(gcf)
            delete('NewFace.png');
        else
            % If any new person enters the frame, stop showing the special
            % message and display bounding boxes and tracking points on the
            % detected faces.
            if all(mod(tracker.BoxIds,5) ~= 0)
                imshown = 0;
            end
            displayFrame = insertObjectAnnotation(framergb, 'rectangle',...
                tracker.Bboxes, tracker.BoxIds);
            displayFrame = insertMarker(displayFrame, tracker.Points);
        end
        videoPlayer.step(displayFrame);
        tracker.BoxIds;
    else
        videoPlayer.step(framergb);
    end
    
    frameNumber = frameNumber + 1;
    
end
%% Clean up
release(videoPlayer)
stop(tim)
clear vidObj
clear fig
clear videoPlayer

