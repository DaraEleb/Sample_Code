#include <iostream>
#include <string>
#include <sstream>
#include <cstdlib>
#include <vector>
using namespace std;
#include "PLScontrol.h"
#include "PDeque.h"

PLScontrol::PLScontrol() 
{ 
  //lib  = new Library;
  storage = new Storage();
  view = new View(this);
}

PLScontrol::~PLScontrol() 
{ 
  //delete lib;
  delete view;
}

void PLScontrol::launch() 
{ 
  //lib->init();
  view->mainMenu();
  view->printAll();
}

void PLScontrol::addPatron() 
{
  Patron *patron;
  Patron *Ppatron;
  PDeque newpatA;
  storage->retPatrons(&newpatA);
  string fn, ln, pfn, pln;
  int    rc;
  int age, index;

  view->getPatronAge(&age);
  view->getPatronName(fn, ln);

  if (age < 18)
  {
     fact = new ChildFactory();
     cout<<endl<<"      Enter Parent Patron's first name: ";
     getline(cin, pfn);
     cout<<endl<<"      Enter Parent Patron's last name: ";
     getline(cin, pln);
     
     fact->createPatron(fn, ln, age, &patron);
     patron->isDependent = 1;
     rc = newpatA.find(pfn, pln, &Ppatron);
     if (rc != C_OK) {
       view->printError("\nCould not find patron, press <ENTER> to continue...");
      return;
     }
     if (Ppatron->isDependent == 1) {
       view->printError("\nThat patron is a Dependent, it is not allowed, press <Enter> to continue...");
       return;
     }
     else { 
       Ppatron->DepArray.add(0, patron);
     }
  }

  else {
     fact = new AdultFactory();
     fact->createPatron(fn, ln, age, &patron);
  }

  storage->updatePatrons(ADD_P, patron);
}

Storage* PLScontrol::getStorage() { return storage; }


void PLScontrol::remPatron() 
{
  Patron *patron;
  PDeque newpatA;
  storage->retPatrons(&newpatA);
  string fn, ln;
  int    index;
  int    rc;

  view->getPatronName(fn, ln);

  rc = newpatA.find(fn, ln, &patron);
  if (rc != C_OK) {
    view->printError("\nCould not find patron, press <ENTER> to continue...");
    return;
  }
  if (patron->hasBooksOut()) {
    view->printError("\nPatron has books checked out, could not remove.  Press <ENTER> to continue...");
    return;
  }
  if (patron->hasDependents() == 1) {
    view->printError("\nPatron has dependents, could not remove.  Press <ENTER> to continue...");
    return;
  }
  if (patron->isDependent == 1) {
    view->printError("\nCannot delete dependents.  Press <ENTER> to continue...");
    return;
   }
  storage->updatePatrons(DELETE_P, patron);
}

void PLScontrol::checkIn(Patron* patron) 
{
  Book* book;

  int   id, index, rc;

  view->getBookId(&id);
 
  rc = patron->Pbooks.find(id, &book);

  if (rc != C_OK) {
    view->printError("\nBook is not checked out to this patron, press <ENTER> to continue...");
    return;
  }

  book->setStatus(CHECKED_IN);
  patron->Pbooks.remove(book);
  
  storage->updatePatrons(MODIFY_P, patron);

}

void PLScontrol::checkOut(Patron* patron) 
{
  Book* book;
  int   id, index, rc;
  vector<Book*> newbookA;
  storage->retBooks(&newbookA);

  if (patron->getMaxBooksIndex() >= MAX_SIZE - 1) {
    view->printError("\nPatron cannot check out any more books.  Press <ENTER> to continue...");
    return;
  }

  view->getBookId(&id);

  rc = C_NOK;
  for (int i=0; i<newbookA.size(); ++i){
     if(newbookA.at(i) == 0){
        continue;
      }
     if(newbookA.at(i)->getId() == id){
        rc = C_OK;
        book = newbookA.at(i);
     }
  }

  if (rc != C_OK) {
    view->printError("\nCould not find book, press <ENTER> to continue...");
    return;
  }

  if (book->getStatus() != CHECKED_IN) {
    view->printError("\nBook is not available, press <ENTER> to continue...");
    return;
  }
  book->setStatus(CHECKED_OUT);
  patron->Pbooks.add(id, book);
  patron->setLifetimeCO();
  storage->updatePatrons(MODIFY_P, patron);

}

//Library* PLScontrol::getLib() { return lib; }

