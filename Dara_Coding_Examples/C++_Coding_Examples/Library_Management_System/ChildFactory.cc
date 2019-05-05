#include "Patron.h"
#include "ChildFactory.h"

void ChildFactory::createPatron(string firstName, string lastName, int age, Patron** newPatron)
{
      *newPatron =  new ChildPatron(firstName, lastName, age);
      //*newPatron->isDependent = 1;
}
