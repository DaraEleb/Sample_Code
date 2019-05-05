#include "Patron.h"
#include "AdultFactory.h"

void AdultFactory::createPatron(string firstName, string lastName, int age, Patron** newPatron)
{
      *newPatron =  new AdultPatron(firstName, lastName, age);
}
