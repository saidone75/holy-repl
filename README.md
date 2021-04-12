# holy-bibl
The Holy BIBL in your REPL

## Usage
List of books:
```
holy-bibl.core=> (books)
("Gen" "Exod" "Lev" "Num" "Deut" "Josh" "Judg" "Ruth" "Sam1" "Sam2" "Kgs1" "Kgs2" "Chr1" "Chr2" "Ezra" "Neh" "Esth" "Job" "Ps" "Prov" "Eccl" "Song" "Isa" "Jer" "Lam" "Ezek" "Dan" "Hos" "Joel" "Amos" "Obad" "Jonah" "Mic" "Nah" "Hab" "Zeph" "Hag" "Zech" "Mal" "Matt" "Mark" "Luke" "John" "Acts" "Rom" "Cor1" "Cor2" "Gal" "Eph" "Phil" "Col" "Thess1" "Thess2" "Tim1" "Tim2" "Titus" "Phlm" "Heb" "Jas" "Pet1" "Pet2" "John1" "John2" "John3" "Jude" "Rev")
```
books are functions:
```
holy-bibl.core=> (Gen "1:14-16")
("And God said, Let there be lights in the firmament of heaven to divide the day from the night; and let them be for signs, and for seasons, and for days and years:" "and let them be for lights in the firmament of heaven to give light upon the earth: and it was so." "And God made the two great lights; the greater light to rule the day, and the lesser light to rule the night: he made the stars also.")
```
number of books are postfixed as by default Clojure does not accept functions names starting with digits (e.g. "3John" become "John3")

Chapter and verse system is the standard, e.g.: (John "3:14–16") or (John "3.14–16") refers to the book of John, chapter 3, verses 14 through 16

## Notes
At this point in the development parameters are not yet checked, please do not pay much attention to crashes or odd behaviours.
Suggestions, patches, pull requests and feedbacks are all welcome!
