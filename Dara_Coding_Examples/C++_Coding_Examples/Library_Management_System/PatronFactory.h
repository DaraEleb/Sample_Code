#ifndef PATRON_FACTORY
#define PATRON_FACTORY

#include "Patron.h"
#include "types.h"
#include "ChildPatron.h"
#include "AdultPatron.h"
class PatronFactory
{
   public:
     virtual void createPatron(string, string, int, Patron**) = 0;
};

#endif
