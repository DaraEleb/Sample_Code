#ifndef VIEW_H
#define VIEW_H

#include "Book.h"
#include "PLScontrol.h"
#include "PDeque.h"

class PLScontrol;

class View
{
  public:
    View(PLScontrol*);
    void mainMenu();
    void getBookId(int*);
    void getPatronName(string&,string&);
    void printAll();
    void printError(const string&);
    void getPatronAge(int*);
  private:
    PLScontrol* control;
    void adminMenu();
    void patronMenu();
    void viewCollection();
    void printCollection();
    void printPatrons();
    void printPatronsBack();
    void printBookInfo(Book*);
    void printCheckedOut(Patron*);
};

#endif

