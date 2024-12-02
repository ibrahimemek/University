#include <iostream>

#include "Convolution.h"

// Default constructor 
Convolution::Convolution() 
	:KernelHeight{0}, KernelWidth{0}
{
}

// Parametrized constructor for custom kernel and other parameters
Convolution::Convolution(double** customKernel, int kh, int kw, int stride_val, bool pad) 
    :KernelHeight{ kh }, KernelWidth{ kw }, StrideValue{stride_val}, bShouldPad{pad}
{

    Kernel = new double* [KernelHeight];
    for (int h = 0; h < KernelHeight; h++)
    {
        Kernel[h] = new double[KernelWidth];
        for (int w = 0; w < KernelWidth; w++)
        {
            Kernel[h][w] = customKernel[h][w];
        }
    }

    

}
// Destructor
Convolution::~Convolution() 
{
    if (Kernel != 0)
    {
        for (int h = 0; h < KernelHeight; h++)
        {
            delete[] Kernel[h];
        }

        delete[] Kernel;
    }
}

// Copy constructor
Convolution::Convolution(const Convolution& other) 
    :Convolution(other.Kernel, other.KernelHeight, other.KernelWidth, other.StrideValue, other.bShouldPad)
{

}

// Copy assignment operator
Convolution& Convolution::operator=(const Convolution& other) 
{
    if (this == &other)
        return *this;


    if (Kernel != 0)
    {
        for (int h = 0; h < KernelHeight; h++)
        {
            delete[] Kernel[h];
        }

        delete[] Kernel;
    }

    KernelHeight = other.KernelHeight;
    KernelWidth = other.KernelWidth;
    StrideValue = other.StrideValue;
    bShouldPad = other.bShouldPad;

    Kernel = new double* [KernelHeight];
    for (int h = 0; h < KernelHeight; h++)
    {
        Kernel[h] = new double[KernelWidth];
        for (int w = 0; w < KernelWidth; w++)
        {
            Kernel[h][w] = other.Kernel[h][w];
        }

    }

    return *this;
}


// Convolve Function: Responsible for convolving the input image with a kernel and return the convolved image.
ImageMatrix Convolution::convolve(const ImageMatrix& input_image) const 
{
    
    ImageMatrix PaddedMatrix;
    if (bShouldPad)
    {
        PaddedMatrix = ImageMatrix(input_image.get_height() + 2, input_image.get_width() + 2);
        for (int h = 0; h < PaddedMatrix.get_height(); h++)
        {
            for (int w = 0; w < PaddedMatrix.get_width(); w++)
            {
                if (h == 0 || h == PaddedMatrix.get_height() - 1 || w == 0 || w == PaddedMatrix.get_width() - 1)
                {
                    PaddedMatrix.get_data()[h][w] = 0.0;
                }
                else
                {
                    PaddedMatrix.get_data()[h][w] = input_image.get_data()[h - 1][w - 1];
                }
            }
        }
    }
    else
    {
        PaddedMatrix = input_image;
    }

    int ConvolvedHeight = (input_image.get_height() - KernelHeight + 2 * bShouldPad) / StrideValue + 1;
    int ConvolvedWidth = (input_image.get_height() - KernelWidth + 2 * bShouldPad) / StrideValue + 1;
    ImageMatrix ConvolvedMatrix(ConvolvedHeight, ConvolvedWidth);

    for (int h = 0, ConvolvedAtH = 0; h < PaddedMatrix.get_height(); h += StrideValue, ConvolvedAtH++)
    {
        for (int w = 0, ConvolvedAtW = 0; w < PaddedMatrix.get_width(); w += StrideValue, ConvolvedAtW++)
        {
            if (h + KernelHeight > PaddedMatrix.get_height() || w + KernelWidth > PaddedMatrix.get_width()) continue;
            double CurrentSum = 0.0;
            for (int kh = 0; kh < KernelHeight; kh++)
            {
                for (int kw = 0; kw < KernelWidth; kw ++)
                {
                    CurrentSum += PaddedMatrix.get_data()[h + kh][w + kw] * Kernel[kh][kw];
                }
            }
            ConvolvedMatrix.get_data()[ConvolvedAtH][ConvolvedAtW] = CurrentSum;

        }
    }
    
    return ConvolvedMatrix;
}
