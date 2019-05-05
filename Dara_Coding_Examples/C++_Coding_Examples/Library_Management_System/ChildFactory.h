#ifndef CHILD_FACTORY
#define CHILD_FACTORY

#include "PatronFactory.h"

class ChildFactory : public PatronFactory
{
   public:
     virtual void createPatron(string, string, int, Patron**);
};

#endif
