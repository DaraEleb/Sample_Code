#ifndef STORAGE_H
#define STORAGE_H

#include "PDeque.h"
#include "Patron.h"
#include "PDeque.h"

#include "types.h"
#include "Server.h"

class BookArray;
class PDeque;
class Patron;

class Storage 
{
  public:
  Storage();
  ~Storage();
  void retBooks(vector<Book*>*);
  void retPatrons(PDeque*);
  void updatePatrons(UpdateType , Patron*);
  void init();
 
  private:
  Server*   server;
  PDeque* patronQueue;
  
};

#endif


