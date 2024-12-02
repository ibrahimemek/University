// DecodeMessage.cpp

#include "DecodeMessage.h"
#include <iostream>

// Default constructor
DecodeMessage::DecodeMessage() {
    // Nothing specific to initialize here
}

// Destructor
DecodeMessage::~DecodeMessage() {
    // Nothing specific to clean up
}


std::string DecodeMessage::decodeFromImage(const ImageMatrix& image, const std::vector<std::pair<int, int>>& edgePixels) 
{
    std::string StringAsBit = "";
    for (auto& EdgePixel : edgePixels)
    {
        int CurrentBit = ((int)(image.get_data()[EdgePixel.first][EdgePixel.second])) % 2;
        StringAsBit += std::to_string(CurrentBit);
    }

    while (StringAsBit.size() % 7 != 0)
    {
        StringAsBit = "0" + StringAsBit;
    }

    std::string StringAsChars;
    for (int i = 0; i < StringAsBit.size() / 7; i++)
    {
        std::string CurrentCharBit = StringAsBit.substr(7 * i, 7);
        int CharValue = 0;
        int x = 64;
        for (int k = 0; k < 7; k++)
        {
            CharValue += (CurrentCharBit.at(k) - '0') * x;
            x /= 2;
        }
        if (CharValue < 33) CharValue += 33;
        if (CharValue > 126) CharValue = 126;
        char c = char(CharValue);
        StringAsChars += c;
    }
    
    return StringAsChars;
}
