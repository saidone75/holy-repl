(ns holy-repl.core
  (:import [java.security SecureRandom])
  (:gen-class))

(require '[clojure.data.xml :as xml]
         '[clojure.string :as s]
         '[clojure.walk :as w]
         '[clojure.java.io :as io])

(defn- is
  []
  (io/input-stream (io/resource "American Standard Version.xml")))

(defn- testaments []
  (filter
    #(= "testament" (:type (:attrs %)))
    (-> (xml/parse (is))
        :content
        (nth 0)
        :content)))

(defn books
  "Returns the list of books.
  ```clojure
  ((juxt first last) (books))
  => [\"Gen\" \"Rev\"]
  ```"
  []
  (map
    #(if (re-matches #"^\d.*" %)
       (apply str (conj (vec (rest %)) (first %)))
       %)
    (reduce
      #(when (= "book" (:type (:attrs %2)))
         (conj %1 (:osisID (:attrs %2))))
      []
      (flatten (map :content (testaments))))))

(defn- parse-args [args]
  (when-not (s/blank? args)
    (let [[chapter verses]
          (s/split args #"[\.\:]")]
      {:chapter chapter
       :verses  verses})))

(defn- get-book [book-name]
  (->>
    (filter
      #(= book-name (:osisID (:attrs %)))
      (flatten (map :content (testaments))))
    first
    :content))

(defn- get-chapter [book-name chapter]
  (->>
    (filter
      #(= (str book-name "." chapter) (:osisID (:attrs %)))
      (get-book book-name))
    first
    :content))

(defn- get-verse [book-name chapter verse]
  (apply
    str
    (flatten
      (w/postwalk
        #(if (instance? clojure.data.xml.Element %)
           (:content %)
           %)
        (filter
          #(= (str book-name "." chapter "." verse) (:osisID (:attrs %)))
          (get-chapter book-name chapter))))))

(defn- get-verses [book-name chapter verses]
  (let [[f l] (s/split verses #"-")
        l (if (nil? l) f l)]
    (map
      (partial get-verse book-name chapter)
      (range (Integer/parseInt f) (inc (Integer/parseInt l))))))

(defn- read-book [book-name args]
  (let [book-name (if (re-matches #"^.*\d$" book-name)
                    (apply str (conj (butlast book-name) (last book-name)))
                    book-name)
        {chapter :chapter verses :verses} (parse-args (first args))]
    (cond
      (nil? chapter) (get-book book-name)
      (nil? verses) (get-chapter book-name chapter)
      :else (get-verses book-name chapter verses))))

(run!
  #(intern *ns* (symbol %)
           (fn [& args] (read-book % args)))
  (books))

(defn random-verse
  "Returns a map entry representing a random verse from a random book.
  The key is the name of the book followed by chapter and verse numbers.\\
  For divination purposes at higher accuracy see also [[secure-random-verse]].
  ```clojure
  (random-verse)
  => [\"John 2:6\" (\"Now there were six waterpots of stone set there after the Jews' manner of purifying, containing
  two or three firkins apiece.\")]
  ```"
  []
  (let [book-name (first (shuffle (books)))
        book (resolve (symbol book-name))
        book-map (zipmap (range 1 (inc (count (book)))) (map #(inc (rand-int (count (:content %)))) (book)))
        chapter (rand-nth (keys book-map))]
    (first {(str book-name " " chapter ":" (get book-map chapter)) (book (str chapter ":" (get book-map chapter)))})))

(defn secure-random-verse
  "Returns a map entry representing a random verse from a random book.
  The key is the name of the book followed by chapter and verse numbers.\\
  Like [[random-verse-str]] but using FIPS 140-2 compliant RNG to determine all collections indices.
  ```clojure
  (secure-random-verse)
  => [\"Deut 9:12\"\n (\"And Jehovah said unto me, Arise, get thee down quickly from hence; for thy people that thou
  hast brought forth out of Egypt have corrupted themselves; they are quickly turned aside out of the way which I
  commanded them; they have made them a molten image.\")]
  ```"
  []
  (let [book-name (nth (books) (.nextInt (SecureRandom.) (count (books))))
        book (resolve (symbol book-name))
        book-map (zipmap (range 1 (inc (count (book)))) (map #(inc (.nextInt (SecureRandom.) (count (:content %)))) (book)))
        chapter (nth (keys book-map) (.nextInt (SecureRandom.) (count book-map)))]
    (first {(str book-name " " chapter ":" (get book-map chapter)) (book (str chapter ":" (get book-map chapter)))})))

(defn random-verse-str
  "Returns a random verse as a string.\\
  For divination purposes at higher accuracy see also [[secure-random-verse-str]].
  ```clojure
  (random-verse-str)
  => \"O Jehovah, to thee do I cry; for the fire hath devoured the pastures of the wilderness, and the flame hath burned
  all the trees of the field.\"
  ```"
  []
  ((comp first val) (random-verse)))

(defn secure-random-verse-str
  "Returns a random verse as a string.\\
  Like [[random-verse-str]] but using FIPS 140-2 compliant RNG to determine all collections indices.
  ```clojure
  (secure-random-verse-str)
  => \"And Absalom answered Joab, Behold, I sent unto thee, saying, Come hither, that I may send thee to the king, to
  say, Wherefore am I come from Geshur? it were better for me to be there still. Now therefore let me see the king's
  face; and if there be iniquity in me, let him kill me.\"
  ```"
  []
  ((comp first val) (secure-random-verse)))