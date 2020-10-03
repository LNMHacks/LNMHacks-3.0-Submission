#include <opencv\cv.h>
#include <opencv\highgui.h>
#include <Windows.h>
#include <mmsystem.h>

IplImage* imgTracking;
int lastX = -1;
int lastY = -1;
int soundcheck = 0;


IplImage* GetThresholdedImage(IplImage* imgHSV){
	IplImage* imgThresh = cvCreateImage(cvGetSize(imgHSV), IPL_DEPTH_8U, 1);
	cvInRangeS(imgHSV, cvScalar(68, 127, 98), cvScalar(142, 256, 256), imgThresh);
	return imgThresh;
}

void trackingFunction(int posX, int posY, double area){
	if (posX > 300 && posX < 380 && posY > 5 && posY < 60 && soundcheck == 0)
	{
		std::cout << "START\n";
		ShellExecute(NULL, L"open", L"C:\\yourfilename.pptx", NULL, NULL, SW_SHOWNORMAL);
		soundcheck = 1;
	}

	if (posX < 150 && posY > 20 && soundcheck == 0)
	{
		std::cout << "Next\n";
		keybd_event(VK_RIGHT, 0x27, 0, 0);
		soundcheck = 1;
	}

	if (posX > 450 && posY > 20 && soundcheck == 0)
	{
		std::cout << "Previous\n";
		keybd_event(VK_LEFT, 0x25, 0, 0);
		soundcheck = 1;
	}

	if (area>5000 && soundcheck == 0)
	{
		std::cout << "Fullscreen\n";
		keybd_event(VK_F5, 0x74, 0, 0);
		soundcheck = 1;
	}

	if (posX < 450 && posX > 150 && posY > 50 && soundcheck == 1)
	{
		soundcheck = 0;
	}

}

void trackObject(IplImage* imgThresh){
	CvMoments *moments = (CvMoments*)malloc(sizeof(CvMoments));
	cvMoments(imgThresh, moments, 1);
	double moment10 = cvGetSpatialMoment(moments, 1, 0);
	double moment01 = cvGetSpatialMoment(moments, 0, 1);
	double area = cvGetCentralMoment(moments, 0, 0);
 
	if (area > 200){
		
		int posX = moment10 / area;
		int posY = moment01 / area;

		if (lastX >= 0 && lastY >= 0 && posX >= 0 && posY >= 0)
		{
			cvLine(imgTracking, cvPoint(posX, posY), cvPoint(lastX, lastY), cvScalar(0, 0, 255), 4);
		}

		if (posX != lastX || posY != lastY)
		{
			std::cout << "x = " << posX << "\n";
			std::cout << "y = " << posY << "\n";
		}

		trackingFunction(posX, posY, area);

		lastX = posX;
		lastY = posY;
	}

	free(moments);
}

using namespace cv;

int main(){

	Mat final;
	VideoCapture cap;
	cap.open(0);
	namedWindow("final", CV_WINDOW_AUTOSIZE);

	CvCapture* capture = 0;
	capture = cvCaptureFromCAM(0);
	if (!capture){
		printf("Capture failure\n");
		return -1;
	}

	IplImage* frame = 0;
	frame = cvQueryFrame(capture);
	if (!frame) return -1;

	imgTracking = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 3);
	cvZero(imgTracking);
	cvNamedWindow("Video");
	cvNamedWindow("Ball");
	while (true){

		cap >> final;
		flip(final, final, 1);
		putText(final, "Open the Slides", Point(220, 30), CV_FONT_HERSHEY_DUPLEX, 0.8, Scalar(200, 250, 250), 1, CV_AA);
		imshow("final", final);

		frame = cvQueryFrame(capture);
		if (!frame) break;
		frame = cvCloneImage(frame);

		cvSmooth(frame, frame, CV_GAUSSIAN, 3, 3);

		IplImage* imgHSV = cvCreateImage(cvGetSize(frame), IPL_DEPTH_8U, 3);
		cvCvtColor(frame, imgHSV, CV_BGR2HSV); 
		IplImage* imgThresh = GetThresholdedImage(imgHSV);

		cvSmooth(imgThresh, imgThresh, CV_GAUSSIAN, 3, 3);
		trackObject(imgThresh);

		cvAdd(frame, imgTracking, frame);

		cvShowImage("Ball", imgThresh);
		cvShowImage("Video", frame);

		cvReleaseImage(&imgHSV);
		cvReleaseImage(&imgThresh);
		cvReleaseImage(&frame);

		
		int c = cvWaitKey(10);

		if ((char)c == 27) break;
	}

	cvDestroyAllWindows();
	cvReleaseImage(&imgTracking);
	cvReleaseCapture(&capture);

	return 0;
}