#include "ImageProcessor.h"

ImageProcessor::ImageProcessor() {

}

ImageProcessor::~ImageProcessor() {

}


std::string ImageProcessor::decodeHiddenMessage(const ImageMatrix& img) 
{
	ImageSharpening Sharpener;
	ImageMatrix SharpenedImage = Sharpener.sharpen(img, 2);
	EdgeDetector EdgeFinder;
	std::vector<std::pair<int, int>> AllEdges = EdgeFinder.detectEdges(SharpenedImage);
	DecodeMessage Decoder;
	std::string DecodedMessage = Decoder.decodeFromImage(SharpenedImage, AllEdges);
	return DecodedMessage;

}

ImageMatrix ImageProcessor::encodeHiddenMessage(const ImageMatrix& img, const std::string& message)
{
	ImageSharpening Sharpener;
	ImageMatrix SharpenedImage = Sharpener.sharpen(img, 2);
	EdgeDetector EdgeFinder;
	std::vector<std::pair<int, int>> AllEdges = EdgeFinder.detectEdges(SharpenedImage);
	EncodeMessage Encoder;
	ImageMatrix EncodedImage = Encoder.encodeMessageToImage(img, message, AllEdges);
	return EncodedImage;
}
