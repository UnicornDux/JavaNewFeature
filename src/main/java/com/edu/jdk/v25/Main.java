/**
 * 自动入口函数
 */

import module java.base;
void main() {
    var fruits = List.of("Linux", "Windows", "MacOs");
    var lengths = fruits.stream()
            .collect(Collectors.toMap(
                    os -> os,
                    String::length
            ));
    IO.println("Operation System: " + lengths);
}