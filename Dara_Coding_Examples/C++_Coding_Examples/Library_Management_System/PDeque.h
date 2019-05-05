#ifndef PDEQUE_H
#define PDEQUE_H

#include <string>
#include "Patron.h"
#include "types.h"

class PDeque
{
  class Node
  {
    friend class PDeque;
    public:
    private:
      Patron* data;
      Node*    next;
  };

  public:
    PDeque();
    PDeque(const PDeque&);
    ~PDeque();
    int push(Patron*);
    void popFront();
    void popBack();
    Patron* back();
    Patron* front();
    int find(string, string, Patron**);
    int remove(Patron*);
    void copy(PDeque**);
    bool Empty();
    PDeque& operator--();
    void operator--(int);
  private:
    Node* head;

};

#endif


