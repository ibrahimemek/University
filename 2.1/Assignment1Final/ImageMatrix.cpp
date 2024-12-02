#include "ImageMatrix.h"
#include <iostream>
#include <fstream>

// Default constructor
ImageMatrix::ImageMatrix()
    :height{0}, width{0}
{
    
}


// Parameterized constructor for creating a blank image of given size
ImageMatrix::ImageMatrix(int imgHeight, int imgWidth) 
    : height{ imgHeight }, width{ imgWidth }
{
    data = new double* [height];
    for (int h = 0; h < height; h++)
    {
        data[h] = new double[width];
        for (int w = 0; w < width; w++)
        {
            data[h][w] = 0.0;
        }
    }
}

// Parameterized constructor for loading image from file. PROVIDED FOR YOUR CONVENIENCE
ImageMatrix::ImageMatrix(const std::string& filepath) {
    // Create an ImageLoader object and load the image
    ImageLoader imageLoader(filepath);

    // Get the dimensions of the loaded image
    height = imageLoader.getHeight();
    width = imageLoader.getWidth();

    // Allocate memory for the matrix
    data = new double* [height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
    }

    // Copy data from imageLoader to data
    double** imageData = imageLoader.getImageData();
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; j++) {
            data[i][j] = imageData[i][j];
        }
    }
}



// Destructor
ImageMatrix::~ImageMatrix() 
{
    if (data != 0)
    {
        for (int h = 0; h < height; h++)
        {
            delete[] data[h];
        }

        delete[] data;
    }
}

// Parameterized constructor - direct initialization with 2D matrix
ImageMatrix::ImageMatrix(const double** inputMatrix, int imgHeight, int imgWidth) 
    :height{imgHeight}, width{imgWidth}
{
   
    data = new double* [height];
    for (int h = 0; h < height; h++)
    {
        data[h] = new double[width];
        for (int w = 0; w < width; w++)
        {
            data[h][w] = inputMatrix[h][w];
        }
    }

}

ImageMatrix::ImageMatrix(double** inputMatrix, int imgHeight, int imgWidth)
    :height{ imgHeight }, width{ imgWidth }
{

    data = new double* [height];
    for (int h = 0; h < height; h++)
    {
        data[h] = new double[width];
        for (int w = 0; w < width; w++)
        {
            data[h][w] = inputMatrix[h][w];
        }
    }


}

void ImageMatrix::Clip()
{
    for (int h = 0; h < get_height(); h++)
    {
        for (int w = 0; w < get_width(); w++)
        {
            if (data[h][w] > 255.0) data[h][w] = 255.0;
            if (data[h][w] < 0.0) data[h][w] = 0.0;
        }
    }
}

// Copy constructor
ImageMatrix::ImageMatrix(const ImageMatrix& other) 
    :ImageMatrix(other.data, other.get_height(), other.get_width())
{

}

// Copy assignment operator
ImageMatrix& ImageMatrix::operator=(const ImageMatrix& other) 
{
    if (this == &other)
        return *this;


    if (data != nullptr)
    {
        for (int h = 0; h < height; h++)
        {
            delete[] data[h];
        }

        delete[] data;
    }

    height = other.height;
    width = other.width;
    data = new double* [height];
    for (int h = 0; h < height; h++)
    {
        data[h] = new double[width];
        for (int w = 0; w < width; w++)
        {
            data[h][w] = other.data[h][w];
        }

    }

    return *this;

}



// Overloaded operators

// Overloaded operator + to add two matrices
ImageMatrix ImageMatrix::operator+(const ImageMatrix& other) const 
{
    ImageMatrix TempMatrix(other.get_height(), other.get_width());
    for (int h = 0; h < height; h++)
    {
        for (int w = 0; w < width; w++)
        {
            TempMatrix.data[h][w] = data[h][w] + other.get_data()[h][w];
        }
    }

    return TempMatrix;
}

// Overloaded operator - to subtract two matrices
ImageMatrix ImageMatrix::operator-(const ImageMatrix& other) const 
{
    ImageMatrix TempMatrix(other.get_height(), other.get_width());
    for (int h = 0; h < height; h++)
    {
        for (int w = 0; w < width; w++)
        {
            TempMatrix.data[h][w] = data[h][w] - other.get_data()[h][w];
        }
    }

    return TempMatrix;
}

// Overloaded operator * to multiply a matrix with a scalar
ImageMatrix ImageMatrix::operator*(const double& scalar) const 
{
    ImageMatrix TempMatrix(height, width);
    for (int h = 0; h < height; h++)
    {
        for (int w = 0; w < width; w++)
        {
            TempMatrix.data[h][w] = data[h][w] * scalar;
        }
    }
    return TempMatrix;
}


// Getter function to access the data in the matrix
double** ImageMatrix::get_data() const 
{
    return data;
}

// Getter function to access the data at the index (i, j)
double ImageMatrix::get_data(int i, int j) const 
{
    return data[i][j];
}

int ImageMatrix::get_height() const
{
    return height;
}

int ImageMatrix::get_width() const
{
    return width;
}

