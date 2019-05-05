#include <iostream>
using namespace std;
#include "ChildPatron.h"

ChildPatron::ChildPatron(string fn, string ln, int a) : Patron(fn, ln, a){}

int ChildPatron::computeLifetimeCO(){
   return getLifetimeCO();
}

