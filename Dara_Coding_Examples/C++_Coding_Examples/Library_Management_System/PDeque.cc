#include <iostream>
using namespace std;
#include "PDeque.h"

PDeque::PDeque() : head(0) { }

PDeque::~PDeque()
{
  Node* currNode = head;
  Node* nextNode;

  while (currNode != 0) {
    nextNode = currNode->next;
    //delete currNode->data;
    delete currNode;
    currNode = nextNode;
  }
}

PDeque::PDeque(const PDeque& copy)
{
   Node* curr = copy.head;
   head = 0;

   while(curr != 0) {
      push(curr->data);
      curr = curr->next;
   }
}
/*PDeque::Node::~Node()
{
  if (data != NULL) {
    delete data;
}*/

int PDeque::push(Patron* add)
{
  int success = C_NOK;

  Node* tmpNode = new Node;
  Node *currNode, *prevNode;

  tmpNode->data = add;
  tmpNode->next = 0;
  currNode = head;
  prevNode = 0;
  
  
  while (currNode != 0) {
    if(currNode->data->computeLifetimeCO() > tmpNode->data->computeLifetimeCO()){
       break;
    }
    prevNode = currNode;
    currNode = currNode->next;
  }

  if (prevNode == 0) {
     head = tmpNode;
  }
  else {
     prevNode->next = tmpNode;
  }
  tmpNode->next = currNode;
  //cout<<tmpNode->data->getFname()<<endl;
  success = C_OK;
  return success;
}

/*void PDeque::popFront() 
{
   Node *curr, *next;
   curr = head;
 
   if(head == 0)
     return;
   
   curr = head->next;
   delete head;
   head = curr;
}*/

PDeque& PDeque::operator--() 
{
   Node *curr, *next;
   curr = head;
 
   /*if(head == 0)
     return ;*/
   
   curr = head->next;
   delete head;
   head = curr;
   return *this;
}

void PDeque::operator--(int a)
{
   Node *currNode, *prevNode;
   currNode = head;
   prevNode = 0;
   
   if (head == 0) {
      return;
   }

   while (currNode->next != 0) {
      prevNode = currNode;
      currNode = currNode->next;
   }
   
   //delete currNode->data;
   delete currNode;
   if (prevNode != 0) {
     prevNode->next = 0;
   }
   if (prevNode == 0) {
      head = 0;
   }
}


/*void PDeque::popBack()
{
   Node *currNode, *prevNode;
   currNode = head;
   prevNode = 0;
   
   if (head == 0) {
      return;
   }

   while (currNode->next != 0) {
      prevNode = currNode;
      currNode = currNode->next;
   }
   
   //delete currNode->data;
   //delete currNode;
   if (prevNode != 0) {
     prevNode->next = 0;
   }
   if (prevNode == 0) {
      head = 0;
   }
}*/

Patron* PDeque::front()
{
   return head->data;
}

Patron* PDeque::back()
{
   Node* currNode = head;

   while (currNode->next != 0) {
      currNode = currNode->next;
   }

   return currNode->data;
}

int PDeque::find(string fn, string ln, Patron** patron)
{
   Node* currNode = head;

   while (currNode != 0) {
      if (currNode->data->getFname() == fn && currNode->data->getLname() == ln){
         break;
      }
      currNode = currNode->next;
   }

   if (currNode == 0) {
      *patron = 0;
      return C_NOK;
   }

   else {
      *patron = currNode->data;
       return C_OK;
   }
}

void PDeque::copy(PDeque** patronQ){

  Node* currNode = head;
  
  if (*patronQ == NULL) {
     return;
  }

  while (currNode != 0) {
     (*patronQ)->push(currNode->data);
     currNode = currNode->next;
  }
}

int PDeque::remove(Patron* patron)
{
   Node *currNode, *prevNode;
   currNode = head;
   prevNode = 0;

   while (currNode != 0) {
      if (currNode->data == patron){
         if (currNode == head) {
            head = currNode->next;
            delete currNode->data;
            delete currNode;
            return C_OK;
         }
 
         prevNode->next = currNode->next;
         delete currNode->data;
         delete currNode;
         return C_OK;
      }
      prevNode = currNode;
      currNode = currNode->next;
   }
   
   return C_NOK;
}

bool PDeque::Empty()
{
   return head == 0;
}


