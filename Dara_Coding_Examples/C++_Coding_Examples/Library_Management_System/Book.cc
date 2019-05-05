#include "Book.h"

int Book::nextId = 1001;

Book::Book(string t, string a, int y, BookStatusType b) 
  : title(t), author(a), year(y), status(b)
{ 
  id     = nextId++;
}

void Book::setStatus(BookStatusType s)
{
  status = s;
}

int            Book::getId()     { return id; }
string         Book::getTitle()  { return title; }
string         Book::getAuthor() { return author; }
BookStatusType Book::getStatus() { return status; }

