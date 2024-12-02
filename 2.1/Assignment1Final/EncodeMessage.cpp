#include "EncodeMessage.h"
#include <cmath>
#include <iostream>



// Default Constructor
EncodeMessage::EncodeMessage() {

}

// Destructor
EncodeMessage::~EncodeMessage() {

}

// Function to encode a message into an image matrix
ImageMatrix EncodeMessage::encodeMessageToImage(const ImageMatrix& img, const std::string& message, const std::vector<std::pair<int, int>>& positions)
{
	const std::string UpdatedString = UpdateString(message);
	std::string BinaryString = ConvertStringToBinary(UpdatedString);

	for (int i = 0; i < positions.size(); i++)
	{
		if (i > BinaryString.size() - 1)
			break; // this means message ended but some edges remained

		int Height = positions.at(i).first;
		int Width = positions.at(i).second;
		if (BinaryString.at(i) == '1' && ((int)(img.get_data()[Height][Width])) % 2 == 0)
		{
			img.get_data()[Height][Width] += 1;
		}
		else if (BinaryString.at(i) == '0' && ((int)(img.get_data()[Height][Width])) % 2 == 1)
		{
			img.get_data()[Height][Width] -= 1;

		}
	}

	return img;
}

std::string EncodeMessage::UpdateString(const std::string& message)
{
	if (message.size() == 0) return "";
	if (message.size() == 1) return message;
	std::string NewMessage = "";
	for (int i = 0; i < message.size(); i++)
	{
		int CharValue = (int)message[i];
		if (i > 1 && IsPrime(i))
		{
			CharValue += CalculateFibonacci(i);


		}
		if (CharValue > 126) CharValue = 126;
		if (CharValue < 33) CharValue += 33;
		NewMessage += char(CharValue);
	}
	std::string StringToEncode;
	if (message.size() % 2 == 0)
	{
		StringToEncode = NewMessage.substr(NewMessage.size() - NewMessage.size() / 2, NewMessage.size() / 2) + NewMessage.substr(0, NewMessage.size() / 2);
	}
	else
	{
		StringToEncode = NewMessage.substr(NewMessage.size() - NewMessage.size() / 2, NewMessage.size() / 2) + NewMessage.substr(0, NewMessage.size() / 2 + 1);
	}
	return StringToEncode;
}

std::string EncodeMessage::ConvertStringToBinary(const std::string& message)
{
	std::string StringAsBinary = "";
	for (auto& EachChar : message)
	{
		StringAsBinary += ConvertIntegerToBinary((int)EachChar);
	}
	return StringAsBinary;
}

std::string EncodeMessage::ConvertIntegerToBinary(int Value)
{
	std::string StringAsBinary = "";
	int x = 64;
	while (x != 0)
	{
		StringAsBinary += std::to_string(Value / x);
		Value -= x * (Value / x);
		x /= 2;
	}
	return StringAsBinary;
}

bool EncodeMessage::IsPrime(int IntToCheck)
{
	bool bIsPrime = true;
	for (int i = 2; i < IntToCheck / 2 + 1; i++)
	{
		if (IntToCheck % i == 0) bIsPrime = false;
	}

	return bIsPrime;
}

int EncodeMessage::CalculateFibonacci(int Index)
{
	int sum = 0;
	int Num1 = 0;
	int Num2 = 1;
	for (int i = 2; i < Index + 1; i++)
	{
		sum = Num1 + Num2;
		Num1 = Num2;
		Num2 = sum;
	}
	return sum;
}
