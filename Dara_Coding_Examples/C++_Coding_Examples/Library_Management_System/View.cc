#include <iostream>
#include <iomanip>
#include <string>
#include <sstream>
#include <cstdlib>
#include <vector>
using namespace std;

#include "View.h"
//#include "PDeque.h"

View::View(PLScontrol* c) : control(c) { }

void View::mainMenu()
{
  string choice = "-1";

  while (choice != "0") {
    system("clear");
    cout<< "\n\n\n\n\n                   WELCOME TO THE PANDORA LIBRARY SYSTEM \n\n\n\n";
    cout<< "        Here are your options:\n\n";
    cout<< "          1. Patron Menu\n\n";
    cout<< "          2. Administrator Menu\n\n";
    cout<< "          3. View Entire Collection\n\n";
    cout<< "          4. Print Patron Ascending\n\n";
    cout<< "          5. Print Patron Descending\n";
    cout<< "          0. Exit\n\n";
    cout<< "        Enter one of the choices above:  ";
    getline(cin, choice);

    while (choice != "1" && choice != "2"
        && choice != "3" && choice != "4" && choice != "5"
        && choice != "0") {
      cout<< "        Enter one of the choices above:  ";
      getline(cin, choice);
    }

    switch(choice[0]) {
      case '1':
        patronMenu();
        break;
      case '2':
        adminMenu();
        break;
      case '3':
        viewCollection();
        break;
      case '4':
        printPatrons();
        break;
      case '5':
        printPatronsBack();
        break;
      default:
        return;
    }
  }
}

void View::patronMenu()
{
  PDeque patA;
  control->getStorage()->retPatrons(&patA);
  Patron* patron;
  Book*   book;
  string  choice = "-1";
  string  fn, ln;
  int     index, rc;

  getPatronName(fn, ln);
  rc = patA.find(fn, ln, &patron);
  if (rc != C_OK) {
    printError("\nCould not find patron, press <ENTER> to continue...");
    return;
  }

  while (choice != "0") {
    system("clear");
    cout<< "\n\n\n\n\n                   PATRON MENU \n\n";
    cout<< "        Here are your options:\n\n";
    cout<< "          1. Check in a book\n";
    cout<< "          2. Check out a book\n";
    cout<< "          3. List books checked out\n";
    cout<< "          0. Exit\n\n";
    cout<< "        Enter one of the choices above:  ";
    getline(cin, choice);

    while (choice != "1" && choice != "2"
        && choice != "3" && choice != "0") {
      cout<< "        Enter one of the choices above:  ";
      getline(cin, choice);
    }

    switch(choice[0]) {
      case '1':
        control->checkIn(patron);
        break;
      case '2':
        control->checkOut(patron);
        break;
      case '3':
        printCheckedOut(patron);
        break;
      default:
        return;
    }
  }
}

void View::adminMenu()
{
  string choice = "-1";

  while (choice != "0") {
    system("clear");
    cout<< "\n\n\n\n\n                   ADMINISTRATOR MENU \n\n";
    cout<< "        Here are your options:\n\n";
    cout<< "          1. Add a Patron \n";
    cout<< "          2. Delete a Patron\n";
    cout<< "          0. Exit\n\n";
    cout<< "        Enter one of the choices above:  ";
    getline(cin, choice);

    while (choice != "1" && choice != "2"
           && choice != "0") {
      cout<< "        Enter one of the choices above:  ";
      getline(cin, choice);
    }

    switch(choice[0]) {
      case '1':
        
        control->addPatron();
        break;
      case '2':
        control->remPatron();
        break;
      default:
        return;
    }
  }
}

void View::viewCollection()
{
  system("clear");
  printCollection();
  cout<< "\nPress <ENTER> to continue...";
  cin.get();
}

void View::getPatronName(string& fn, string& ln)
{
  string str;

  cout<< endl<<"        Enter patron name ([first] [last]:  ";
  getline(cin, str);
  stringstream ss(str);
  ss >> fn >> ln;
}

void View::getPatronAge(int* age)
{
   string PAge;
   cout<< endl<<"      Enter patron age:   ";
   getline(cin, PAge);
   stringstream ss(PAge);
   ss >> *age;
}
   

void View::getBookId(int* id)
{
  string str;

  cout<< endl<<"        Enter book identifier:  ";
  getline(cin, str);
  stringstream ss(str);
  ss >> *id;
}

void View::printAll()
{
  system("clear");
  printCollection();
  printPatrons();
  printPatronsBack();
}

