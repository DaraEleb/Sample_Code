#ifndef ADULTPATRON_H
#define ADULTPATRON_H

#include "Patron.h"

class AdultPatron : public Patron
{
  public:
    AdultPatron(string="", string="", int=0);
    int computeLifetimeCO();
};

#endif
