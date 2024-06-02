# holy-repl
The Holy BIBL in your REPL

[![Clojars Version](https://img.shields.io/clojars/v/org.saidone/holy-repl)](https://clojars.org/org.saidone/holy-repl)
[![cljdoc badge](https://cljdoc.org/badge/org.saidone/holy-repl)](https://cljdoc.org/d/org.saidone/holy-repl)

![Lisp](https://imgs.xkcd.com/comics/lisp.jpg)

## Usage
List of books:
```clojure
holy-repl.core=> (books)
("Gen" "Exod" "Lev" "Num" "Deut" "Josh" "Judg" "Ruth" "Sam1" "Sam2" "Kgs1" "Kgs2" "Chr1" "Chr2" "Ezra" "Neh" "Esth" "Job" "Ps" "Prov" "Eccl" "Song" "Isa" "Jer" "Lam" "Ezek" "Dan" "Hos" "Joel" "Amos" "Obad" "Jonah" "Mic" "Nah" "Hab" "Zeph" "Hag" "Zech" "Mal" "Matt" "Mark" "Luke" "John" "Acts" "Rom" "Cor1" "Cor2" "Gal" "Eph" "Phil" "Col" "Thess1" "Thess2" "Tim1" "Tim2" "Titus" "Phlm" "Heb" "Jas" "Pet1" "Pet2" "John1" "John2" "John3" "Jude" "Rev")
```
books are functions indeed (as originally intended):
```clojure
holy-repl.core=> (Jer "29:11")
("For I know the thoughts that I think toward you, saith Jehovah, thoughts of peace, and not of evil,
to give you hope in your latter end.")
```
multiple verses are (obviously) returned as a list:
```clojure
holy-repl.core=> (Prov "3:5-6")
("Trust in Jehovah with all thy heart, And lean not upon thine own understanding:" "In all thy ways acknowledge him, And he will direct thy paths.")
```
number of chapters in a book:
```clojure
holy-repl.core=> (count (Exod))
40
```
chapter number and verses:
```clojure
holy-repl.core=> (into
            #_=>  (sorted-map)
            #_=>  (zipmap
            #_=>   (range 1 (inc (count (Exod))))
            #_=>   (map
            #_=>    #(count (:content %))
            #_=>    (Exod))))
{1 22, 2 25, 3 22, 4 31, 5 23, 6 30, 7 25, 8 32, 9 35, 10 29, 11 10, 12 51, 13 22, 14 31, 15 27, 16 36, 17 16, 18 27, 19 25, 20 26, 21 36, 22 31, 23 33, 24 18, 25 40, 26 37, 27 21, 28 43, 29 46, 30 38, 31 18, 32 35, 33 23, 34 35, 35 35, 36 38, 37 29, 38 31, 39 43, 40 38}
```
random verse:
```clojure
holy-repl.core=> (random-verse)
{"Josh 23:11" ("Take good heed therefore unto yourselves, that ye love Jehovah your God.")}
holy-repl.core=> (random-verse)
{"Dan 7:3" ("And four great beasts came up from the sea, diverse one from another.")}
```
Chapter and verse system is the standard, e.g.: (John "3:14–16") or (John "3.14–16") refers to the book of John, chapter 3, verses 14 through 16

## Notes
Number of books are postfixed as by default Clojure does not accept functions names starting with digits (e.g. "3John" become "John3").

Suggestions, patches, pull requests and feedbacks are all welcome!

## License
Copyright (c) 2021-2024 Saidone

Distributed under the MIT License