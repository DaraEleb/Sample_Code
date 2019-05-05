#ifndef CHILDPATRON_H
#define CHILDPATRON_H

#include "Patron.h"

class ChildPatron: public Patron 
{
  public:
    ChildPatron(string="", string="",int=0);
    int computeLifetimeCO();
    //int addDependent(Patron*);
};

#endif
