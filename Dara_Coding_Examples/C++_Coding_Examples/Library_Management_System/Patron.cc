#include <iostream>
using namespace std;
#include "Patron.h"

Patron::Patron(string fn, string ln, int a) 
  : fname(fn), lname(ln), lifetimeCO(0), isDependent(0), age(a)
{}

int Patron::hasBooksOut()
{
  for(int i=0; i<Pbooks.getMaxIndex(); ++i) {
    if (Pbooks.at(i) != 0)
      return C_TRUE;
  }
  return C_FALSE;
}

int Patron::getMaxBooksIndex() { return Pbooks.getMaxIndex(); }
int Patron::getMaxDepIndex() { return DepArray.getMaxIndex();}
int Patron::getLifetimeCO()    { return lifetimeCO; }
void Patron::setLifetimeCO()   { lifetimeCO++; }
string Patron::getFname()      { return fname; }
string Patron::getLname()      { return lname; }

Book* Patron::getBook(int index)
{
  if (index < 0 || index >= getMaxBooksIndex())
    return 0;

  return Pbooks.at(index);
}


Patron* Patron::getDependent(int index) 
{ 
  if (index < 0 || index >= getMaxDepIndex())
    return 0;
  return DepArray.at(index); 
}

int Patron::hasDependents()
{
   int Deps = 0;
   int isTrue = 0;
   for (int i=0; i<getMaxDepIndex(); ++i) {
     if (DepArray.at(i) == 0 || DepArray.at(i) == NULL) {
        continue;
     }
     Deps = Deps + 1;
   }
   if (Deps > 0) {isTrue = 1;}
   return isTrue;
}

Patron& Patron::operator=(Patron& a)
{
  cout<<this->fname<<endl;
   this->fname = a.fname;
   this->lname = a.lname;
   this->lifetimeCO = a.lifetimeCO;
   this->Pbooks = a.Pbooks;
   this->DepArray = a.DepArray;
   return *this;
}
