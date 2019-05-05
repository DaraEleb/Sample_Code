#ifndef MAP_H
#define MAP_H

#include "types.h"

template < class T, class F> 
class Map {

  public: 
      Map();
      int  find(T, F*);
      int  getMaxIndex();
      int add(T, F);
      void remove(F);
      F    at(int);
      T    atKey(int);

  private: 
       int maxIndex;
       T Key[MAX_SIZE];
       F Value[MAX_SIZE];
      
};

template <class T, class F>
Map<T, F>::Map() {
	maxIndex = 0;
}

template < class T, class F> 
int Map<T,F>::add(T key, F value) {
   if(maxIndex > MAX_SIZE) return C_NOK;

   Key[maxIndex] = key;
   Value[maxIndex] = value;
   ++maxIndex;
   return C_OK;
}

template < class T, class F> 
void Map<T,F>::remove(F value) {
	for(int i = 0; i < maxIndex; ++i)
	{
	  if(Value[i] == value) {Value[i] = NULL;}
        }
}

template < class T, class F> 
int Map<T,F>::find(T key, F* value) {
 for (int i=0; i < maxIndex; ++i){
    if (Key[i] == 0)
      continue;
    if ((Key[i] == key) && (Value[i] != NULL)) {
      *value  = Value[i];
      return C_OK;
    }
  }
  *value  =  0;
  return C_NOK;
}

template < class T, class F> 
int Map<T,F>::getMaxIndex() {
	return maxIndex;
}

template < class T, class F> 
F Map<T,F>::at(int i) {
	if(i > maxIndex) return 0;
	return Value[i];
}

template < class T, class F> 
T Map<T,F>::atKey(int i) {
	if(i > maxIndex) return 0;
	return Key[i];
}

#endif

