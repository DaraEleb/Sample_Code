#ifndef ADULT_FACTORY
#define ADULT_FACTORY

#include "PatronFactory.h"

class AdultFactory : public PatronFactory
{
   public:
     virtual void createPatron(string, string, int, Patron**);
};

#endif
