
#ifdef __cplusplus
extern "C" {
#endif

#ifndef SIMPLENATIVELIBRARY__SIMPLE_FUNCTIONS_H_
#define SIMPLENATIVELIBRARY__SIMPLE_FUNCTIONS_H_

#include <stdlib.h>

#ifdef _MSC_VER
# define LIBRARY_API __declspec(dllexport)
#else
# define LIBRARY_API
#endif 


typedef struct Person {
  long long id;
  char name[10];
  int age;
} Person;


typedef void (*OnEach)(int element);


LIBRARY_API void ForEach(int array[], int length, OnEach on_each);

LIBRARY_API void DumpPerson(Person *person);

LIBRARY_API int GetLangVersion();

LIBRARY_API int Add(int a, int b);

#endif //SIMPLENATIVELIBRARY__SIMPLE_FUNCTIONS_H_

#ifdef __cplusplus
}
#endif
