# holy-bibl
The Holy BIBL in your REPL

![Lisp](https://imgs.xkcd.com/comics/lisp.jpg)

## Usage
List of books:
```
holy-bibl.core=> (books)
("Gen" "Exod" "Lev" "Num" "Deut" "Josh" "Judg" "Ruth" "Sam1" "Sam2" "Kgs1" "Kgs2" "Chr1" "Chr2" "Ezra" "Neh" "Esth" "Job" "Ps" "Prov" "Eccl" "Song" "Isa" "Jer" "Lam" "Ezek" "Dan" "Hos" "Joel" "Amos" "Obad" "Jonah" "Mic" "Nah" "Hab" "Zeph" "Hag" "Zech" "Mal" "Matt" "Mark" "Luke" "John" "Acts" "Rom" "Cor1" "Cor2" "Gal" "Eph" "Phil" "Col" "Thess1" "Thess2" "Tim1" "Tim2" "Titus" "Phlm" "Heb" "Jas" "Pet1" "Pet2" "John1" "John2" "John3" "Jude" "Rev")
```
books are functions:
```
holy-bibl.core=> (Jer "29:11")
("For I know the thoughts that I think toward you, saith Jehovah, thoughts of peace, and not of evil,
to give you hope in your latter end.")
```
number of chapters of a book:
```
holy-bibl.core=> (count (Exod))
40
```
chapter number and verses:
```
holy-bibl.core=> (into
            #_=>  (sorted-map)
            #_=>  (zipmap
            #_=>   (range 1 (inc (count (Exod))))
            #_=>   (map
            #_=>    #(count (:content %))
            #_=>    (Exod))))
{1 22, 2 25, 3 22, 4 31, 5 23, 6 30, 7 25, 8 32, 9 35, 10 29, 11 10, 12 51, 13 22, 14 31, 15 27, 16 36, 17 16, 18 27, 19 25, 20 26, 21 36, 22 31, 23 33, 24 18, 25 40, 26 37, 27 21, 28 43, 29 46, 30 38, 31 18, 32 35, 33 23, 34 35, 35 35, 36 38, 37 29, 38 31, 39 43, 40 38}
```
Chapter and verse system is the standard, e.g.: (John "3:14–16") or (John "3.14–16") refers to the book of John, chapter 3, verses 14 through 16

## Notes
Number of books are postfixed as by default Clojure does not accept functions names starting with digits (e.g. "3John" become "John3")

At this point in the development parameters are not yet checked, please do not pay much attention to crashes or odd behaviours

Suggestions, patches, pull requests and feedbacks are all welcome!
