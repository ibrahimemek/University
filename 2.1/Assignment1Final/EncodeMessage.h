#ifndef ENCODE_MESSAGE_H
#define ENCODE_MESSAGE_H

#include <string>
#include <vector>
#include "ImageMatrix.h"

class EncodeMessage {
public:
    EncodeMessage();
    ~EncodeMessage();

    ImageMatrix encodeMessageToImage(const ImageMatrix& img, const std::string& message, const std::vector<std::pair<int, int>>& positions);

private:
    // Any private helper functions or variables if necessary

    std::string UpdateString(const std::string& message);
    std::string ConvertStringToBinary(const std::string& message);
    std::string ConvertIntegerToBinary(int Value);
    bool IsPrime(int IntToCheck);
    int CalculateFibonacci(int Index);

};

#endif // ENCODE_MESSAGE_H
