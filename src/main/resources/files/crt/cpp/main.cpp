#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>

using namespace std;

bool writeFile()
{
    ofstream testFile ("/tmp/cppTestFile");
    cout << "Now we will write some text to fire /tmp/cppTestFile" << endl;
    if(testFile.is_open())
    {
        testFile << "This is basic line,\n";
        testFile << "Created by C++ Development Assistant\n";
        testFile.close();
        cout << "Text has been written to file successfully" << endl;
    }
    else
    {
        cerr << "Unable to open file";
        return false;
    }
    return true;
}

bool readFile()
{
    ifstream testFile ("/tmp/cppTestFile");
    cout << "Read text from file /tmp/cppTestFile" << endl;
    if(testFile.is_open())
    {
        string line;
        while (getline(testFile,line))
        {
            cout << line << endl;
        }
        cout << "Reading operation was successful" << endl;
    }
    else
    {
        cerr << "Unable to open file" << endl;
        return false;
    }
    return true;
}

int main()
{

    if (!writeFile())
    {
        cerr << "Could not create and write to file" << endl;
        exit(1);
    }

    if(!readFile())
    {
        cerr << "Could not read from file" << endl;
        exit(1);
    }
}
       
