#ifndef TYPES_H
#define TYPES_H

#define MAX_SIZE      64
#define MAX_COLL_SIZE   1024
#define C_OK               0
#define C_NOK             -1
#define C_TRUE             1
#define C_FALSE            0

typedef enum {CHECKED_IN, CHECKED_OUT, UNDER_REPAIR, LOST} BookStatusType;
typedef enum {ADD_P, MODIFY_P, DELETE_P} UpdateType;

#endif
