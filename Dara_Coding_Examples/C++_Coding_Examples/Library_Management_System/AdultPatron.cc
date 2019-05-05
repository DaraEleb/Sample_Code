#include <iostream>
using namespace std;
#include "AdultPatron.h"
#include "ChildPatron.h"

AdultPatron::AdultPatron(string fn, string ln, int a) : Patron(fn, ln, a){}

int AdultPatron::computeLifetimeCO(){
   int lifeTT = 0;
   lifeTT += getLifetimeCO();
   for (int i=0; i<DepArray.getMaxIndex(); ++i){
      lifeTT = lifeTT + getDependent(i)->getLifetimeCO();
   }
   return lifeTT;
}


