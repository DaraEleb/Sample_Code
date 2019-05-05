#ifndef SERVER_H
#define SERVER_H

#include <string>
#include "types.h"
#include "Book.h"
#include <vector>

class Server
{
  public:
    Server();
    ~Server();
    void retrieve(vector<Book*>&);
  private:
    vector<Book*> books;
};

#endif

