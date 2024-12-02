// EdgeDetector.cpp

#include "EdgeDetector.h"
#include <cmath>

#include "EdgeDetector.h"
#include <cmath>
#include <iostream>

// Default constructor
EdgeDetector::EdgeDetector() 
{
	KernelGx = new double* [3]
	{
		new double[3] {-1, 0, 1},
		new double[3] {-2, 0, 2},
		new double[3] {-1, 0, 1}
	};
	KernelGy = new double* [3]
	{
		new double[3] {-1, -2, -1},
		new double[3] {0, 0, 0},
		new double[3] {1, 2, 1}
	};

}

// Destructor
EdgeDetector::~EdgeDetector() 
{
	for (int i = 0; i < 3; i++)
	{
		delete[] KernelGx[i];
		delete[] KernelGy[i];
	}

	delete[] KernelGx;
	delete[] KernelGy;
	
}

// Detect Edges using the given algorithm
std::vector<std::pair<int, int>> EdgeDetector::detectEdges(const ImageMatrix& input_image) 
{
	Convolution ConvX(KernelGx, 3, 3, 1, true);
	ImageMatrix NewImageX = ConvX.convolve(input_image);

	Convolution ConvY(KernelGy, 3, 3, 1, true);
	ImageMatrix NewImageY = ConvY.convolve(input_image);

	ImageMatrix GradientMatrix(input_image.get_height(), input_image.get_width());
	double AllGradientSum = 0.0;
	for (int h = 0; h < input_image.get_height(); h++)
	{
		for (int w = 0; w < input_image.get_width(); w++)
		{

			double CurrentGradient = sqrt(pow(NewImageX.get_data()[h][w], 2) + pow(NewImageY.get_data()[h][w], 2));
			GradientMatrix.get_data()[h][w] = CurrentGradient;
			AllGradientSum += CurrentGradient;
		}
	}
	
	double AverageGradient = AllGradientSum / (input_image.get_height() * input_image.get_width());

	std::vector<std::pair<int, int>> Edges;
	for (int h = 0; h < input_image.get_height(); h++)
	{
		for (int w = 0; w < input_image.get_width(); w++)
		{
			if (GradientMatrix.get_data()[h][w] > AverageGradient)
			{
				Edges.push_back(std::make_pair(h, w));
			}
		}
	}


	return Edges;
}

