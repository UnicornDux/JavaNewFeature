#include "ffm.h"
#include <stdlib.h>
#include <stdio.h>

void ForEach(int array[], int length, OnEach on_each) {
  for(int i = 0; i < length; ++i) {
    on_each(array[i]);
  }
}

int GetLangVersion() {
  // return __STDC_VERSION__;
  return 10202;
}

int Add(int a, int b){
    return a + b;
}
