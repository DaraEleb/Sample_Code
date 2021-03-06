#include <iostream>
#include <string>
#include "storage.h"
#include "Map.h"
#include "Patron.h"
#include "AdultPatron.h"
#include "View.h"
#include "PDeque.h"
#include <vector>

Storage::Storage(){
 
  server   = new Server();
  patronQueue = new PDeque(); 
  init();
  
}


Storage::~Storage(){

}


void Storage::retBooks(vector<Book*>* bookArr){
  server->retrieve(*bookArr);
 } 

void Storage::retPatrons(PDeque* patronQ){

  if (patronQ == NULL) {
    return;
  }
  patronQueue->copy(&patronQ);
}

void Storage::updatePatrons(UpdateType action, Patron* patron){

  int rc;
  Patron* patTemp;
  View* view;

  if(patron == NULL){
     return; 
  }
 
  if(action == ADD_P){
     rc = patronQueue->push(patron);
     if (rc != C_OK) {
        view->printError("\nCould not add patron to library, press <ENTER> to continue...");
     }
  }

  else if(action == DELETE_P){
     patronQueue->remove(patron);
  }

  else if(action == MODIFY_P){
     Patron* nPat;
     patronQueue->find(patron->getFname(), patron->getLname(), &nPat);
     *nPat = *patron;
     cout<<nPat->getFname()<<endl;
  }
 }

void Storage::init()
{
  //Book*   newBook;
  //Book* newsBook;
  Patron* newPatron;
  newPatron = new AdultPatron("Jack", "Shephard");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Kate", "Austen");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Hugo", "Reyes");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("James", "Ford");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Sayid", "Jarrah");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Sun-Hwa", "Kwon");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Jin-Soo", "Kwon");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("John", "Locke");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Juliet", "Burke");
  patronQueue->push(newPatron);

  newPatron = new AdultPatron("Benjamin", "Linus");
  patronQueue->push(newPatron);
}

 


