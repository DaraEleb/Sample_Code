#ifndef PLS_CONTROL_H
#define PLS_CONTROL_H

#include "types.h"
#include "storage.h"
#include "Patron.h"
#include "View.h"
#include "ChildPatron.h"
#include "AdultPatron.h"
#include "PatronFactory.h"
#include "ChildFactory.h"
#include "AdultFactory.h"

class View;

class PLScontrol
{
  public:
    PLScontrol();
    ~PLScontrol();
    void launch();
    void addPatron();
    void remPatron();
    void checkIn(Patron*);
    void checkOut(Patron*);
    Storage* getStorage();
    
  private:
    View*    view;
    Storage* storage;
    PatronFactory* fact;
};

#endif