void View::printCollection()
{
  vector<Book*> bookA;
  control->getStorage()->retBooks(&bookA);
  cout << endl << setw(40)<< "COLLECTION" <<endl;;
  cout << setw(40)<< "----------"<<endl<<endl;

  for (int i=0; i<bookA.size(); ++i) {
    if (bookA.at(i) == NULL)
      continue;
    printBookInfo(bookA.at(i));
  }
}

void View::printBookInfo(Book* book)
{
  cout<< "    "<<setw(4)<<book->getId()<<setw(40)<<book->getTitle()<<setw(20)<<book->getAuthor();

  switch(book->getStatus()) {
    case CHECKED_IN:
      cout<< "  "<<setw(12)<< "Checked in";
      break;
    case CHECKED_OUT:
      cout<< "  "<<setw(12)<< "Checked out";
      break;
    case UNDER_REPAIR:
      cout<< "  "<<setw(12)<< "Under repair";
      break;
    case LOST:
      cout<< "  "<<setw(12)<< "Lost";
      break;
  }
  cout << endl;
}

void View::printPatrons()
{
   string str;
   PDeque tempQueue;
   control->getStorage()->retPatrons(&tempQueue);

   cout<< endl<<endl<<setw(40)<<"PATRONS"<<endl;;
   cout << setw(40)<< "-------"<<endl<<endl;
 
   while(tempQueue.Empty() == false) {

    str = tempQueue.front()->getFname() + " "
          + tempQueue.front()->getLname();
    str += ", lifetime #books: ";
    cout << setw(40) << str ;
    cout << tempQueue.front()->computeLifetimeCO() << endl;
    for (int j=0; j<tempQueue.front()->getMaxBooksIndex(); ++j) {
      if (tempQueue.front()->getBook(j) == NULL)
        continue;
      printBookInfo(tempQueue.front()->getBook(j));
    }
    if (tempQueue.front()->hasDependents() == 1)
    {
       cout << "                        " 
         << tempQueue.front()->getFname() << "'s Dependents" <<endl;
       for (int n=0; n<tempQueue.front()->getMaxDepIndex(); ++n) {
          if (tempQueue.front()->getDependent(n) == NULL)
            continue;
      
          cout << endl << "                              " 
           << tempQueue.front()->getDependent(n)->getFname()<<" "
           << tempQueue.front()->getDependent(n)->getLname() <<endl<<endl;
       }
     }
   
     --tempQueue;
   }
}

void View::printPatronsBack()
{
   string str;
   PDeque tempQueue;
   control->getStorage()->retPatrons(&tempQueue);

   cout<< endl<<endl<<setw(40)<<"PATRONS"<<endl;;
   cout << setw(40)<< "-------"<<endl<<endl;
 
   while(tempQueue.Empty() == false) {
    str = tempQueue.back()->getFname() + " "
          + tempQueue.back()->getLname();
    str += ", lifetime #books: ";
    cout << setw(40) << str ;
    cout << tempQueue.back()->computeLifetimeCO() << endl;
    for (int j=0; j<tempQueue.back()->getMaxBooksIndex(); ++j) {
      if (tempQueue.back()->getBook(j) == NULL)
        continue;
      printBookInfo(tempQueue.back()->getBook(j));
    }
    if (tempQueue.back()->hasDependents() == 1)
    {
       cout << "                        " 
         << tempQueue.back()->getFname() << "'s Dependents" <<endl;
       for (int n=0; n<tempQueue.back()->getMaxDepIndex(); ++n) {
          if (tempQueue.back()->getDependent(n) == NULL)
            continue;
      
          cout << endl << "                              " 
           << tempQueue.back()->getDependent(n)->getFname()<<" "
           << tempQueue.back()->getDependent(n)->getLname() <<endl<<endl;
       }
     }
   
     tempQueue--;
   }
}
   
void View::printCheckedOut(Patron* patron)
{
  system("clear");
  cout << endl<<endl<<endl <<setw(40)<< "BOOKS CHECKED OUT FOR:  "
       << patron->getFname() << " " << patron->getLname() <<endl;
  cout << setw(38)<< "----------------------"<<endl<<endl;

  for (int i=0; i<patron->getMaxBooksIndex(); ++i) {
    if (patron->getBook(i) == 0)
      continue;
    printBookInfo(patron->getBook(i));
  }
  cout<< "\nPress <ENTER> to continue...";
  cin.get();
}

void View::printError(const string& err)
{
  cout << err << endl;
  cin.get();
}

