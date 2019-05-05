#ifndef PATRON_H
#define PATRON_H

#include <string>
#include <vector>
#include "Book.h"
#include "types.h"
#include "Map.h"


class Patron
{
  public:
    Patron(string="", string="", int=0);
    //void   addBook(Book*);
    //void   remBook(int);
    //int    findBook(int, Book**, int*);
    int    hasBooksOut();
    string getFname();
    string getLname();
    Book*  getBook(int);
    int    getMaxBooksIndex();
    int    getLifetimeCO();
    void    setLifetimeCO();
    Map<int, Book*> Pbooks; 
    int getMaxDepIndex();
    //virtual int addDependent(Patron*)=0;
    Patron* getDependent(int);
    Map<int, Patron*> DepArray;
    int hasDependents();
    int isDependent;
    virtual int computeLifetimeCO()=0;
    Patron& operator=(Patron&);
  private:
    
    string fname;
    string lname;
    int    age;
    int    lifetimeCO;
};

#endif

