# cmake 最小兼容的版本
cmake_minimum_required(VERSION 3.15)

# 当前的项目名称
project("ffm")

set(CMAKE_C_STANDARD 11)
# 头文件所在的目录
include_directories(include)
# 源码文件所在的目录
aux_source_directory(src SOURCE)

add_executable(main ${SOURCE})
add_library(${CMAKE_PROJECT_NAME} SHARED ${SOURCE})
target_link_libraries(${CMAKE_PROJECT_NAME})
