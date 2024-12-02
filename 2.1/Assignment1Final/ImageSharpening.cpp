#include "ImageSharpening.h"

// Default constructor
ImageSharpening::ImageSharpening() 
{
	CustomKernel = new double* [3]
	{
		new double[3] {1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0},
		new double[3] {1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0},
		new double[3] {1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0}
	};

}

ImageSharpening::~ImageSharpening() 
{
	for (int h = 0; h < 3; h++)
	{
		delete[] CustomKernel[h];
	}
	delete[] CustomKernel;

}

ImageMatrix ImageSharpening::sharpen(const ImageMatrix& input_image, double k) 
{
	Convolution Convolutor(CustomKernel, 3, 3, 1, true);
	ImageMatrix BlurredImage = Convolutor.convolve(input_image);
	ImageMatrix SharpenedImage = input_image + (input_image - BlurredImage) * k;
	SharpenedImage.Clip();
	return SharpenedImage;
}
